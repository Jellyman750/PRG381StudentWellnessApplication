/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.student_wellness_service;

/**
 *
 * @author tarina
 */
//impot
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;


public class UserAuthentication {
    //function that verifies if the studentnumber and password match and returns a boolean
    static public User LogIn(String email, String password){
        //System.out.println("UserAuthentication: Attempting login for student: " + email);//debug
        
        //query
        String query = "SELECT student_number, name, surname, email, password FROM users WHERE email = ?";
        try (Connection conn = DBUtil.getConnection();//connect to db
            PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (BCrypt.checkpw(password, storedHash)) { //compares the entered password  to the stored hashed version
                    int studentNumber = rs.getInt("student_number");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");

                    return new User(studentNumber, name, surname, email); // sucesfull so returns user obj
                } else {
                    return null; // incorect password
                }
            } else {
                return null; // email not found
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
