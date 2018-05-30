package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.repository.EntityRepository;
import by.kurlovich.musicshop.repository.dbconnection.ConnectionException;
import by.kurlovich.musicshop.repository.dbconnection.ConnectionPool;
import by.kurlovich.musicshop.entity.Author;
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
import java.util.Collections;
import java.util.List;

public class AuthorRepositoryImpl implements EntityRepository<Author> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRepositoryImpl.class);
    private static final String GET_STATUS = "SELECT status FROM authors WHERE name=?";
    private static final String SET_STATUS = "UPDATE authors SET status='active' WHERE name=? AND genre=(SELECT id FROM genres WHERE name=?) AND type=?";
    private static final String ADD_AUTHOR = "INSERT INTO authors (name, genre, type, status) VALUES (?,(SELECT id FROM genres WHERE name=?),?,?)";
    private static final String DELETE_AUTHOR = "UPDATE authors SET status='deleted' WHERE name=?";
    private static final String UPDATE_AUTHOR = "UPDATE authors SET name=?, genre=(SELECT id FROM genres WHERE name=?), type=? WHERE id=?";
    private final ConnectionPool pool;

    public AuthorRepositoryImpl() throws RepositoryException {
        try {
            LOGGER.debug("Creating author OtherEntityRepository class.");
            pool = ConnectionPool.getInstance();
        } catch (ConnectionException e) {
            throw new RepositoryException("Can't create dbconnection pool", e);
        }
    }

    @Override
    public void add(Author item) throws RepositoryException {
        LOGGER.debug("adding new author {}", item.getName());

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(ADD_AUTHOR)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getGenre());
            ps.setString(3, item.getType());
            ps.setString(4, "active");

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add of AuthorRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void delete(Author item) throws RepositoryException {
        LOGGER.debug("deleting author {}", item.getName());

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_AUTHOR)) {

            ps.setString(1, item.getName());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in delete of AuthorRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void update(Author item) throws RepositoryException {
        LOGGER.debug("updating author {}", item.getName());

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_AUTHOR)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getGenre());
            ps.setString(3, item.getType());
            ps.setString(4, item.getId());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in update of AuthorRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public List<Author> query(Specification specification) throws RepositoryException {
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        List<Author> authorList = new ArrayList<>();

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlSpecification.toSqlQuery());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Author author = new Author();

                author.setId(rs.getString(1));
                author.setName(rs.getString(2));
                author.setGenre(rs.getString(3));
                author.setType(rs.getString(4));
                author.setStatus(rs.getString(5));

                authorList.add(author);
            }
            LOGGER.debug("found {} items.", authorList.size());
            return authorList;

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in query of AuthorRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public List<Author> queryWithOwners(Specification specification) throws RepositoryException {
        return Collections.emptyList();
    }

    @Override
    public Status getStatus(Author item) throws RepositoryException {
        LOGGER.debug("Checking author {} status.", item.getName());
        String status = "";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_STATUS)) {

            ps.setString(1, item.getName());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    status = (rs.getString(1));
                }
            }

            return Status.getStatus(status);

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in check getStatus of AuthorRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void undelete(Author item) throws RepositoryException {
        LOGGER.debug("Set author {} status to active.", item.getName());

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SET_STATUS)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getGenre());
            ps.setString(3, item.getType());

            ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in undelete of AuthorRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void buy(Specification specification) throws RepositoryException {

    }
}
