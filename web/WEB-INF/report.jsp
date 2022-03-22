<%-- 
    Document   : report
    Created on : Mar 22, 2022, 9:59:48 AM
    Author     : 641380
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Report Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </head>
    <body>
        <%@ include file="navbar.jsp" %>
        <p class="h1">Report Page</p>
        
        <form>
            <div class="mb-3 row">
            <label class="col-md-4 col-form-label">From</label>
            <div class="col-md-4">
                <input type="date" class="col-form-control"/>    
            </div>
            </div>
        
            <div class="mb-3 row">
                <label class="col-md-4 col-form-label">To</label>
                <div class="col-md-4">
                    <input type="date" class="col-form-control"/>    
                </div>
            </div>
            
            <div class="dropdown">
            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
              Choose what the report is on
            </button>
            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
              <li><a class="dropdown-item" href="#">City</a></li>
              <li><a class="dropdown-item" href="#">Team</a></li>
              <li><a class="dropdown-item" href="#">Store</a></li>
            </ul>
          </div>
        </form>
        

        
    </body>
</html>
