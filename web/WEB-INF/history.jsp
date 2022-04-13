<%-- 
    Document   : history
    Created on : Mar 10, 2022, 12:34:05 AM
    Author     : Main
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/png" href="content/images/favicon-32x32.png" sizes="32x32">
        <link rel="icon" type="image/svg+xml" href="content/images/favicon-svg.svg">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History</title>
        <link rel="stylesheet" href="css/history-styles.css">
        <script type="text/javascript" src="scripts/history.js"></script>
        <script type="text/javascript" src="scripts/models/autotable.js"></script>
        <script type="text/javascript" src="scripts/models/datacolumn.js"></script>
        <script type="text/javascript" src="scripts/models/customcolumn.js"></script>
        <script type="text/javascript" src="scripts/models/rowmanager.js"></script>
        <script>${historyData}</script>
    </head>
    <body class="base">
        <%@ include file="navbar.jsp" %>
        <div id="main" class="body">
            <h1 class="header">History</h1>
            <p>Total Hours Worked: <span id="total-hours" style="font-weight: bold;"></span></p>
        </div>
    </body>
</html>