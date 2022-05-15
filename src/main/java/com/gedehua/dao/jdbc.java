package com.gedehua.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jdbc {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
        String userName = "gedehua";
        String passWord = "2002726+abc";
        Connection connection = DriverManager.getConnection(url, userName, passWord);
        return  connection;
    }

}
