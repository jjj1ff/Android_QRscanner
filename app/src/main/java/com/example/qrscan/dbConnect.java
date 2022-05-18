package com.example.qrscan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnect {
    private final static String driver ="com.mysql.jdbc.Driver";
    private final static String url ="jdbc:mysql://localhost:3306/medicine?useSSL=false&serverTimezone=Asia/Shanghai";
    private final static String username ="root";
    private final static String password ="123456";

    Connection mConnection = null;
    Statement mStatement = null;

    static {
        try{
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Error1");
            System.out.println(e.getMessage());
        }
    }
    public Connection getConnection(){

        try{
            mConnection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException throwables) {
            System.out.println("Error2");
            System.out.println(throwables.getMessage());
        }
        return mConnection;
    }

    public Statement getStatement(){
        try {
            mStatement = mConnection.createStatement();
        } catch (SQLException throwables) {
            System.out.println("Error3");
            System.out.println(throwables.getMessage());
        }
        return mStatement;
    }
}
