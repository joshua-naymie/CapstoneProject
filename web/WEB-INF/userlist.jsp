<%-- 
    Document   : User
    Created on : Feb 3, 2022, 8:38:37 AM
    Author     : DWEI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/png" href="content/images/favicon-32x32.png" sizes="32x32">
        <link rel="icon" type="image/svg+xml" href="content/images/favicon-svg.svg">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
        <link rel="stylesheet" href="css/styles.css">
        <script src="https://kit.fontawesome.com/7eb48072cc.js" crossorigin="anonymous"></script>
        <script type="text/javascript" src="scripts/userlist.js"></script>
        <script type="text/javascript" src="scripts/models/autotable.js"></script>
        <script type="text/javascript" src="scripts/models/datacolumn.js"></script>
        <script type="text/javascript" src="scripts/models/customcolumn.js"></script>
        <script type="text/javascript" src="scripts/models/rowmanager.js"></script>
        <script type="text/javascript" src="scripts/models/functions.js"></script>
        
        <script>${userData}</script>

    </head>
    <body>
        <div class="base">
            <%@ include file="navbar.jsp" %>
            <div id="content-section" class="body">
                <div id="controls" class="table-controls">
                    <div>
                        <a class="add-user__button" onclick="exportPressed();">Export All</a>
                        <a class="add-user__button" onclick="exportCSVPressed();">Export CSV</a>
                    </div>
                    <div style="display: flex; justify-content: center; align-items: center;">
                        <input id="searchbar" type="text" placeholder="Name" />
                    </div>

                    <div style="display: flex; justify-content: flex-end">
                        <a class="add-user__button" href="/ECSSENPro/add">Add User</a>
                    </div>
                    
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