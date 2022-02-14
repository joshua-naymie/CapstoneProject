<%-- 
    Document   : login
    Created on : Feb 8, 2022, 10:41:17 AM
    Author     : Main
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login - ECSSEN Pro</title>
       <link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Patrick+Hand&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="css/loginpagecss.css">
        <script type="text/javascript" src="scripts/login.js"></script>
        <script type="text/javascript" src="scripts/inputgroup.js"></script>
        <script type="text/javascript" src="scripts/inputgroupcollection.js"></script>
        <script type="text/javascript" src="scripts/regexs.js"></script>
        <script type="text/javascript" src="scripts/validator.js"></script>
        <script type="text/javascript" src="scripts/functions.js"></script>
        
    </head>
    <body>
           <div class="base">
            <div class="body">
<!--                <img src="content/images/logo-vert.png" width="170px" alt="Ecclogo"/>-->
                <h1>ECSSEN PRO</h1>
                <form id="login-form">
                    <input id="action" name="action" type="hidden">
                    <div id="inputs" class="input-area">
                    </div>
                    <p>${msg}</p>  
                </form>
            </div>
        </div>
    </body>
</html>