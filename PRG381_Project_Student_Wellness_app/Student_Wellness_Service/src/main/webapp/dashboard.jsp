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
        <title>Home Page</title>
    </head>
    <body>
        <h1>Welcome, <%= user.getName() %> <%= user.getSurname() %>!</h1>
        
        <form action="logout" method="post">
            <input type="submit" value="Logout">
</form>
    </body>
</html>
