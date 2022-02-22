<%-- 
    Document   : forgot
    Created on : 11-Aug-2021, 9:30:33 PM
    Author     : srvad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" rel="stylesheet" href="<c:url value="/login.css" />" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password</title>
    </head>
    <body>
        <h1>Forgot Password?</h1>
        <p style="margin-top: 20px;">Please enter your username to retrieve your password.</p>
        <p style="margin-top: 20px;">
            <form action="" method="post">
                Email: <input type="text" name="fEmail" value=""><br>
                
                <input style="margin-top: 20px;" class="btn second" type="submit" value="Submit">
                
                <input type="hidden" name="uuid" value="${uuid}">
                
            </form>
            
            <c:if test="${emailCheck}">
                <p style="color: red" > Invalid email. Please try again.</p>
            </c:if>
        </p>
    </body>
</html>
