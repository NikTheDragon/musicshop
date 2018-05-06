package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.dbconnection.ConnectionException;
import by.kurlovich.musicshop.dbconnection.ConnectionPool;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User> {
    private static final String ADD_USER = "INSERT INTO users (name,surname,login,password,email,role,status,points) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE_USER = "UPDATE users SET name=?, surname=?, login=?, password=?, email=?, role=?, status=?, points=? WHERE id=?";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    private final ConnectionPool pool;

    static final int ID = 1;
    static final int NAME = 2;
    static final int SURNAME = 3;
    static final int LOGIN = 4;
    static final int PASSWORD = 5;
    static final int EMAIL = 6;
    static final int ROLE = 7;
    static final int STATUS = 8;
    static final int POINTS = 9;

    public UserRepository() throws RepositoryException {
        try {
            LOGGER.debug("Creating User Repository class.");
            pool = ConnectionPool.getInstance();
        } catch (ConnectionException e) {
            throw new RepositoryException("Can't create dbconnection pool", e);
        }
    }

    @Override
    public void add(User user) throws RepositoryException {
        try (Connection connection = pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(ADD_USER)) {
                ps.setString(1, user.getName());
                ps.setString(2, user.getSurname());
                ps.setString(3, user.getLogin());
                ps.setString(4, user.getPassword());
                ps.setString(5, user.getEmail());
                ps.setString(6, user.getRole());
                ps.setString(7, user.getStatus());
                ps.setString(8, String.valueOf(user.getPoints()));

                ps.executeUpdate();
            }

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add of UserRepository.\n" + e, e);
        }
    }

    @Override
    public void delete(User item) throws RepositoryException {

    }

    @Override
    public void update(User item) throws RepositoryException {
        try (Connection connection = pool.getConnection()) {
            LOGGER.debug("Updating user {}", item.getName());
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_USER)) {
                ps.setString(1, item.getName());
                ps.setString(2, item.getSurname());
                ps.setString(3, item.getLogin());
                ps.setString(4, item.getPassword());
                ps.setString(5, item.getEmail());
                ps.setString(6, item.getRole());
                ps.setString(7, item.getStatus());
                ps.setString(8, String.valueOf(item.getPoints()));
                ps.setString(9, item.getId());

                ps.executeUpdate();
            }

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in update of UserRepository.\n" + e, e);
        }
    }

    @Override
    public List<User> query(Specification specification) throws RepositoryException {
        LOGGER.debug("Requesting all users.");
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        List<User> allUsers = new ArrayList<>();

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlSpecification.toSqlQuery());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();

                user.setId(rs.getString(ID));
                user.setName(rs.getString(NAME));
                user.setSurname(rs.getString(SURNAME));
                user.setLogin(rs.getString(LOGIN));
                user.setPassword(rs.getString(PASSWORD));
                user.setEmail(rs.getString(EMAIL));
                user.setRole(rs.getString(ROLE));
                user.setStatus(rs.getString(STATUS));
                user.setPoints(rs.getInt(POINTS));

                allUsers.add(user);
            }

            return allUsers;

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in user query", e);
        }
    }

    @Override
    public List<User> queryWithOwners(Specification specification) throws RepositoryException {
        return new ArrayList<>();
    }

    @Override
    public Status getStatus(User item) throws RepositoryException {
        return null;
    }

    @Override
    public void undelete(User item) throws RepositoryException {

    }

    @Override
    public void buy(Specification specification) throws RepositoryException {

    }
}
