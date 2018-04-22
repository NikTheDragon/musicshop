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
    private static final String ADD_CLIENT = "INSERT INTO users (name,surname,login,password,email,role,status) VALUES (?,?,?,?,?,?,?)";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    private ConnectionPool connectionPool;

    static final int ID = 1;
    static final int NAME = 2;
    static final int SURNAME = 3;
    static final int LOGIN = 4;
    static final int PASSWORD = 5;
    static final int EMAIL = 6;
    static final int ROLE = 7;
    static final int STATUS = 8;

    public UserRepository() throws RepositoryException {
        try {
            LOGGER.debug("Creating User Repository class.");
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionException e) {
            throw new RepositoryException("Can't create dbconnection pool", e);
        }
    }

    @Override
    public void add(User user) throws RepositoryException {
        try {
            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(ADD_CLIENT)) {
                ps.setString(NAME, user.getName());
                ps.setString(SURNAME, user.getSurname());
                ps.setString(LOGIN, user.getLogin());
                ps.setString(PASSWORD, user.getPassword());
                ps.setString(EMAIL, user.getEmail());
                ps.setString(ROLE, user.getRole());
                ps.setString(STATUS, user.getStatus());

                //ps.executeUpdate();
            }
            connectionPool.releaseConnection(dbConnection);
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in addNewClient", e);
        }
    }

    @Override
    public void delete(User item) throws RepositoryException {

    }

    @Override
    public void update(User item) throws RepositoryException {

    }

    @Override
    public List<User> query(Specification specification) throws RepositoryException {
        LOGGER.debug("Inside User Repositiry query method.");
        final SqlSpecification sqlSpecification = (SqlSpecification) specification;
        final List<User> userList = new ArrayList<>();
        try {
            Connection dbConnection = connectionPool.getConnection();
            try (PreparedStatement ps = dbConnection.prepareStatement(sqlSpecification.toSqlQuery());
                 ResultSet rs = ps.executeQuery()) {

                LOGGER.debug("ps string: {}", sqlSpecification.toSqlQuery());
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

                    userList.add(user);
                }

                connectionPool.releaseConnection(dbConnection);

                return userList;
            }

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in user query", e);
        }
    }

    @Override
    public Status getStatus(User item) throws RepositoryException {
        return null;
    }

    @Override
    public void undelete(User item) throws RepositoryException {

    }
}
