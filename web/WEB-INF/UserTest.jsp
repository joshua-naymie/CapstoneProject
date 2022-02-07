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
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Edit</th>
                <th>Change status</th>
            </tr>

            <form action="" method="post">
                Search By Last Name: <input type="text" name="lastName">
                <input type ="submit" value="Search" name="action">
            </form>

            <form action="" method="get">
                <input type ="submit" value="Refresh">
            </form>
            
            <p>${userMessage}</p>
            
            <c:forEach items="${users}" var="user">
                <tr>
                <form method="POST">
                    <input name="username" type="hidden" value="${user.getUserId()}">
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

    <div id="addUserDiv">
        <form method="POST">
            <h2>Add User</h2>
            <label>Username:</label><input type="email" name="username" value="${username_attribute}" placeholder="Username"><br>
            <label>Password:</label><input type="text" name="user_password" value="${password_attribute}" placeholder="Password"><br>
            <label>City:</label><input type="text" name="user_city" value="${email_attribute}" placeholder="City"><br>
            <label>First Name:</label><input type="text" name="user_firstname" value="${firstname_attribute}" placeholder="First Name"><br>
            <label>Last Name:</label><input type="text" name="user_lastname" value="${lastname_attribute}" placeholder="Last Name"><br>

            <input type="submit" value="Add" name="action">
            <p>${userMessage}</p>
        </form>
    </div>

    <div id="editUserDiv">
        <h2>Edit User</h2>
        <form method="POST">
            <label>Username:</label><input type="text" name="saveusername" value="${editUser.getUserId()}" placeholder="Username" readonly><br> 
            <label>First Name:</label><input type="text" name="saveuser_firstname" value="${editUser.getFirstName()}" placeholder="First Name"><br>
            <label>Last Name:</label><input type="text" name="saveuser_lastname" value="${editUser.getLastName()}" placeholder="Last Name"><br>

            <div id="editButtons">
                <input type="submit" value="Save" name="action">
                <input type="submit" value="Cancel" name="action">
            </div>
        </form>
    </div>
</body>
</html>
