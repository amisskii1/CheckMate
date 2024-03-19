package com.misskii.javatodolistapp.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static final String DB_DRIVER_CLASS = "jdbc.driver";
    private static final String URL = "jdbc.url";
    private static final String USERNAME="jdbc.username";
    private static final String PASSWORD="jdbc.password";

    private static Connection connection = null;

    static{
        try {
            Properties properties = new Properties();
            InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("database.properties");
            if (inputStream == null) {
                throw new IOException("database.properties file not found");
            }
            properties.load(inputStream);
            Class.forName(properties.getProperty(DB_DRIVER_CLASS));
            connection = DriverManager.getConnection(properties.getProperty(URL), properties.getProperty(USERNAME), properties.getProperty(PASSWORD));
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
