package by.kurlovich.musicshop.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);
    private static final String DB_PROPERTIES_FILE = "db.properties";
    private static ConnectionPool instance;
    private BlockingQueue<Connection> connectionsQueue;
    private String dbDriver;
    private String connectionUrl;
    private String userName;
    private String password;
    private int maxConnections;
    private AtomicInteger currentConnections;
    private Connection connection = null;

    private ConnectionPool() throws ConnectionException {
        paramInit();
        driverInit();
        connectionsInit();
    }

    public static synchronized ConnectionPool getInstance() throws ConnectionException {
        if (instance == null) {
            instance = new ConnectionPool();
        }

        return instance;
    }

    public synchronized Connection getConnection() throws ConnectionException {
        try {
            if (!connectionsQueue.isEmpty()) {
                connection = connectionsQueue.take();
                if (connection.isClosed()) {
                    connection = getConnection();
                }
            } else {
                return null;
            }

            currentConnections.decrementAndGet();

            LOGGER.debug("Get connection. Connections left: {}.", currentConnections);

            return connection;
        } catch (Exception e) {
            throw new ConnectionException("Problems with getting connections from pool", e);
        }
    }

    public synchronized void releaseConnection(Connection connection) throws ConnectionException {
        if (connection != null && currentConnections.intValue() < maxConnections) {
            try {
                connectionsQueue.put(connection);
                currentConnections.incrementAndGet();

                LOGGER.debug("Release connection. Connections left: {}.", currentConnections);
            } catch (InterruptedException e) {
                throw new ConnectionException("Problems in connection queue.", e);
            }
        }
    }

    private void paramInit() throws ConnectionException {
        try {
            InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_FILE);
            Properties properties = new Properties();
            if (properties != null) {

                properties.load(inputStream);

                this.dbDriver = properties.getProperty("dbDriver");
                this.connectionUrl = properties.getProperty("url");
                this.userName = properties.getProperty("username");
                this.password = properties.getProperty("password");
                this.maxConnections = Integer.parseInt(properties.getProperty("maxConnections"));
                this.currentConnections = new AtomicInteger(maxConnections);
                this.connectionsQueue = new LinkedBlockingQueue<>(maxConnections);
            } else {
                throw new ConnectionException("Can't find " + DB_PROPERTIES_FILE + " file with db properties.");
            }
            LOGGER.debug("db pool parameters initialized.");
        } catch (IOException e) {
            throw new ConnectionException("Can't load parameters in connection pool", e);
        }
    }

    private void driverInit() throws ConnectionException {
        try {
            Class.forName(dbDriver).newInstance();

            LOGGER.debug("db driver initialized.");
        } catch (Exception e) {
            throw new ConnectionException("Can't load db driver in connection pool", e);
        }
    }

    private void connectionsInit() throws ConnectionException {
        for (int i = 0; i < maxConnections; i++) {
            try {
                if (newConnection() != null) {
                    connectionsQueue.put(newConnection());
                }

                LOGGER.debug("db pool {} connections initialized.", maxConnections);
            } catch (InterruptedException e) {
                throw new ConnectionException("Problems in connection queue.", e);
            }
        }
    }

    private Connection newConnection() {
        try {
            connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException e) {
            return null;
        }

        return connection;
    }
}
