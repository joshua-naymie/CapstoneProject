<%-- 
    Document   : programTest
    Created on : Mar 7, 2022, 9:40:19 PM
    Author     : 861349
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>program add test</title>
    </head>
    <body>
        <div id="addUserDiv">
        <form method="POST">
            <h2>Add User</h2>
            <input name="manager-ID" type="hidden" value="2">
            <input name="status" type="hidden" value="active">
            <label>Program Name </label><input type="text" name="program-name" value="" placeholder=""><br>
            <label>Manager Name</label><input type="text" name="Managername" value="" placeholder=""><br>

            <input type="submit" value="Add" name="action">
            <p>${userMessage}</p>
        </form>
    </div>
    </body>
</html>
