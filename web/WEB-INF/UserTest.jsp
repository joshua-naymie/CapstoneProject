<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Edit</th>
                <th>Change status</th>
            </tr>

            <c:forEach items="${users}" var="user">
                <tr>
                <form method="POST">
                    <td name="tableUsername" value="${user.getUserId()}">${user.getUserId()}</td>
                    <td name="tableFirstName" value="${user.getFirstName()}">${user.getFirstName()}</td>
                    <td name="tableLastName" value="${user.getLastName()}">${user.getLastName()}</td>
                    <td name="" value="edit"><button type="submit" value="Edit" name="action"><i class="fas fa-pen"></i></button></td>
                    <td name="" value="delete"><button type="submit" value="Delete" name="action"><i class="fas fa-pen"></i></button></td>
                    <td name="" value="editAdmin"><button type="submit" value="EditAdmin" name="action"><i class="fas fa-pen"></i></button></td>
                </form>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
