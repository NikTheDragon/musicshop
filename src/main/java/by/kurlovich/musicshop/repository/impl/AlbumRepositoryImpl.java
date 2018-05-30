package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.SqlSpecification;
import by.kurlovich.musicshop.repository.dbconnection.ConnectionException;
import by.kurlovich.musicshop.repository.dbconnection.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepositoryImpl extends BaseEntityRepository<Album> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumRepositoryImpl.class);
    private static final String GET_STATUS = "SELECT status FROM albums WHERE name=?";
    private static final String SET_STATUS = "UPDATE albums SET status='active' WHERE name=? AND author=(SELECT id FROM authors WHERE name=?) AND genre=(SELECT id FROM genres WHERE name=?) AND year=?";
    private static final String ADD_ALBUM = "INSERT INTO albums (name, author, genre, year, status) VALUES (?,(SELECT id FROM authors WHERE name=?),(SELECT id FROM genres WHERE name=?),?,?)";
    private static final String DELETE_ALBUM = "UPDATE albums SET status='deleted' WHERE name=? AND author=(SELECT id FROM authors WHERE name=?)";
    private static final String UPDATE_ALBUM = "UPDATE albums SET name=?, author=(SELECT id FROM authors WHERE name=?), genre=(SELECT id FROM genres WHERE name=?), year=? WHERE id=?";
    private final ConnectionPool pool;

    public AlbumRepositoryImpl() throws RepositoryException {
        try {
            LOGGER.debug("Creating album OtherEntityRepository class.");
            pool = ConnectionPool.getInstance();
        } catch (ConnectionException e) {
            throw new RepositoryException("Can't create dbconnection pool", e);
        }
    }

    @Override
    public void add(Album item) throws RepositoryException {
        LOGGER.debug("adding new album {}", item.getName());

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(ADD_ALBUM)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getAuthor());
            ps.setString(3, item.getGenre());
            ps.setInt(4, item.getYear());
            ps.setString(5, "active");

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add of AlbumRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void delete(Album item) throws RepositoryException {
        LOGGER.debug("deleting album {}", item.getName());

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_ALBUM)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getAuthor());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in delete of AlbumRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void update(Album item) throws RepositoryException {
        LOGGER.debug("updating album {}", item.getName());

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_ALBUM)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getAuthor());
            ps.setString(3, item.getGenre());
            ps.setInt(4, item.getYear());
            ps.setString(5, item.getId());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in update of AlbumRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public List<Album> query(Specification specification) throws RepositoryException {
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        List<Album> albumList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlSpecification.toSqlQuery());
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
            LOGGER.debug("found {} items.", albumList.size());
            return albumList;

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in query of AlbumRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public List<Album> queryWithOwners(Specification specification) throws RepositoryException {
        LOGGER.debug("quering mixes with owners.");
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        List<Album> albumList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlSpecification.toSqlQuery());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Album album = new Album();

                album.setId(rs.getString(1));
                album.setName(rs.getString(2));
                album.setAuthor(rs.getString(3));
                album.setGenre(rs.getString(4));
                album.setYear(rs.getInt(5));
                album.setTracksCount(rs.getInt(6));
                album.setStatus(rs.getString(7));
                album.setOwnerId(rs.getString(8));

                albumList.add(album);
            }
            LOGGER.debug("found {} items.", albumList.size());
            return albumList;

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in queryWithOwners of AlbumRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public Status getStatus(Album item) throws RepositoryException {
        LOGGER.debug("checking album {} status.", item.getName());
        String status = "";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_STATUS)) {

            ps.setString(1, item.getName());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    status = (rs.getString(1));
                }
            }

            return Status.getStatus(status);

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in getStatus of AlbumRepositoryImpl.\n" + e, e);
        }
    }


    @Override
    public void undelete(Album item) throws RepositoryException {
        LOGGER.debug("Set album {} status to active.", item.getName());

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SET_STATUS)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getAuthor());
            ps.setString(3, item.getGenre());
            ps.setInt(4, item.getYear());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in undelete of AlbumRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void buy(Specification specification) throws RepositoryException {
        LOGGER.debug("buying album.");
        SqlSpecification sqlSpecification = (SqlSpecification) specification;

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlSpecification.toSqlQuery())) {

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in buy of TrackRepositoryImpl..\n" + e, e);
        }
    }
}
