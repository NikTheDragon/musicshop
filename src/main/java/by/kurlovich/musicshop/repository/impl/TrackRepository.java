package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.dbconnection.ConnectionException;
import by.kurlovich.musicshop.dbconnection.ConnectionPool;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.receiver.ReceiverException;
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
import java.util.List;

public class TrackRepository implements Repository<Track> {
    private static final String ADD_TRACK = "INSERT INTO tracks (name, author, genre, year, length, status) VALUES (?,?,?,?,?,?)";
    private static final String GET_STATUS = "SELECT status FROM tracks WHERE name=?";
    private static final String SET_STATUS = "UPDATE tracks SET status='active' WHERE name=? AND author=? AND genre=? AND year=? AND length=?";
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreRepository.class);
    private ConnectionPool connectionPool;

    public TrackRepository() throws RepositoryException {
        try {
            LOGGER.debug("Creating track Repository class.");
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionException e) {
            throw new RepositoryException("Can't create dbconnection pool", e);
        }
    }

    @Override
    public void add(Track track) throws RepositoryException {
        try {
            LOGGER.debug("Adding new track {}", track.getName());
            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(ADD_TRACK)) {
                ps.setString(1, track.getName());
                ps.setString(2, track.getAuthor());
                ps.setString(3, track.getGenre());
                ps.setString(4, track.getYear());
                ps.setString(5, track.getLength());
                ps.setString(6, track.getStatus());

                ps.executeUpdate();
            }
            connectionPool.releaseConnection(dbConnection);
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add track.", e);
        }
    }

    @Override
    public void delete(Track item) throws RepositoryException {

    }

    @Override
    public void update(Track item) throws RepositoryException {

    }

    @Override
    public List<Track> query(Specification specification) throws RepositoryException {
        return null;
    }

    @Override
    public Status checkStatus(Track track) throws RepositoryException {
        final int STATUS = 1;

        try {
            String status = "";
            LOGGER.debug("Checking track {} status.", track.getName());
            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(GET_STATUS)) {
                ps.setString(1, track.getName());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        status = (rs.getString(STATUS));
                    }
                }
            }

            connectionPool.releaseConnection(dbConnection);

            switch (status) {
                case "active":
                    return Status.ACTIVE;
                case "deleted":
                    return Status.DELETE;
                default:
                    return Status.NA;
            }

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in check track status method.", e);
        }
    }

    @Override
    public void setStatus(Track track) throws RepositoryException {
        try {
            LOGGER.debug("Set track {} status to active.", track.getName());

            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(SET_STATUS)) {
                ps.setString(1, track.getName());
                ps.setString(2, track.getAuthor());
                ps.setString(3, track.getGenre());
                ps.setString(4, track.getYear());
                ps.setString(5, track.getLength());

                ps.executeUpdate();
            }

            connectionPool.releaseConnection(dbConnection);

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in set track status method.", e);
        }
    }
}
