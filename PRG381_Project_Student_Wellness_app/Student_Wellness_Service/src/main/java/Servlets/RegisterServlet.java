package Servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentNumber = request.getParameter("student_number");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

            if (studentNumber == null || studentNumber.isEmpty() ||
            name == null || name.isEmpty() ||
            surname == null || surname.isEmpty() ||
            email == null || email.isEmpty() || !email.contains("@") ||
            phone == null || phone.isEmpty() || !phone.matches("\\d{10,}") ||
            password == null || password.isEmpty()) {

            request.setAttribute("error", "Please fill in all fields correctly.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
         try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/wellness_db", 
                "postgres", 
                "yourpassword"  // change to your real password
            );

            //  Check if user already exists
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT * FROM users WHERE email = ? OR student_number = ?"
            );
            checkStmt.setString(1, email);
            checkStmt.setString(2, studentNumber);
            ResultSet rs = checkStmt.executeQuery();

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wellness_db", "postgres", "yourpassword");

            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE email = ? OR student_number = ?");
            checkStmt.setString(1, email);
            checkStmt.setString(2, studentNumber);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                request.setAttribute("error", "User already exists.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                PreparedStatement insertStmt = conn.prepareStatement(
                        "INSERT INTO users (student_number, name, surname, email, phone, password) VALUES (?, ?, ?, ?, ?, ?)"
                );
                insertStmt.setString(1, studentNumber);
                insertStmt.setString(2, name);
                insertStmt.setString(3, surname);
                insertStmt.setString(4, email);
                insertStmt.setString(5, phone);
                insertStmt.setString(6, password);
                insertStmt.executeUpdate();

                response.sendRedirect("login.jsp?success=1");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=1");
        }
    }
}
