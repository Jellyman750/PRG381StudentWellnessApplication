<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <div style="color:red;"><%= error %></div>
<% } %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - Student Wellness App</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Student Registration</h1>
       <form action="register" method="post">
           <div class="form-group">
               <label for="studentNumber">Student Number:</label>
               <input type="text" id="studentNumber" name="studentNumber" placeholder="e.g. 20251234" required>
           </div>

           <div class="form-group">
               <label for="firstName">First Name:</label>
               <input type="text" id="firstName" name="firstName" placeholder="John" required>
           </div>

           <div class="form-group">
               <label for="lastName">Last Name:</label>
               <input type="text" id="lastName" name="lastName" placeholder="Doe" required>
           </div>

           <div class="form-group">
               <label for="email">Email Address:</label>
               <input type="email" id="email" name="email" placeholder="john.doe@example.com" required>
           </div>

           <div class="form-group">
               <label for="phone">Phone Number:</label>
               <input type="tel" id="phone" name="phone" placeholder="e.g. 0821234567" required>
           </div>

           <div class="form-group">
               <label for="password">Password:</label>
               <input type="password" id="password" name="password" placeholder="Create a strong password" required>
           </div>

           <div class="form-group">
               <button type="submit" class="btn">Register</button>
           </div>
           
       </form>

        <div class="back-link">
            <p><a href="index.jsp">← Back to Home</a></p>
        </div>
    </div>
 <script>
        function validateRegistrationForm() {
            const studentNumber = document.getElementById("studentNumber").value.trim();
            const firstName = document.getElementById("firstName").value.trim();
            const lastName = document.getElementById("lastName").value.trim();
            const email = document.getElementById("email").value.trim();
            const phone = document.getElementById("phone").value.trim();
            const password = document.getElementById("password").value.trim();

            if (!studentNumber || !firstName || !lastName || !email || !phone || !password) {
                alert("Please fill in all fields.");
                return false;
            }

            if (!email.includes("@") || !email.includes(".")) {
                alert("Please enter a valid email address.");
                return false;
            }

            if (!/^[0-9]{10,}$/.test(phone)) {
                alert("Phone number must contain at least 10 digits.");
                return false;
            }

            if (password.length < 6) {
                alert("Password must be at least 6 characters long.");
                return false;
            }

            return true; // allow form to submit
        }
    </script>
</body>
</html>
