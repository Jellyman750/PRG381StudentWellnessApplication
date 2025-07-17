<%-- 
    Document   : index
    Created on : 13 Jul 2025, 17:04:36
    Author     : kyles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Welcome to Student Wellness Service</title>

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
  background: #f2f2f2;
  padding: 40px 60px;
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0,0,0,0.2);
  text-align: center;
  max-width: 600px; /* Increased from 400px */
  width: 90%;
  border: 3px solid black;
}
h1 {
  margin-bottom: 24px;
  font-weight: 700;
    color: #9457eb /* changed heading color */
}

    form {
      margin: 12px 0;
    }

    button {
      width: 100%;
      padding: 14px 0;
      margin-top: 12px;
      font-size: 18px;
      font-weight: 700;
      color: white;
      background-color: #667eea;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    button:hover {
      background-color: #5a67d8;
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

    <!-- Add your logo image here -->
    <img src="images/logo.png" alt="Student Wellness Service Logo" style="max-height: 60px; width: auto; margin-bottom: 20px;" />

    <h1>Welcome to the Student Wellness Service</h1>

    <form action="${pageContext.request.contextPath}/login.jsp" method="get">
      <button type="submit">Login</button>
    </form>

    <form action="${pageContext.request.contextPath}/register.jsp" method="get">
      <button type="submit">Register</button>
    </form>
  </div>
</body>

</html>

