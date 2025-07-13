<%-- 
    Document   : dashboard.jsp
    Created on : 13 Jul 2025, 17:05:43
    Author     : kyles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page session="true" %>
<%
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("studentName") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String studentName = (String) session.getAttribute("studentName");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Dashboard - Student Wellness Service</title>
    <style>
        body { font-family: Arial, sans-serif; background: #e9f7ef; text-align: center; padding-top: 100px; }
        h1 { color: #2c3e50; }
        a.logout-btn {
            display: inline-block; margin-top: 40px; padding: 10px 20px;
            background-color: #dc3545; color: white; text-decoration: none; border-radius: 5px;
        }
        a.logout-btn:hover { background-color: #b02a37; }
    </style>
</head>
<body>
    <h1>Welcome, <%= studentName %>!</h1>
    <a href="LogoutServlet" class="logout-btn">Logout</a>
</body>
</html>
