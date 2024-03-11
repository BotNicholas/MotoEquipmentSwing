package com.nicholas.model.dao.connection;

import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBconnection {
    private static java.sql.Connection connection;
    private static Properties properties = new Properties();

    public DBconnection() {
    }

    public static void initDBconnection(){
        try {
            properties.load(new FileReader("src/main/resources/credentials.properties"));
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("login"), properties.getProperty("password"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static java.sql.Connection getDBconnection() throws SQLException {
        return connection;
    }

    public static boolean closeDBconnection(){
        try {
            connection.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
