/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Appointment;
import model.Counselor;
import model.Feedback;


/**
 *
 * @author tarina
 */
public class DBConnection {
    
    //driver
    private static final String DRIVER="org.apache.derby.jdbc.EmbeddedDriver";
    //url
    private static final String URL="jdbc:derby:BC_Wellness;create=true";
    //connection object
    Connection conn;
    
    //constructor
    public DBConnection(){}
    
    
    //establishes a connecton to the javadb
    public void connect() throws ClassNotFoundException{
        try{
            Class.forName(DRIVER);
            this.conn=DriverManager.getConnection(URL);
            if(this.conn !=null){
                System.out.println("Connected to DB");//debugging
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    //returns the database connection
    public Connection getConnection() {
        return this.conn;
    }
    //test if tables already exist
    public boolean tableExists(String tableName){
        try {
            DatabaseMetaData meta = this.conn.getMetaData();
            try (ResultSet rs = meta.getTables(null, null, tableName.toUpperCase(), new String[]{"TABLE"})) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
//Disconnect method-cll when the application is closed-dont know if needed
  public void disconnect() {
    try {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            System.out.println("Disconnected from DB");
        }
    } 
    catch (SQLException ex) {
        ex.printStackTrace();
    }}     
}

