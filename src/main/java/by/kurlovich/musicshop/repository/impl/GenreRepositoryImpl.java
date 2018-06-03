package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.entity.Genre;
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

public class GenreRepositoryImpl extends BaseEntityRepository<Genre> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreRepositoryImpl.class);

    private static final String ADD_GENRE = "INSERT INTO genres (name, status) VALUES (?,?)";
    private static final String DELETE_GENRE = "UPDATE genres SET status='deleted' WHERE name=?";
    private static final String UPDATE_GENRE = "UPDATE genres SET name=? WHERE id=?";
    private static final String GET_STATUS = "SELECT status FROM genres WHERE name=?";
    private static final String SET_STATUS = "UPDATE genres SET status='active' WHERE name=?";

    private static final int ID = 1;
    private static final int UPDATE_ID = 2;
    private static final int NAME = 2;
    private static final int SET_NAME = 1;
    private static final int S_STATUS = 2;
    private static final int STATUS = 3;

    public GenreRepositoryImpl() throws RepositoryException {
        super();
    }

    @Override
    public void add(Genre genre) throws RepositoryException {
        LOGGER.debug("adding new genre {}", genre.getName());
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(ADD_GENRE)) {

            ps.setString(SET_NAME, genre.getName());
            ps.setString(S_STATUS, "active");
            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add of GenreRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void delete(Genre genre) throws RepositoryException {
        LOGGER.debug("deleting genre {}", genre.getName());
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_GENRE)) {

            ps.setString(SET_NAME, genre.getName());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in delete of GenreRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void update(Genre genre) throws RepositoryException {
        LOGGER.debug("updating genre {}", genre.getName());
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_GENRE)) {

            ps.setString(SET_NAME, genre.getName());
            ps.setString(UPDATE_ID, genre.getId());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in update of GenreRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public List<Genre> query(Specification specification) throws RepositoryException {
        LOGGER.debug("querying genres.");
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        List<Genre> genreList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlSpecification.toSqlQuery())) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Genre genre = new Genre();

                    genre.setId(rs.getString(ID));
                    genre.setName(rs.getString(NAME));
                    genre.setStatus(rs.getString(STATUS));

                    genreList.add(genre);
                }
            }
            LOGGER.debug("found {} items.", genreList.size());
            return genreList;

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in query of GenreRepositoryImpl\n" + e, e);
        }
    }

    @Override
    public List<Genre> queryWithOwners(Specification specification) throws RepositoryException {
        return Collections.emptyList();
    }

    @Override
    public Status getStatus(Genre genre) throws RepositoryException {
        LOGGER.debug("checking genre {} status.", genre.getName());
        String status = "";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_STATUS)) {

            ps.setString(SET_NAME, genre.getName());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    status = (rs.getString(1));
                }
            }

            return Status.getStatus(status);

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in getStatus of GenreRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void undelete(Genre genre) throws RepositoryException {
        LOGGER.debug("set genre {} status to active.", genre.getName());

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SET_STATUS)) {

            ps.setString(SET_NAME, genre.getName());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in undelete of GenreRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void buy(Specification specification) {
        throw new UnsupportedOperationException();
    }
}
