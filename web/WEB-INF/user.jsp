<%-- 
    Document   : user
    Created on : Feb 6, 2022, 1:31:23 PM
    Author     : Main
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

        
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add User</title>
        
        <link rel="stylesheet" href="css/user.css">
        <link rel="stylesheet" href="css/inputstyles.css">
        <script type="text/javascript" src="scripts/userscripts.js"></script>
        <script type="text/javascript" src="scripts/inputgroup.js"></script>
        <script type="text/javascript" src="scripts/inputgroupcollection.js"></script>
        <script type="text/javascript" src="scripts/regexs.js"></script>
        <script type="text/javascript" src="scripts/validator.js"></script>
    </head>
    <body id="body">
        <div class="main">
            <h2>Add User</h2>
            <div id="container" class="container">
                <div id="container-left" class="input-column"></div>
                <div id="container-right" class="input-column"></div>
                <button onclick="validateUserInfo();">Validate</button>
            </div>
        </div>
    </body>
</html>
