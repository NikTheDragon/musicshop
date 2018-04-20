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
import java.util.ArrayList;
import java.util.List;

public class TrackRepository implements Repository<Track> {
    private static final String ADD_TRACK = "INSERT INTO tracks (name, author, genre, year, length, status) VALUES (?,(SELECT id FROM authors WHERE name=?),(SELECT id FROM genres WHERE name=?),?,?,?)";
    private static final String DELETE_TRACK = "UPDATE tracks SET status='deleted' WHERE name=? AND author=(SELECT id FROM authors WHERE name=?)";
    private static final String UPDATE_TRACK = "UPDATE tracks SET name=?, author=(SELECT id FROM authors WHERE name=?), genre=(SELECT id FROM genres WHERE name=?), year=?, length=? WHERE id=?";
    private static final String GET_STATUS = "SELECT status FROM tracks WHERE name=?";
    private static final String SET_STATUS = "UPDATE tracks SET status='active' WHERE name=? AND author=(SELECT id FROM authors WHERE name=?) AND genre=(SELECT id FROM genres WHERE name=?) AND year=? AND length=?";
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreRepository.class);
    private ConnectionPool connectionPool;

    public TrackRepository() throws RepositoryException {
        try {
            LOGGER.debug("Creating track Repository class.");
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionException e) {
            throw new RepositoryException("Can't create dbconnection pool.\n" + e, e);
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
            throw new RepositoryException("Exception in add track.\n" + e, e);
        }
    }

    @Override
    public void delete(Track track) throws RepositoryException {
        try {
            LOGGER.debug("Deleting track {}", track.getName());
            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(DELETE_TRACK)) {
                ps.setString(1, track.getName());
                ps.setString(2, track.getAuthor());

                ps.executeUpdate();
            }
            connectionPool.releaseConnection(dbConnection);
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in delete track.\n" + e, e);
        }
    }

    @Override
    public void update(Track track) throws RepositoryException {
        try {
            LOGGER.debug("Updating track {}", track.getName());
            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(UPDATE_TRACK)) {
                ps.setString(1, track.getName());
                ps.setString(2, track.getAuthor());
                ps.setString(3, track.getGenre());
                ps.setString(4, track.getYear());
                ps.setString(5, track.getLength());
                ps.setString(6, track.getId());

                ps.executeUpdate();
            }
            connectionPool.releaseConnection(dbConnection);
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in update track.\n" + e, e);
        }
    }

    @Override
    public List<Track> query(Specification specification) throws RepositoryException {
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        List<Track> trackList = new ArrayList<>();

        try {
            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(sqlSpecification.toSqlQuery())) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Track track = new Track();

                        track.setId(rs.getString(1));
                        track.setName(rs.getString(2));
                        track.setAuthor(rs.getString(3));
                        track.setGenre(rs.getString(4));
                        track.setYear(rs.getString(5));
                        track.setLength(rs.getString(6));
                        track.setStatus(rs.getString(7));

                        trackList.add(track);
                    }
                }

                connectionPool.releaseConnection(dbConnection);

                return trackList;
            }

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in track query.\n" + e, e);
        }
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
            throw new RepositoryException("Exception in check track status method.\n" + e, e);
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
            throw new RepositoryException("Exception in set track status method.\n" + e, e);
        }
    }
}
