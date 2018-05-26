package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.repository.dbconnection.ConnectionException;
import by.kurlovich.musicshop.repository.dbconnection.ConnectionPool;
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

public class UserRepositoryImpl implements UserRepository {
    private static final String ADD_USER = "INSERT INTO users (name,surname,login,password,email,role,status,points) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE_USER = "UPDATE users SET name=?, surname=?, login=?, email=?, role=?, status=?, points=? WHERE id=?";
    private static final String UPDATE_PASSWORD = "UPDATE users SET password=? WHERE id=?";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);
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

    public UserRepositoryImpl() throws RepositoryException {
        try {
            LOGGER.debug("Creating UserRepository class.");
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
                ps.setString(6, "user");
                ps.setString(7, "active");
                ps.setString(8, "0");

                ps.executeUpdate();
            }

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add of UserRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void update(User user) throws RepositoryException {
        try (Connection connection = pool.getConnection()) {
            LOGGER.debug("Updating user {}", user.getName());
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_USER)) {
                ps.setString(1, user.getName());
                ps.setString(2, user.getSurname());
                ps.setString(3, user.getLogin());
                ps.setString(4, user.getEmail());
                ps.setString(5, user.getRole());
                ps.setString(6, user.getStatus());
                ps.setString(7, String.valueOf(user.getPoints()));
                ps.setString(8, user.getId());

                ps.executeUpdate();
            }

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in update of UserRepositoryImpl.\n" + e, e);
        }
    }

    @Override
    public void updatePassword(String password, String userId) throws RepositoryException{
        try (Connection connection = pool.getConnection()) {
            LOGGER.debug("Updating password.");
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_PASSWORD)) {
                ps.setString(1, password);
                ps.setString(2, userId);

                ps.executeUpdate();
            }

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in updatePassword of UserRepositoryImpl.\n" + e, e);
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
            LOGGER.debug("found {} items.", allUsers.size());
            return allUsers;

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in user query", e);
        }
    }
}
