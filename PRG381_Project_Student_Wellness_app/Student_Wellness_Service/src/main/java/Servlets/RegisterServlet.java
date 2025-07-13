package Servlets;

//imports
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.mycompany.student_wellness_service.DBUtil;//import database connection
import javax.servlet.annotation.WebServlet;
import org.mindrot.jbcrypt.BCrypt;


@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get user inputs
        String studentNumber = request.getParameter("studentNumber").trim();
        String name = request.getParameter("firstName").trim();
        String surname = request.getParameter("lastName").trim();
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();
        String password = request.getParameter("password").trim();
        
        
        
        //input validation
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
         try (Connection conn=DBUtil.getConnection()){

            //  Check if user already exists
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT * FROM users WHERE email = ? OR student_number = ?"
            );
            checkStmt.setString(1, email);
            checkStmt.setInt(2,  Integer.parseInt(studentNumber));
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                //System.out.println("User already exists.");//debug
                request.setAttribute("error", "User already exists.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                PreparedStatement insertStmt = conn.prepareStatement(
                        "INSERT INTO users (student_number, name, surname, email, phone, password) VALUES (?, ?, ?, ?, ?, ?)"
                );
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());//hash the password  
                
                insertStmt.setInt(1,  Integer.parseInt(studentNumber));
                insertStmt.setString(2, name);
                insertStmt.setString(3, surname);
                insertStmt.setString(4, email);
                insertStmt.setString(5, phone);
                insertStmt.setString(6, hashedPassword);
                insertStmt.executeUpdate();
                //System.out.println("User inserted successfully.");//debugging    
                response.sendRedirect("login.jsp?success=1");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
            //response.sendRedirect("register.jsp?error=1");
        }
    }
}
