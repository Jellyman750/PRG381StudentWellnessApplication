
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Student Wellness App</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Login</h1>
        <form action="login_process.jsp" method="post">
            <label for="username">Username / Email:</label>
            <input type="text" id="username" name="username" placeholder="Enter your email" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Enter your password" required>

            <input type="submit" value="Login">
        </form>
        <div class="back-link">
            <p><a href="index.jsp">← Back to Home</a></p>
        </div>
    </div>
</body>

</html>

