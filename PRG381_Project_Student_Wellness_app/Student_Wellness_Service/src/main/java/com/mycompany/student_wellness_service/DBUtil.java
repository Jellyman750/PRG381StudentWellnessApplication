/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.student_wellness_service;

/**
 *
 * @author tarina
 */

import java.sql.*;
public class DBUtil {
  //url to connect to db
     static final String URL = "jdbc:postgresql://localhost:5432/BC_Wellness_db?currentSchema=public&user=postgres&password=password";

    public static Connection getConnection() throws SQLException {
       try {
            Class.forName("org.postgresql.Driver");
            System.out.println("DBUtil: Connecting to database...");
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("DBUtil: Connection successful");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("DBUtil: PostgreSQL Driver not found");
            throw new SQLException("PostgreSQL Driver not found", e);
        } catch (SQLException e) {
            System.out.println("DBUtil: Connection failed: " + e.getMessage());
            throw e;
        }
    }  
}

