package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.dbconnection.ConnectionException;
import by.kurlovich.musicshop.dbconnection.DBConnection;
import by.kurlovich.musicshop.entity.Mix;
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

public class MixRepository implements Repository<Mix> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MixRepository.class);
    private static final String GET_STATUS = "SELECT status FROM mixes WHERE name=?";
    private static final String SET_STATUS = "UPDATE mixes SET status='active' WHERE name=? AND genre=(SELECT id FROM genres WHERE name=?) AND year=?";
    private static final String ADD_MIX = "INSERT INTO mixes (name, genre, year, status) VALUES (?,(SELECT id FROM genres WHERE name=?),?,?)";
    private static final String DELETE_MIX = "UPDATE mixes SET status='deleted' WHERE name=?";
    private static final String UPDATE_MIX = "UPDATE mixes SET name=?, genre=(SELECT id FROM genres WHERE name=?), year=? WHERE id=?";

    public MixRepository() {
        LOGGER.debug("Creating mix Repository class.");
    }

    @Override
    public void add(Mix item) throws RepositoryException {
        try (DBConnection dbConnection = new DBConnection()) {
            LOGGER.debug("Adding new mix {}", item.getName());
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(ADD_MIX)) {
                ps.setString(1, item.getName());
                ps.setString(2, item.getGenre());
                ps.setString(3, item.getYear());
                ps.setString(4, item.getStatus());

                ps.executeUpdate();
            }
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in add of MixRepository.\n" + e, e);
        }
    }

    @Override
    public void delete(Mix item) throws RepositoryException {
        try (DBConnection dbConnection = new DBConnection()) {
            LOGGER.debug("Deleting mix {}", item.getName());
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(DELETE_MIX)) {
                ps.setString(1, item.getName());

                ps.executeUpdate();
            }
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in delete of MixRepository.\n" + e, e);
        }
    }

    @Override
    public void update(Mix item) throws RepositoryException {
        try (DBConnection dbConnection = new DBConnection()) {
            LOGGER.debug("Updating mix {}", item.getName());
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_MIX)) {
                ps.setString(1, item.getName());
                ps.setString(2, item.getGenre());
                ps.setString(3, item.getYear());
                ps.setString(4, item.getId());

                ps.executeUpdate();
            }
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in update of MixRepository.\n" + e, e);
        }
    }

    @Override
    public List<Mix> query(Specification specification) throws RepositoryException {
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        List<Mix> mixList = new ArrayList<>();

        try (DBConnection dbConnection = new DBConnection()) {
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(sqlSpecification.toSqlQuery());
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Mix mix = new Mix();

                    mix.setId(rs.getString(1));
                    mix.setName(rs.getString(2));
                    mix.setGenre(rs.getString(3));
                    mix.setYear(rs.getString(4));
                    mix.setStatus(rs.getString(5));

                    mixList.add(mix);
                }
                return mixList;
            }

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in query of MixRepository.\n" + e, e);
        }
    }

    @Override
    public Status getStatus(Mix item) throws RepositoryException {
        final int STATUS = 1;

        try (DBConnection dbConnection = new DBConnection()) {
            String status = "";
            LOGGER.debug("Checking mix {} status.", item.getName());
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(GET_STATUS)) {

                ps.setString(1, item.getName());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        status = (rs.getString(STATUS));
                    }
                }
            }

            return Status.getStatus(status);

        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in get getStatus of MixRepository.\n" + e, e);
        }
    }

    @Override
    public void undelete(Mix item) throws RepositoryException {
        try (DBConnection dbConnection = new DBConnection()) {
            LOGGER.debug("Set mix {} status to active.", item.getName());
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(SET_STATUS)) {
                ps.setString(1, item.getName());
                ps.setString(2, item.getGenre());
                ps.setString(3, item.getYear());

                ps.executeUpdate();
            }
        } catch (SQLException | ConnectionException e) {
            throw new RepositoryException("Exception in undelete of MixRepository.\n" + e, e);
        }
    }
}
