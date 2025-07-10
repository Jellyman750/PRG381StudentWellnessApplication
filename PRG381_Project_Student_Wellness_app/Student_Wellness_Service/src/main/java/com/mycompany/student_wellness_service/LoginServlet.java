/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.student_wellness_service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tarina
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("LoginServlet: Received POST request");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("Login attempt: " + username + ", " + password);

        // Input validation
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("LoginServlet: Empty username or password");
            request.setAttribute("error", "Student number and password cannot be empty.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            int studentNumber = Integer.parseInt(username);
            System.out.println("LoginServlet: Parsed student number: " + studentNumber);
            User user = UserAuthentication.LogIn(studentNumber, password);
            if (user != null) {
                System.out.println("LoginServlet: Login successful for student: " + user.getName());
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("dashboard.jsp");
            } else {
                System.out.println("LoginServlet: Invalid credentials");
                request.setAttribute("error", "Invalid student number or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            System.out.println("LoginServlet: Invalid student number format: " + username);
            request.setAttribute("error", "Student number must be numeric.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("LoginServlet: Unexpected error: " + e.getMessage());
            request.setAttribute("error", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        
    }
    @Override
    public String getServletInfo() {
        return "Handles user login for the Student Wellness Management System";
    }
   

}
