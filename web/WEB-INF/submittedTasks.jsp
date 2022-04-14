<%-- 
    Document   : submittedTasks
    Created on : Mar 10, 2022, 10:22:13 AM
    Author     : Main
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/png" href="content/images/favicon-32x32.png" sizes="32x32">
        <link rel="icon" type="image/svg+xml" href="content/images/favicon-svg.svg">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Submitted Tasks</title>
        <link rel="stylesheet" href="css/submittedtask-styles.css">
        <script type="text/javascript" src="scripts/submittedtasks.js"></script>
        <script type="text/javascript" src="scripts/models/autotable.js"></script>
        <script type="text/javascript" src="scripts/models/datacolumn.js"></script>
        <script type="text/javascript" src="scripts/models/customcolumn.js"></script>
        <script type="text/javascript" src="scripts/models/rowmanager.js"></script>
        <script>${taskData}</script>
    </head>
    <body class="base">
        <%@ include file="navbar.jsp" %>
        <div id="main" class="body">
            <h1 class="header">Submitted Tasks</h1>
            <div id="table-container" class="table-container"></div>
            <h1 id="no-task-message" class="no-tasks">No Tasks</h1>
        </div>
    </body>
</html>