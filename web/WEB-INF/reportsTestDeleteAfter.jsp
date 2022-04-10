<%-- 
    Document   : reportsTestDontPush
    Created on : Apr 9, 2022, 4:13:33 PM
    Author     : DWEI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Reports Test!</h1>
        <form method="POST">
            <label>TeamId:</label><input type="text" name="teamId"><br>
            <label>UserId:</label><input type="text" name="userId"><br>
            <label>StoreId:</label><input type="text" name="storeId"><br>
            <label>Start:</label><input type="date" name="startdate"><br>
            <label>End:</label><input type="date" name="enddate"><br>
            <br>
            <div id="editButtons">
                <input type="submit" value="foodTeamReport" name="action">
                <input type="submit" value="individualReport" name="action">
                <input type="submit" value="foodProgramStoreReport" name="action">
            </div>
            <p>${userMessage}</p>
        </form>
    </body>
</html>
