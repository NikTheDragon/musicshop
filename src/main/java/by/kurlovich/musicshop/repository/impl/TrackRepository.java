package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.dbconnection.ConnectionException;
import by.kurlovich.musicshop.dbconnection.ConnectionPool;
import by.kurlovich.musicshop.entity.Track;
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
    private final ConnectionPool pool;

    public TrackRepository() throws RepositoryException {
        try {
            LOGGER.debug("Creating track Repository class.");
            pool = ConnectionPool.getInstance();
        } catch (ConnectionException e) {
            throw new RepositoryException("Can't create dbconnection pool.\n" + e, e);
        }
    }

    @Override
    public void add(Track track) throws RepositoryException {
        LOGGER.debug("adding track {}", track.getName());
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(ADD_TRACK)) {

            ps.setString(1, track.getName());
            ps.setString(2, track.getAuthor());
            ps.setString(3, track.getGenre());
            ps.setString(4, track.getYear());
            ps.setString(5, track.getLength());
            ps.setString(6, track.getStatus());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add of TrackRepository.\n" + e, e);
        }
    }

    @Override
    public void delete(Track track) throws RepositoryException {
        LOGGER.debug("deleting track {}", track.getName());
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_TRACK)) {

            ps.setString(1, track.getName());
            ps.setString(2, track.getAuthor());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in delete of TrackRepository.\n" + e, e);
        }
    }

    @Override
    public void update(Track track) throws RepositoryException {
        LOGGER.debug("updating track {}", track.getName());
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_TRACK)) {

            ps.setString(1, track.getName());
            ps.setString(2, track.getAuthor());
            ps.setString(3, track.getGenre());
            ps.setString(4, track.getYear());
            ps.setString(5, track.getLength());
            ps.setString(6, track.getId());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in update of TrackRepository..\n" + e, e);
        }
    }

    @Override
    public List<Track> query(Specification specification) throws RepositoryException {
        LOGGER.debug("quering tracks.");
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        List<Track> trackList = new ArrayList<>();

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlSpecification.toSqlQuery());
             ResultSet rs = ps.executeQuery()) {

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

            return trackList;

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in query of TrackRepository..\n" + e, e);
        }
    }

    @Override
    public List<Track> queryWithOwners(Specification specification) throws RepositoryException {
        LOGGER.debug("quering tracks with owners.");
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        List<Track> trackList = new ArrayList<>();

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlSpecification.toSqlQuery());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Track track = new Track();

                track.setId(rs.getString(1));
                track.setName(rs.getString(2));
                track.setAuthor(rs.getString(3));
                track.setGenre(rs.getString(4));
                track.setYear(rs.getString(5));
                track.setLength(rs.getString(6));
                track.setStatus(rs.getString(7));
                track.setOwnerId(rs.getString(8));

                trackList.add(track);
            }
            LOGGER.debug("found {} items.", trackList.size());
            return trackList;

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in queryWithOwners of TrackRepository..\n" + e, e);
        }
    }

    @Override
    public Status getStatus(Track track) throws RepositoryException {
        LOGGER.debug("checking track {} status.", track.getName());
        String status = "";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_STATUS)) {

            ps.setString(1, track.getName());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    status = (rs.getString(1));
                }
            }

            return Status.getStatus(status);

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in getStatus of TrackRepository.\n" + e, e);
        }
    }

    @Override
    public void undelete(Track track) throws RepositoryException {
        LOGGER.debug("set track {} status to active.", track.getName());

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SET_STATUS)) {
            ps.setString(1, track.getName());
            ps.setString(2, track.getAuthor());
            ps.setString(3, track.getGenre());
            ps.setString(4, track.getYear());
            ps.setString(5, track.getLength());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in undelete of TrackRepository.\n" + e, e);
        }
    }

    @Override
    public void buy(Specification specification) throws RepositoryException {
        LOGGER.debug("buying track.");
        SqlSpecification sqlSpecification = (SqlSpecification) specification;

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlSpecification.toSqlQuery())) {
            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in query of TrackRepository..\n" + e, e);
        }
    }
}
