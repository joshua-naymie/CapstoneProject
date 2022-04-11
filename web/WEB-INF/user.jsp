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
        		<link rel="stylesheet" href="css/task.css">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
		<!-- JavaScript Bundle with Popper -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
		<script src="https://code.jquery.com/jquery-3.6.0.min.js"
			  integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
			  crossorigin="anonymous"></script>
        <link rel="stylesheet" href="css/user.css">
        <link rel="stylesheet" href="css/inputstyles.css">
        <script type="text/javascript" src="scripts/user.js"></script>
        <script type="text/javascript" src="scripts/models/inputgroup.js"></script>
        <script type="text/javascript" src="scripts/models/inputgroupcollection.js"></script>
        <script type="text/javascript" src="scripts/models/regexs.js"></script>
        <script type="text/javascript" src="scripts/models/validator.js"></script>
        <script>${userData}</script>
    </head>
    <body id="body">
                <%@ include file="navbar.jsp" %>
        <div class="main">
            <h2 id="header">Add User</h2>
            <form id="container" class="container" method="post" action="">
                <div id="container-left" class="input-column"></div>
                <div id="container-right" class="input-column"></div>
            </form>
        </div>
    </body>
</html>