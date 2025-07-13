<%-- 
    Document   : register
    Created on : 13 Jul 2025, 17:05:28
    Author     : kyles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Register - Student Wellness Service</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f9fafb; padding: 50px; }
        form { max-width: 400px; margin: auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px #ccc; }
        label { display: block; margin-top: 15px; font-weight: bold; }
        input[type="text"], input[type="email"], input[type="password"], input[type="tel"] {
            width: 100%; padding: 8px; margin-top: 5px; box-sizing: border-box;
        }
        button {
            margin-top: 20px; padding: 10px; width: 100%; background-color: #28a745; border: none; color: white; font-size: 16px;
            cursor: pointer; border-radius: 5px;
        }
        button:hover {
            background-color: #218838;
        }
        .error { color: red; margin-top: 10px; }
        .success { color: green; margin-top: 10px; }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Register</h2>
    <form action="RegisterServlet" method="post">
        <label for="student_number">Student Number:</label>
        <input type="text" id="student_number" name="student_number" required />

        <label for="name">First Name:</label>
        <input type="text" id="name" name="name" required />

        <label for="surname">Surname:</label>
        <input type="text" id="surname" name="surname" required />

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required />

        <label for="phone">Phone Number:</label>
        <input type="tel" id="phone" name="phone" pattern="[0-9]{10}" placeholder="e.g. 0123456789" required />

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required />

        <button type="submit">Register</button>
    </form>
</body>
</html>