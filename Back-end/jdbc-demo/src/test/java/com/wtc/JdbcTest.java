package com.wtc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

public class JdbcTest {

    /**
     * JDBC 入门
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void testUpdate() throws ClassNotFoundException, SQLException {
        // 1. 注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2. 获取连接
        String url = "jdbc:mysql://localhost:3306/web_demo";
        String user = "root";
        String password = "mysql";
        Connection connection = DriverManager.getConnection(url, user, password);

        // 3. 获取执行sql语句的对象
        Statement statement = connection.createStatement();

        // 4. 执行sql语句
        int i = statement.executeUpdate("update user set age = 25 where id = 1"); // DML
        System.out.println("SQL 执行影响的记录数：" + i);

        // 5. 释放资源
        statement.close();
        connection.close();
    }

    @Test
    public void testSelect() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/web_demo";
        String user = "root";
        String password = "mysql";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        // 预编译 SQL 语句
        String sql = "select id, username, password, name, age from user where username = ? and password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, "daqiao");
        preparedStatement.setString(2, "123456");

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            User u = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getInt("age"));
            System.out.println(u);
        }

        // 释放资源
        rs.close();
        preparedStatement.close();
        connection.close();
    }
}
