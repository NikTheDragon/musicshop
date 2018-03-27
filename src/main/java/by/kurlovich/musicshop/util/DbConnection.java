package by.kurlovich.musicshop.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbConnection {
    private static Connection dbConnection = null;

    public static Connection getConnection() {
        if (dbConnection != null) {
            return dbConnection;
        } else {
            try {
                InputStream inputStream = DbConnection.class.getClassLoader()
                        .getResourceAsStream("db.properties");
                Properties properties = new Properties();
                if (properties != null) {
                    properties.load(inputStream);

                    String dbDriver = properties.getProperty("dbDriver");
                    String connectionUrl = properties.getProperty("url");
                    String userName = properties.getProperty("username");
                    String password = properties.getProperty("password");

                    Class.forName(dbDriver).newInstance();
                    dbConnection = DriverManager.getConnection(connectionUrl,
                            userName, password);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dbConnection;
        }
    }
}
