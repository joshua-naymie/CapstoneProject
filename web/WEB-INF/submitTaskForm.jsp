<%-- 
    Document   : submitTaskForm
    Created on : Mar. 8, 2022, 10:48:56 a.m.
    Author     : srvad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Submit Task Form</title>
    </head>
    <body>
        <p>
            Task: ${description}
        </p>
        
        <div class="form-group col-md-4">
            <label>Start Time:</label>
            <input class="form-control" type="time" name="taskStart" value="" placeholder="">
        </div>
        
        <div class="form-group col-md-4">
                <label>End Time:</label>
                <input class="form-control" type="time" name="taskEnd" value="" placeholder="">
        </div>
        
        <div>
            <label for="notes">Notes:</label>
            <textarea name="notes" rows="3" cols="50"> </textarea>
        </div>

  <br>
        

        
        <c:if test="${foodDelivery}">
            
            
            
        </c:if>
            
    </body>
</html>
