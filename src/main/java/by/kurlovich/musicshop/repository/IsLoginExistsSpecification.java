package by.kurlovich.musicshop.repository;

import by.kurlovich.musicshop.repository.impl.UserRepository;
import by.kurlovich.musicshop.util.DbConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IsLoginExistsSpecification implements IsExistsSpecification {
    final String IS_LOGIN_EXISTS = "SELECT COUNT(*) AS TotalUsers FROM users WHERE login=?";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    private Connection dbConnection;
    private String field;

    public IsLoginExistsSpecification(String field) {
        this.field = field;
        dbConnection = DbConnection.getConnection();
    }

    @Override
    public boolean exists() throws RepositoryException {
        final int LOGIN = 1;
        final int TOTAL_CLIENTS = 1;

        try (PreparedStatement ps = dbConnection.prepareStatement(IS_LOGIN_EXISTS)){

            ps.setString(LOGIN, field);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (Integer.parseInt(rs.getString(TOTAL_CLIENTS)) > 0) {
                        return true;
                    }
                }
                return false;
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RepositoryException("Exception in addNewClient", e);
        }
    }
}
