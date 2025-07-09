<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

           <div class="form-group">
               <a href="login.jsp" class="back-link">← Back to Home</a>
           </div>
       </form>

        <div class="back-link">
            <p><a href="index.jsp">← Back to Home</a></p>
        </div>
    </div>
</body>
</html>
