<%-- 
    Document   : login
    Created on : 13 Jul 2025, 17:05:11
    Author     : kyles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Login - Student Wellness Service</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f9fafb; padding: 50px; }
        form { max-width: 350px; margin: auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px #ccc; }
        label { display: block; margin-top: 15px; font-weight: bold; }
        input[type="email"], input[type="password"] {
            width: 100%; padding: 8px; margin-top: 5px; box-sizing: border-box;
        }
        button {
            margin-top: 20px; padding: 10px; width: 100%; background-color: #007bff; border: none; color: white; font-size: 16px;
            cursor: pointer; border-radius: 5px;
        }
        button:hover {
            background-color: #0056b3;
        }
        .error { color: red; margin-top: 10px; text-align: center; }
        .success { color: green; margin-top: 10px; text-align: center; }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Login</h2>
    <form action="LoginServlet" method="post">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required />

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required />

        <button type="submit">Login</button>
    </form>
</body>
</html>