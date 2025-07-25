package dbmanager;

import java.sql.*;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        try {
            // Load properties from the file
            Properties props = new Properties();

            // Load from resources folder
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
            props.load(input);

            // Get the properties
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }

        try {
            if (instance.connection.isClosed()) {
                instance = null;
                return getInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
