package com.yuan.cn.mybatis;

import com.yuan.cn.mybatis.pojo.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Crazy
 * @date 2018/12/1
 */
public class MyBaseExecutor implements MyExecutor{
    private static String url;
    private static String username;
    private static String password;
    static
    {
        InputStream is = MyBaseExecutor.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
            String driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public <T> T query(String statement) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url,username,password);
            String sql = statement;
            System.out.println(sql);
            preparedStatement = connection.prepareStatement(sql);
            if(sql.startsWith("insert"))
            {
                preparedStatement.executeUpdate();
                return null;
            }
            resultSet = preparedStatement.executeQuery();
            User user = null;
            if(resultSet.next())
            {
                user = new User();
                user.setId(resultSet.getInt("sid"));
                user.setUsername(resultSet.getString("sname"));
                user.setUsername(resultSet.getString("saddress"));
                user.setEmail(resultSet.getString("slikes"));
            }
            return (T)user;
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
            return null;
        }

    }
}
