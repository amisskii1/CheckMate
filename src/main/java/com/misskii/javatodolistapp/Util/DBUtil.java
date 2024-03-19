package com.misskii.javatodolistapp.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String DB_DRIVER_CLASS =  System.getenv("DATABASE_DRIVER");;
    private static final String URL = System.getenv("DATABASE_URL");;
    private static final String USERNAME= System.getenv("DATABASE_USERNAME");;
    private static final String PASSWORD= System.getenv("DATABASE_PASSWORD");;

    private static Connection connection = null;

    static{
        try {
            Class.forName(DB_DRIVER_CLASS);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
