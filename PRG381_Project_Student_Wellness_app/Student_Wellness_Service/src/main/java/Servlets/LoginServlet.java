/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servlets;

//imports
import com.mycompany.student_wellness_service.User;
import com.mycompany.student_wellness_service.UserAuthentication;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author tarina
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //gets username/email and password
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //System.out.println("Login attempt: " + username + ", " + password); //for debuging

        // input validation
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
           // System.out.println("LoginServlet: Empty username or password"); //used for debugging
            request.setAttribute("error", "Student number and password cannot be empty.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            User user = UserAuthentication.LogIn(username, password);
            
            if (user != null) { //if the user is not null then log in
                //System.out.println("LoginServlet: Login successful for student: " + user.getName());//debug
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("dashboard.jsp");//opens the dashboard page
            } else { //error if the user value is null
                //System.out.println("LoginServlet: invalid credentials");
                request.setAttribute("error", "Invalid email or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) { // displays error
            //System.out.println("LoginServlet: unexpected error: " + e.getMessage());
            request.setAttribute("error", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response); 
        } 
    }
    @Override
    public String getServletInfo() {
        return "Handles user login for the Student Wellness Management System";
    }
    
}
