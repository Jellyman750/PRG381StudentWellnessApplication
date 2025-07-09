<%-- 
    Document   : register.jsp
    Created on : 09 Jul 2025, 18:21:58
    Author     : kyles
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - Student Wellness App</title>
</head>
<body>
    <h1>Register</h1>
    <form action="register_process.jsp" method="post">
        <label for="username">Username:</label><br>
        <input type="text" id="username" name="username" required><br><br>

        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password" required><br><br>

        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <input type="submit" value="Register">
    </form>
    <p><a href="index.jsp">Back to Home</a></p>
</body>
</html>
