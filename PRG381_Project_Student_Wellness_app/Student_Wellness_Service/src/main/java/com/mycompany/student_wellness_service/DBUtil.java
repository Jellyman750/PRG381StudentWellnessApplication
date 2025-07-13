/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.student_wellness_service;

/**
 *
 * @author tarina
 */
//this was used to hash all the existing 
import java.sql.*;
public class DBUtil {
  //url to connect to db
     static final String URL = "jdbc:postgresql://localhost:5432/BC_Wellness_db?currentSchema=public&user=postgres&password=password";

    public static Connection getConnection() throws SQLException {
       try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL);
           // System.out.println("DBUtil: Connection successful"); //debug
            return conn;
        } catch (ClassNotFoundException e) {
           // System.out.println("DBUtil: Postgres Driver not found");// for debug
            throw new SQLException("PostgreSQL Driver not found", e);
        } catch (SQLException e) {
           // System.out.println("Connection failed: " + e.getMessage());//debug
            throw e;
        }
    }  
}

