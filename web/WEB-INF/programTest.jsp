<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/inventory.css">
        <script src="https://kit.fontawesome.com/7eb48072cc.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <h1>Hello World!</h1>
        <table>  
            <tr>
                <th>program ID</th>
                <th>program Name</th>
                <th>manager Name</th>
                <th>Edit</th>
                <th>Change status</th>
            </tr>
            
            <form action="" method="post">
                <input type ="submit" value="Export" name="action">
            </form>

            <form action="" method="get">
                <input type ="submit" value="Refresh">
            </form>
            
            <p>${userMessage}</p>
            
            <c:forEach items="${programs}" var="program">
                <tr>
                <form method="POST">
                    <input name="programID" type="hidden" value="${program.getProgramId()}">
                    <td name="tableUsername" value="${program.getProgramId()}">${program.getProgramId()}</td>
                    <td name="tableFirstName" value="${program.getProgramName()}">${program.getProgramName()}</td>
                    <td name="tableLastName" value="${program.getManagerName()}">${program.getManagerName()}</td>
                    <td name="" value="edit"><button type="submit" value="Edit" name="action"><i class="fas fa-pen"></i></button></td>
                    <td name="" value="delete"><button type="submit" value="Delete" name="action"><i class="fas fa-pen"></i></button></td>
                    <td name="" value="editAdmin"><button type="submit" value="EditAdmin" name="action"><i class="fas fa-pen"></i></button></td>
                </form>
                </tr>
            </c:forEach>
    </table>

    <div id="addUserDiv">
        <form method="POST">
            <h2>Add program</h2>
            <label>Program Name:</label><input type="text" name="programName" value="${username_attribute}" placeholder="program name"><br>
            <label>Program Manager:</label><input type="text" name="managerName" value="${password_attribute}" placeholder="manager name"><br>
            

            <input type="submit" value="Add" name="action">
            <p>${userMessage}</p>
        </form>
    </div>

    <div id="editUserDiv">
        <h2>Edit program</h2>
        <form method="POST">
            <label>Program ID: </label><input type="text" name="savepID" value="${editProgram.getProgramId()}" placeholder="Username" readonly><br> 
            <label>Program Name:</label><input type="text" name="savepName" value="${editProgram.getProgramName()}" placeholder="First Name"><br>
            <label>Program Manager:</label><input type="text" name="savepMan" value="${editProgram.getManagerName()}" placeholder="Last Name"><br>

            <div id="editButtons">
                <input type="submit" value="Save" name="action">
                <input type="submit" value="Cancel" name="action">
            </div>
        </form>
    </div>
</body>
</html>
