package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.Connection.ConnectionException;
import by.kurlovich.musicshop.Connection.ConnectionPool;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.repository.*;

import by.kurlovich.musicshop.util.DbConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User> {
    final String ADD_CLIENT = "INSERT INTO users (name,surname,login,password,email,role,status) VALUES (?,?,?,?,?,?,?)";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    private ConnectionPool connectionPool;
    private Connection connection;

    public UserRepository() throws RepositoryException {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionException e) {
            throw new RepositoryException("Can't create connection pool", e);
        }
    }

    static final int NAME = 1;
    static final int SURNAME = 2;
    static final int LOGIN = 3;
    static final int PASSWORD = 4;
    static final int EMAIL = 5;
    static final int ROLE = 6;
    static final int STATUS = 7;

    @Override
    public void add(User user) throws RepositoryException {

        try (PreparedStatement ps = connectionPool.getConnection().prepareStatement(ADD_CLIENT)) {
            ps.setString(NAME, user.getName());
            ps.setString(SURNAME, user.getSurname());
            ps.setString(LOGIN, user.getLogin());
            ps.setString(PASSWORD, user.getPassword());
            ps.setString(EMAIL, user.getEmail());
            ps.setString(ROLE, user.getRole());
            ps.setString(STATUS, user.getStatus());

            //ps.executeUpdate();

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in addNewClient", e);
        }
    }

    @Override
    public List<User> query(Specification specification) throws RepositoryException {
        final SqlSpecification sqlSpecification = (SqlSpecification) specification;
        final List<User> userList = new ArrayList<>();

        try (PreparedStatement ps = connectionPool.getConnection().prepareStatement(sqlSpecification.toSqlQuery())) {

            LOGGER.debug("ps string: {}", sqlSpecification.toSqlQuery());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User();

                    user.setName(rs.getString(NAME));
                    user.setSurname(rs.getString(SURNAME));
                    user.setLogin(rs.getString(LOGIN));
                    user.setPassword(rs.getString(PASSWORD));
                    user.setEmail(rs.getString(EMAIL));
                    user.setRole(rs.getString(ROLE));
                    user.setStatus(rs.getString(STATUS));

                    userList.add(user);
                }

            }
            LOGGER.debug("Found {} users", userList.size());
            return userList;

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in user query", e);
        }

    }
}
