package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.dbconnection.ConnectionException;
import by.kurlovich.musicshop.dbconnection.ConnectionPool;
import by.kurlovich.musicshop.entity.Genre;
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

public class GenreRepository implements Repository<Genre> {
    private static final String ADD_GENRE = "INSERT INTO genres (name, status) VALUES (?,?)";
    private static final String DELETE_GENRE = "UPDATE genres SET status='deleted' WHERE name=?";
    private static final String UPDATE_GENRE = "UPDATE genres SET name=? WHERE id=?";
    private static final String GET_STATUS = "SELECT status FROM genres WHERE name=?";
    private static final String SET_STATUS = "UPDATE genres SET status='active' WHERE name=?";
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreRepository.class);
    private ConnectionPool connectionPool;

    private static final int ID = 1;
    private static final int UPDATE_ID = 2;
    private static final int NAME = 2;
    private static final int SET_NAME = 1;
    private static final int S_STATUS = 2;
    private static final int STATUS = 3;

    public GenreRepository() throws RepositoryException {
        try {
            LOGGER.debug("Creating Genre Repository class.");
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionException e) {
            throw new RepositoryException("Can't create dbconnection pool", e);
        }
    }

    @Override
    public void add(Genre genre) throws RepositoryException {
        try {
            LOGGER.debug("Adding new genre {}", genre.getName());
            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(ADD_GENRE)) {
                ps.setString(SET_NAME, genre.getName());
                ps.setString(S_STATUS, genre.getStatus());
                ps.executeUpdate();
            }
            connectionPool.releaseConnection(dbConnection);
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add genre.", e);
        }
    }

    @Override
    public void delete(Genre genre) throws RepositoryException {
        try {
            LOGGER.debug("Deleting genre {}", genre.getName());
            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(DELETE_GENRE)) {
                ps.setString(SET_NAME, genre.getName());

                ps.executeUpdate();
            }
            connectionPool.releaseConnection(dbConnection);
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add genre.", e);
        }
    }

    @Override
    public void update(Genre genre) throws RepositoryException {
        try {
            LOGGER.debug("Updating genre {}", genre.getName());
            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(UPDATE_GENRE)) {
                ps.setString(SET_NAME, genre.getName());
                ps.setString(UPDATE_ID, genre.getId());

                ps.executeUpdate();
            }
            connectionPool.releaseConnection(dbConnection);
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add genre.\n"+e, e);
        }
    }

    @Override
    public List<Genre> query(Specification specification) throws RepositoryException {
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        List<Genre> genreList = new ArrayList<>();

        try {
            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(sqlSpecification.toSqlQuery())) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Genre genre = new Genre();

                        genre.setId(rs.getString(ID));
                        genre.setName(rs.getString(NAME));
                        genre.setStatus(rs.getString(STATUS));

                        genreList.add(genre);
                    }
                }

                connectionPool.releaseConnection(dbConnection);

                return genreList;
            }

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in genre query", e);
        }
    }

    @Override
    public Status getStatus(Genre genre) throws RepositoryException {
        try {
            String status = "";
            LOGGER.debug("Checking genre {} status.", genre.getName());
            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(GET_STATUS)) {
                ps.setString(SET_NAME, genre.getName());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        status = (rs.getString(1));
                    }
                }
            }

            connectionPool.releaseConnection(dbConnection);

            return Status.getStatus(status);

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add genre.", e);
        }
    }

    @Override
    public void undelete(Genre genre) throws RepositoryException {
        try {
            LOGGER.debug("Set genre {} status to active.", genre.getName());

            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(SET_STATUS)) {
                ps.setString(SET_NAME, genre.getName());

                ps.executeUpdate();
            }

            connectionPool.releaseConnection(dbConnection);

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add genre.", e);
        }
    }
}
