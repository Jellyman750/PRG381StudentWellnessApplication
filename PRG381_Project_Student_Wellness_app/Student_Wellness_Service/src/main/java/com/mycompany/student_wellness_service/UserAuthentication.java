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


public class UserAuthentication {
    //function that verifies if the studentnumber and password match and returns a boolean
    static public User LogIn(String email, String password){
        //System.out.println("UserAuthentication: Attempting login for student: " + email);//debug
        //query
        String query = "SELECT student_number, name, surname, email, password FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();//connect to db
            PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();//execute query
            if (rs.next()) { //if there is a row then return the user
                int studentNumber=rs.getInt("student_number");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                
                //System.out.println("UserAuthentication: Login successful for " + name + " " + surname);//for debugging
                return new User(studentNumber, name, surname, email);
            } else {
                //System.out.println("UserAuthentication: No matching user found for student_number: " + email + " and password");/debug
                return null;
            }
        } catch (SQLException e) {
            //System.out.println("UserAuthentication: Database error: " + e.getMessage()); debugging
            e.printStackTrace();
            return null;
        }catch (Exception e) { //other 
            System.out.println("UserAuthentication: Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }
}
