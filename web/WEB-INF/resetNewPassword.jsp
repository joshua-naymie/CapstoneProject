<%-- 
    Document   : ressetNewPassword
    Created on : Feb. 21, 2022, 12:51:44 p.m.
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
        <title>Password Reset</title>
    </head>
    <body>
        <h1>Enter a new password</h1>
        <p style="margin-top: 20px;">
            <form action="" method="post">
                Password: <input type="text" name="nPassword" value=""><br>
                
                <input type="hidden" name="uuid" value="${uuid}">
                
                <input  style="margin-top: 20px;" class="btn second" type="submit" value="Submit">
            </form>
           
        </p>
    </body>
</html>

