<%-- 
    Document   : User
    Created on : Feb 3, 2022, 8:38:37 AM
    Author     : DWEI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
        <link rel="stylesheet" href="css/styles.css">
        <script src="https://kit.fontawesome.com/7eb48072cc.js" crossorigin="anonymous"></script>
        <script type="text/javascript" src="scripts/userlist.js"></script>
        <script type="text/javascript" src="scripts/autotable.js"></script>
        <script type="text/javascript" src="scripts/datacolumn.js"></script>
        <script type="text/javascript" src="scripts/customcolumn.js"></script>
        <script type="text/javascript" src="scripts/rowmanager.js"></script>
        <script type="text/javascript" src="scripts/functions.js"></script>
    </head>
    <body>
        <div class="base">
            <%@ include file="navbar.jsp" %>
            <div class="body">
                <div class="table-controls">
                    <div id="dummy"></div>
                    <div style="display: flex; justify-content: center; align-items: center;">
                        <input id="searchbar" type="text" placeholder="Name" />
                    </div>

                    <div style="display: flex; justify-content: flex-end">
                        <a class="add-user__button" href="/ECSSENPro/add">Add User</a>
                    </div>
                </div>
                <div id="table-container" class="table-container">

                </div>
            </div>  
        </div>
            <form id="submit-form">
                <input id="action" name="action" type="hidden">
                <input id="userid" name="userid" type="hidden">
            </form>
    </body>
</html>