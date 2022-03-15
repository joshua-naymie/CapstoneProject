<%-- 
    Document   : resetNewPassword
    Created on : Feb. 21, 2022, 12:51:44 p.m.
    Author     : srvad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/resetNewPassword.css">
    <script type="text/javascript" src="scripts/resetNewPassword.js"></script>
    <script type="text/javascript" src="scripts/inputgroup.js"></script>
    <script type="text/javascript" src="scripts/inputgroupcollection.js"></script>
    <script type="text/javascript" src="scripts/functions.js"></script>
    <script type="text/javascript" src="scripts/resettest.js"></script>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        COMMIT-->
        <title>Password Reset</title>
    </head>
    <body>
        <div class="base">
            <div class="body">
                <h1>Reset Password</h1>
                <form id="newPassword-form">
                    <input id="action" name="action" type="hidden">
                    <div id="inputs" class="input-area">

                    </div>
                </form>

                <div id="moo" onfocus="focusFunction()" onblur="blurFunction()">
                    <h3>PASSWORD MUST CONTAIN</h3>
                    <p id="letter" class="invalid">A <b>lowercase</b> letter</p>
                    <p id="capital" class="invalid">A <b>capital (uppercase)</b> letter</p>
                    <p id="number" class="invalid">A <b>number</b></p>
                    <p id="length" class="invalid">Minimum <b>8 characters</b></p>
                </div>

            </div>
        </div>
    </body>
</html>

