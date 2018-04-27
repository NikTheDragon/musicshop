package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.dbconnection.ConnectionException;
import by.kurlovich.musicshop.dbconnection.DBConnection;
import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.SqlSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumContentRepository implements Repository<Content> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumContentRepository.class);
    private static final String ADD_CONTENT = "INSERT INTO albums_content (album_id, track_id, status) VALUES (?,(SELECT id FROM tracks WHERE name=? AND author=(SELECT id FROM authors WHERE name=?)), 'active')";
    private static final String DELETE_CONTENT = "UPDATE albums_content SET status='deleted' WHERE album_id=? AND track_id=?";
    private static final String GET_STATUS = "SELECT status FROM albums_content WHERE album_id=? AND track_id=(SELECT id FROM tracks WHERE name=? AND author=(SELECT id FROM authors WHERE name=?))";

    @Override
    public void add(Content item) throws RepositoryException {
        try (DBConnection dbConnection = new DBConnection()) {
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(ADD_CONTENT)) {
                ps.setString(1, item.getEntityId());
                ps.setString(2, item.getTrackName());
                ps.setString(3, item.getAuthorName());

                ps.executeUpdate();
            }
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add of AlbumContentRepository.\n" + e, e);
        }
    }

    @Override
    public void delete(Content item) throws RepositoryException {
        try (DBConnection dbConnection = new DBConnection()) {
            LOGGER.debug("Deleting album track.");
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(DELETE_CONTENT)) {
                ps.setString(1, item.getEntityId());
                ps.setString(2, item.getTrackId());

                ps.executeUpdate();
            }
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in delete of AlbumContentRepository.\n" + e, e);
        }
    }

    @Override
    public void update(Content item) throws RepositoryException {

    }

    @Override
    public List<Content> query(Specification specification) throws RepositoryException {
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        List<Content> contentList = new ArrayList<>();

        try (DBConnection dbConnection = new DBConnection()) {
            Connection connection = dbConnection.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sqlSpecification.toSqlQuery())) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Content content = new Content();

                        content.setEntityId(rs.getString(1));
                        content.setTrackId(rs.getString(2));
                        content.setTrackName(rs.getString(3));
                        content.setLength(rs.getString(4));
                        content.setStatus(rs.getString(5));

                        contentList.add(content);
                    }
                }

                LOGGER.debug("found {} entities.", contentList.size());
                return contentList;
            }

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception query of AlbumContentRepository.\n" + e, e);
        }
    }

    @Override
    public Status getStatus(Content item) throws RepositoryException {
        final int STATUS = 1;

        try (DBConnection dbConnection = new DBConnection()) {
            String status = "";
            LOGGER.debug("Checking content {} status.", item.getTrackName());
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(GET_STATUS)) {
                ps.setString(1, item.getEntityId());
                ps.setString(2, item.getTrackName());
                ps.setString(3, item.getAuthorName());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        status = (rs.getString(STATUS));
                    }
                }
            }

            return Status.getStatus(status);

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in getStatus of AlbumContentRepository.\n" + e, e);
        }
    }

    @Override
    public void undelete(Content item) throws RepositoryException {

    }
}
