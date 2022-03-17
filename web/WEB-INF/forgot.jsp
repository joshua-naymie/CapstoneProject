<%-- 
    Document   : forgot
    Created on : 11-Aug-2021, 9:30:33 PM
    Author     : srvad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <link type="text/css" rel="stylesheet" href="<c:url value="/login.css" />" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Patrick+Hand&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Hind+Madurai&display=swap" rel="stylesheet"> 
    <link rel="stylesheet" href="css/forgotPassword.css">
    <script type="text/javascript" src="scripts/models/regexs.js"></script>
    <script type="text/javascript" src="scripts/models/validator.js"></script>
    <script type="text/javascript" src="scripts/forgot.js"></script>
    <script type="text/javascript" src="scripts/models/inputgroup.js"></script>
    <script type="text/javascript" src="scripts/models/inputgroupcollection.js"></script>
    <script type="text/javascript" src="scripts/models/functions.js"></script>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
<!--        COMMIT-->
    </head>
    <body>
        <div class="base">
            <div class="body">
                <h1>Reset Password</h1>
                <form id="forgot-form">
                    <input id="action" name="action" type="hidden">
                    <div id="inputs" class="input-area">

                    </div>
                    <input type="hidden" name="uuid" value="${uuid}">
                    <p>${userMessage}</p>
                </form>
            </div>
        </div>
    </body>
</html>
