package by.kurlovich.musicshop.dbconnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class DBConnection implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBConnection.class);
    private ConnectionPool pool;
    private Connection connection;

    public DBConnection() {
        try {
            pool = ConnectionPool.getInstance();
            this.connection = pool.getConnection();
        } catch (ConnectionException e) {
            LOGGER.error("Can't create DBConnection.\n{}", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws ConnectionException {
        try {
            pool.releaseConnection(connection);
        } catch (Exception e) {
            throw new ConnectionException("Exception in autoclose connection.\n" + e, e);
        }
    }
}
