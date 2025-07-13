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

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">

  <style>
    /* Reset & base */
    * {
      box-sizing: border-box;
    }

    body, html {
      margin: 0; 
      padding: 0; 
      height: 100%;
      font-family: 'Roboto', Arial, sans-serif;

      /* Background image */
      background: url('images/background1.jpg') no-repeat center center fixed;
      background-size: cover;

      /* Fallback background color */
      background-color: #667eea;

      color: #333;

      display: flex;
      justify-content: center;
      align-items: center;

      height: 100vh;
    }

    .container {
      background: #f2f2f2; /* light grey background */
      padding: 40px 60px;
      border-radius: 12px;
      box-shadow: 0 8px 24px rgba(0,0,0,0.2);
      text-align: center;
      max-width: 450px;
      width: 90%;
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

    h2 {
      margin-bottom: 24px;
      font-weight: 700;
      color: #6a0dad; /* purple to match index and login */
    }

    form {
      margin: 0;
      text-align: left;
    }

    label {
      display: block;
      margin-top: 15px;
      font-weight: 700;
    }

    input[type="text"], input[type="email"], input[type="password"], input[type="tel"] {
      width: 100%;
      padding: 10px;
      margin-top: 5px;
      box-sizing: border-box;
      border-radius: 6px;
      border: 1px solid #ccc;
      font-size: 16px;
      font-family: 'Roboto', Arial, sans-serif;
    }

    button {
      margin-top: 20px; 
      padding: 14px 0; 
      width: 100%; 
      background-color: #667eea; /* same blue border color */
      border: none; 
      color: white; 
      font-size: 18px;
      font-weight: 700;
      cursor: pointer; 
      border-radius: 8px;
      transition: background-color 0.3s ease;
    }

    button:hover {
      background-color: #5a67d8;
    }

    .error, .success {
      margin-top: 12px;
      font-weight: 700;
      text-align: center;
    }

    .error {
      color: #e53e3e;
    }

    .success {
      color: #38a169;
    }

    /* Responsive */
    @media (max-width: 480px) {
      .container {
        padding: 30px 20px;
      }
      button {
        font-size: 16px;
      }
    }
  </style>
</head>
<body>
  <div class="container">
    <!-- Logo -->
    <img src="images/logo.png" alt="Student Wellness Service Logo" class="logo" />

    <h2>Register</h2>

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
  </div>
</body>
</html>