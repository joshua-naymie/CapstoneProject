<%-- 
    Document   : programs
    Created on : Feb 15, 2022, 8:34:06 AM
    Author     : 854950
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ECSSEN Program Offerings</title>
        <link rel="stylesheet" href="css/styles.css">
        <script src="https://kit.fontawesome.com/7eb48072cc.js" crossorigin="anonymous"></script>
        <script type="text/javascript" src="scripts/userlist.js"></script>

        <script>${programData}</script>

    </head>
    <body>
        <div class="base">
            <%@ include file="navbar.jsp" %>
            <h1>ECSSEN Programs</h1>
            <div id="content-section" class="body">
                <div id="controls" class="table-controls">
                    
                    <form action="" method="post">
                            
                    </form>
                    
                </div>
                <div id="table-container" class="table-container">

                </div>
            </div>  
        </div>
            <form id="submit-form">
                <input id="action" name="action" type="hidden">
                <input id="username" name="username" type="hidden">
            </form>
    </body>
</html>