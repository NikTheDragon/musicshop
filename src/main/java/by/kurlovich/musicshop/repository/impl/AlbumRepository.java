package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.dbconnection.ConnectionException;
import by.kurlovich.musicshop.dbconnection.DBConnection;
import by.kurlovich.musicshop.entity.Album;
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

public class AlbumRepository implements Repository<Album> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumRepository.class);
    private static final String GET_STATUS = "SELECT status FROM albums WHERE name=?";
    private static final String SET_STATUS = "UPDATE albums SET status='active' WHERE name=? AND author=(SELECT id FROM authors WHERE name=?) genre=(SELECT id FROM genres WHERE name=?) AND year=?";
    private static final String ADD_ALBUM = "INSERT INTO albums (name, author, genre, year, status) VALUES (?,(SELECT id FROM authors WHERE name=?),(SELECT id FROM genres WHERE name=?),?,?)";
    private static final String DELETE_ALBUM = "UPDATE albums SET status='deleted' WHERE name=? AND author=(SELECT id FROM authors WHERE name=?)";
    private static final String UPDATE_ALBUM = "UPDATE albums SET name=?, (SELECT id FROM authors WHERE name=?), genre=(SELECT id FROM genres WHERE name=?), year=? WHERE id=?";
    private DBConnection dbConnection;

    public AlbumRepository() {
        LOGGER.debug("Creating album Repository class.");
    }

    @Override
    public void add(Album item) throws RepositoryException {
        try (DBConnection dbConnection = new DBConnection()) {
            LOGGER.debug("Adding new album {}", item.getName());
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(ADD_ALBUM)) {
                ps.setString(1, item.getName());
                ps.setString(2, item.getAuthor());
                ps.setString(3, item.getGenre());
                ps.setInt(4, item.getYear());
                ps.setString(5, item.getStatus());

                ps.executeUpdate();
            }
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add album.\n" + e, e);
        }
    }

    @Override
    public void delete(Album item) throws RepositoryException {
        try (DBConnection dbConnection = new DBConnection()) {
            LOGGER.debug("Deleting album {}", item.getName());
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(DELETE_ALBUM)) {
                ps.setString(1, item.getName());
                ps.setString(2, item.getAuthor());

                ps.executeUpdate();
            }
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in delete album.\n" + e, e);
        }
    }

    @Override
    public void update(Album item) throws RepositoryException {
        try (DBConnection dbConnection = new DBConnection()) {
            LOGGER.debug("Updating album {}", item.getName());
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_ALBUM)) {
                ps.setString(1, item.getName());
                ps.setString(2, item.getAuthor());
                ps.setString(3, item.getGenre());
                ps.setInt(4, item.getYear());
                ps.setString(5, item.getId());

                ps.executeUpdate();
            }
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in update album.\n" + e, e);
        }
    }

    @Override
    public List<Album> query(Specification specification) throws RepositoryException {
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        List<Album> albumList = new ArrayList<>();

        try (DBConnection dbConnection = new DBConnection()) {
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(sqlSpecification.toSqlQuery());
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Album album = new Album();

                    album.setId(rs.getString(1));
                    album.setName(rs.getString(2));
                    album.setAuthor(rs.getString(3));
                    album.setGenre(rs.getString(4));
                    album.setYear(rs.getInt(5));
                    album.setStatus(rs.getString(6));

                    albumList.add(album);
                }
                return albumList;
            }

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in author query.\n" + e, e);
        }
    }

    @Override
    public Status getStatus(Album item) throws RepositoryException {
        final int STATUS = 1;

        try (DBConnection dbConnection = new DBConnection()) {
            String status = "";
            LOGGER.debug("Checking album {} status.", item.getName());
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(GET_STATUS)) {

                ps.setString(1, item.getName());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        status = (rs.getString(STATUS));
                    }
                }
            }

            return Status.getStatus(status);

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in get album status method.\n" + e, e);
        }
    }


    @Override
    public void undelete(Album item) throws RepositoryException {
        try (DBConnection dbConnection = new DBConnection()) {
            LOGGER.debug("Set album {} status to active.", item.getName());
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(SET_STATUS)) {
                ps.setString(1, item.getName());
                ps.setString(2, item.getAuthor());
                ps.setString(3, item.getGenre());
                ps.setInt(4, item.getYear());

                ps.executeUpdate();
            }
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in set album status method.\n" + e, e);
        }
    }
}
