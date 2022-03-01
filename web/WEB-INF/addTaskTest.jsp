<%-- 
    Document   : addTaskTest
    Created on : Feb. 22, 2022, 2:06:08 p.m.
    Author     : srvad
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Task</title>
    </head>
    <body>
        <h1>Add Task</h1>
        
        <form action="" method="post">
            
            <label>Description:</label><input type="text" name="description" value="" placeholder=""><br><br>
            
                <div class="">
                    <label for="programAdd" class="input-label">Program</label>
                    <select name="programAdd" id="programAdd">
                        <option value="" selected>Choose here</option>
                            <c:forEach items="${allPrograms}" var="program">
                                <option value="${program.getProgramName()};${program.getProgramId()}" >${program.getProgramName()}
                                </option>
                            </c:forEach>
                    </select>
                </div>
            <br>
                <div class="">
                    <label for="cityAdd" class="input-label">City</label>
                    <select name="cityAdd" id="cityAdd">
                        <option value="" selected>Choose here</option>
                        <option value="Calgary" >Calgary</option>
                        <option value="Calgary" >Lethbridge</option>
                    </select>
                </div>
            <br>
            
            <label>Date:</label><input type="text" name="program_date" value="" placeholder=""><br><br>
            <label>Start Time:</label><input type="text" name="user_city" value="" placeholder=""><br><br>
            <label>End Time:</label><input type="text" name="user_firstname" value="" placeholder=""><br><br>
            
            <label>Supervisor:</label><input type="text" name="user_lastname" value="" placeholder=""><br>

            <br>
                <div class="">
                    <label for="companyAdd" class="input-label">Company</label>
                    <select name="companyAdd" id="companyAdd">
                        <option value="" selected>Choose here</option>
                            <c:forEach items="${allCompanies}" var="company">
                                <option value="${company.getCompanyName()};${company.getCompanyId()}" >${company.getCompanyName()}
                                </option>
                            </c:forEach>
                    </select>
                </div>
            <br>

                <div class="">
                    <label for="storeAdd" class="input-label">Store Name:</label>
                    <select name="storeAdd" id="storeAdd">
                        <option value="" selected>Choose here</option>
                            <c:forEach items="${allStores}" var="store">
                                <option value="${store.getStoreName()};${store.getStoreId()}" >${store.getStoreName()}
                                </option>
                            </c:forEach>
                    </select>
                </div>
            <br>

            <input type="submit" value="Add" name="action">
            
        </form>

    </body>
</html>
