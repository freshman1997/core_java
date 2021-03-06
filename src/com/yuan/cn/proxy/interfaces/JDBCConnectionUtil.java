package com.yuan.cn.proxy.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class JDBCConnectionUtil {
    private static String url;
    private static String username = null;
    private static String password = null;

    private static Connection connection = null;

    static {
        InputStream resourceAsStream = JDBCConnectionUtil.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

            Class.forName(properties.getProperty("driver"));
            assert resourceAsStream != null;
            resourceAsStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {

                connection = (username == null && password == null) ?  DriverManager.getConnection(url) :
                        DriverManager.getConnection(url, username, password);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}
