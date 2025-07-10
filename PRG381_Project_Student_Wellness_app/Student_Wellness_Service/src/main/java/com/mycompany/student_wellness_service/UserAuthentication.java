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
    static public User LogIn(int studentNumber, String password){
      //query
        System.out.println("UserAuthentication: Attempting login for student number: " + studentNumber);
        String query = "SELECT student_number, name, surname, email, password FROM users WHERE student_number = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, studentNumber);
            statement.setString(2, password);
            System.out.println("UserAuthentication: Executing query for student_number: " + studentNumber + ", password: " + password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                System.out.println("UserAuthentication: Login successful for " + name + " " + surname);
                return new User(studentNumber, name, surname, email);
            } else {
                System.out.println("UserAuthentication: No matching user found for student_number: " + studentNumber + " and password");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("UserAuthentication: Database error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }
}
