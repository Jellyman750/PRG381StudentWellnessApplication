<%-- 
    Document   : home
    Created on : 10 Jul 2025, 12:51:36
    Author     : tarina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="com.mycompany.student_wellness_service.User"%>
<%
    //gets the user object from session
    User user = (User) session.getAttribute("user");
    if (user == null ) {
        response.sendRedirect("login.jsp");
        return;
    }
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
    </head>
    <body>
        <h1>Welcome, <%= user.getName() %> <%= user.getSurname() %>!</h1>
        <p>
            Welcome to your Student Wellness Dashboard. Here, you can manage your wellness journey, stay informed about upcoming resources, and access support when you need it. Take a moment to check in with yourself and explore the tools available to help you thrive throughout your studies.
        </p>
        
        <form action="logout" method="post">
            <input type="submit" value="Logout">
</form>
    </body>
</html>