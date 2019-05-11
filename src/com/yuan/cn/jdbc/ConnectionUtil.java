package com.yuan.cn.jdbc;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class ConnectionUtil
{
    private static String driverClass;
    private static String url;
    private static String username;
    private static String password;
    private static Connection conn;

    static
    {
        Properties properties = new Properties();
        InputStream inputStream = ConnectionUtil.class.getClassLoader().getResourceAsStream("com/yuan/cn/jdbc/db.properties");
        try {
            if(inputStream != null) {
                properties.load(inputStream);
                url = properties.getProperty("jdbc.url");
                driverClass = properties.getProperty("jdbc.driverClass");
                username = properties.getProperty("jdbc.username");
                password = properties.getProperty("jdbc.password");
                Class.forName(driverClass);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized static  Connection getConnection() throws SQLException {
        if(conn != null)
        {
            return conn;
        }
        conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public static void main(String[] args) throws SQLException {
        Statement statement = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        String sql = "insert into  video_details(id,caption,img,title) values(?,?,?,?)";
        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,2);
            preparedStatement.setString(2,"站在山上看风景的人");
            File f = new File("C:\\Users\\Crazy\\Pictures\\1.jpg");
            preparedStatement.setBinaryStream(3, new FileInputStream(f), f.length());
            preparedStatement.setString(4,"http://localhost:8888/resources/SpringBoot2.0不容错过的新特性 WebFlux响应式编程/");
            preparedStatement.executeUpdate();
//            statement = getConnection().createStatement();
//            rs = statement.executeQuery("select * from t_user");
//            while (rs.next())
//            {
//                int id = rs.getInt("id");
//                String username = rs.getString("username");
//                String password = rs.getString("password");
//                System.out.println("id="+id+", username="+username+", password="+password);
//            }
        }catch (SQLException | FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
