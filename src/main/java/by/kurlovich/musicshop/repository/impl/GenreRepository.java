package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.dbconnection.ConnectionException;
import by.kurlovich.musicshop.dbconnection.ConnectionPool;
import by.kurlovich.musicshop.entity.Genre;
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
    private static final String ADD_GENRE = "INSERT INTO genres (name) VALUES (?)";
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreRepository.class);
    private ConnectionPool connectionPool;

    private static final int ID = 1;
    private static final int NAME = 2;
    private static final int SET_NAME = 1;

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

                ps.executeUpdate();
            }
            connectionPool.releaseConnection(dbConnection);
        } catch (SQLException | ConnectionException e) {
            System.out.println(e);
            throw new RepositoryException("Exception in add genre.", e);
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
}
