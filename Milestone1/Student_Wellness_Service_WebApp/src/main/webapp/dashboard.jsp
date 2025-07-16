<%-- 
    Document   : home
    Created on : 10 Jul 2025, 12:51:36
    Author     : tarina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="com.mycompany.student_wellness_service.User"%>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Dashboard - Student Wellness Service</title>

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">

  <style>
    * {
      box-sizing: border-box;
    }

    body, html {
      margin: 0; 
      padding: 0; 
      height: 100%;
      font-family: 'Roboto', Arial, sans-serif;
      background: url('images/background1.jpg') no-repeat center center fixed;
      background-size: cover;
      background-color: #667eea;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }

    .container {
      background: #f2f2f2;
      padding: 40px 60px;
      border-radius: 12px;
      box-shadow: 0 8px 24px rgba(0,0,0,0.2);
      max-width: 600px;
      width: 90%;
      text-align: center;
      border: 3px solid black;
    }

    img.logo {
      max-height: 60px;
      width: auto;
      margin-bottom: 20px;
      display: block;
      margin-left: auto;
      margin-right: auto;
    }

    h1 {
      font-size: 28px;
      margin-bottom: 20px;
      color: #6a0dad;
    }

    p {
      font-size: 16px;
      line-height: 1.6;
      color: #444;
      margin-bottom: 30px;
    }

    button {
      padding: 14px 28px;
      font-size: 16px;
      background-color: #667eea;
      color: white;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      font-weight: bold;
      transition: background-color 0.3s ease;
    }

    button:hover {
      background-color: #5a67d8;
    }
  </style>
</head>
<body>
  <div class="container">
    <!-- Optional: Logo -->
    <img src="images/logo.png" alt="Student Wellness Logo" class="logo" />

    <h1>Welcome, <%= user.getName() %> <%= user.getSurname() %>!</h1>
    <p>
      Welcome to your Student Wellness Dashboard. Here, you can manage your wellness journey, stay informed about upcoming resources, and access support when you need it.
    </p>
    <form action="logout" method="post">
      <button type="submit">Logout</button>
    </form>
  </div>
</body>
</html>