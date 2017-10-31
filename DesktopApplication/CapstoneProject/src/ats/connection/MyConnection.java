/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Chi Hieu
 */
public class MyConnection {
    public static Connection getMyConnection() throws Exception{
//        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ats","root","1234");
        return conn;
    }
}
