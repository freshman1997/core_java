package com.yuan.cn.jdbc.sqlite;




import java.sql.*;

public class SQLiteHelper {

     Connection connection;
     Statement statement;
     ResultSet resultSet;
     String dbFilePath = "src/user.db";

    public SQLiteHelper(){


        try {
            connection = getConnection(dbFilePath);
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            statement.executeUpdate("create table if not exists user(id, name, password);");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection(String dbFilePath) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:"+dbFilePath);
        return connection;
    }


    public void test() throws SQLException {

    }

    public static void main(String[] args) throws SQLException {
        SQLiteHelper helper = new SQLiteHelper();
        helper.statement.executeUpdate("insert into user values (1, '你好', 'tomcat');");
        helper.statement.executeUpdate("insert into user values (2, 'smith', 'share');");
        helper.statement.executeUpdate("insert into user values (3, 'admin', 'admin');");
        helper.connection.commit();

        helper.resultSet = helper.statement.executeQuery("select * from user");
        while (helper.resultSet.next()){
            System.out.println(helper.resultSet.getString("name"));
        }
    }
}
