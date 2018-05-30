package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.SqlSpecification;
import by.kurlovich.musicshop.repository.dbconnection.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MixContentRepositoryImpl extends ContentEntityRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(MixContentRepositoryImpl.class);

    private static final String ADD_CONTENT = "INSERT INTO mixes_content (mix_id, track_id, status) VALUES (?,(SELECT id FROM tracks WHERE name=? AND author=(SELECT id FROM authors WHERE name=?)), 'active')";
    private static final String DELETE_CONTENT = "UPDATE mixes_content SET status='deleted' WHERE mix_id=? AND track_id=?";
    private static final String GET_STATUS = "SELECT status FROM mixes_content WHERE mix_id=? AND track_id=(SELECT id FROM tracks WHERE name=? AND author=(SELECT id FROM authors WHERE name=?))";
    private static final String SET_STATUS = "UPDATE mixes_content SET status='active' WHERE mix_id=? AND track_id=(SELECT id FROM tracks WHERE name=? AND author=(SELECT id FROM authors WHERE name=?))";

    public MixContentRepositoryImpl() throws RepositoryException {
        super();
    }

    @Override
    public void add(Content item) throws RepositoryException {
        LOGGER.debug("adding new content for {}", item.getTrackName());
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(ADD_CONTENT)) {

            ps.setString(1, item.getEntityId());
            ps.setString(2, item.getTrackName());
            ps.setString(3, item.getAuthorName());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add of MixContentRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void delete(Content item) throws RepositoryException {
        LOGGER.debug("Deleting content");

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_CONTENT)) {

            ps.setString(1, item.getEntityId());
            ps.setString(2, item.getTrackId());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in delete of MixContentRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void update(Content item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Content> query(Specification specification) throws RepositoryException {
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        List<Content> contentList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlSpecification.toSqlQuery())) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Content content = new Content();

                    content.setEntityId(rs.getString(1));
                    content.setTrackId(rs.getString(2));
                    content.setTrackName(rs.getString(3));
                    content.setAuthorName(rs.getString(4));
                    content.setStatus(rs.getString(5));

                    contentList.add(content);
                }
            }
            return contentList;

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception query of MixContentRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public List<Content> queryWithOwners(Specification specification) throws RepositoryException {
        return Collections.emptyList();
    }

    @Override
    public void undelete(Content item) throws RepositoryException {
        LOGGER.debug("Set content {} status to active.", item.getTrackName());

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SET_STATUS)) {

            ps.setString(1, item.getEntityId());
            ps.setString(2, item.getTrackName());
            ps.setString(3, item.getAuthorName());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception undelete of MixContentRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void buy(Specification specification) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getStatusSQL() {

        return GET_STATUS;
    }
}
