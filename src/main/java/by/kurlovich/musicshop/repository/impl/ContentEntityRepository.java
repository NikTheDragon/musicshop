package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.dbconnection.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ContentEntityRepository extends BaseEntityRepository<Content> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentEntityRepository.class);

    ContentEntityRepository() throws RepositoryException {
    }

    @Override
    public Status getStatus(Content item) throws RepositoryException {
        LOGGER.debug("checking content {} status.", item.getTrackName());
        String status = "";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(getStatusSQL())) {

            ps.setString(1, item.getEntityId());
            ps.setString(2, item.getTrackName());
            ps.setString(3, item.getAuthorName());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    status = rs.getString(1);
                }
            }

            return Status.getStatus(status);

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in getStatus method: " + e.getMessage(), e);
        }
    }

    protected abstract String getStatusSQL();

}


