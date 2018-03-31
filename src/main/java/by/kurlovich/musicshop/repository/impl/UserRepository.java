package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.repository.IsExistsSpecification;
import by.kurlovich.musicshop.repository.Repository;

import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.util.DbConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserRepository implements Repository<User> {
    final String ADD_CLIENT = "INSERT INTO users (name,surname,login,password,email) VALUES (?,?,?,?,?)";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    private Connection dbConnection;

    public UserRepository() {
        dbConnection = DbConnection.getConnection();
    }


    @Override
    public boolean add(User user) throws RepositoryException {
        final int NAME = 1;
        final int SURNAME = 2;
        final int LOGIN = 3;
        final int PASSWORD = 4;
        final int EMAIL = 5;

        try (PreparedStatement ps = dbConnection.prepareStatement(ADD_CLIENT)) {
            ps.setString(NAME, user.getName());
            ps.setString(SURNAME, user.getSurname());
            ps.setString(LOGIN, user.getLogin());
            ps.setString(PASSWORD, user.getPassword());
            ps.setString(EMAIL, user.getEmail());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RepositoryException("Exception in addNewClient", e);
        }
    }

    @Override
    public boolean isExists(IsExistsSpecification specification) throws RepositoryException {
        boolean result = specification.exists();
        LOGGER.debug("Login result= {}", result);
        return result;
    }
}
