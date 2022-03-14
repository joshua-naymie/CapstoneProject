<%-- 
    Document   : submitTaskForm
    Created on : Mar. 8, 2022, 10:48:56 a.m.
    Author     : srvad
--%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Submit Task Form</title>
        <script type="text/javascript"
                    src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="css/submitTaskForm.css">
    </head>
    <body>
		<%@ include file="navbar.jsp" %>
        <div class="container">
			<p class="h1 text-center header"> Task: ${description} </p>
        
        <form action="" method="post" class="mt-3">   
        
        <div class="row mb-3">
            <div class="form-group col-md-6">
                <label class="form-label">Start Time:</label>
                <input class="form-control" type="time" name="taskStart" value="" placeholder="">
            </div>
        
            <div class="form-group col-md-6">
                    <label class="form-label">End Time:</label>
                    <input class="form-control" type="time" name="taskEnd" value="" placeholder="">
            </div>
        </div>
        
        <div class="form-floating mb-3">
            <textarea name="notes" class="form-control" id="notes" placeholder="Write down your notes here" style="height: 100px"></textarea>
            <label for="notes">Notes</label>
        </div>

        <c:if test="${foodDelivery}">
            <div class="form-group col-md-6">
                <label for="deliveryType" class="form-label">Delivered to </label>
                    <select name="deliveryType" id="deliveryType" class="form-select">
                        <option value="" selected>Choose here</option>
                        <option value="Organization">Organization</option>
                        <option value="Community">Community</option>
                </select>
            </div>
            
            <div class="form-group col-md-4" id="organization_id" style='display:none;' >
                <label for="organization_id" class="input-label">Organization Name</label>
                <select name="organization_id" id="organization_id" class="form-select">
                    <option value="" selected>Choose here</option>
                        <c:forEach items="${organizations}" var="org">
                            <option value="${org.getOrganizationId()}"> ${org.getOrgName()}
                            </option>
                        </c:forEach>
                </select>
            </div>

            <div class="row" id="family_count" style='display:none;' >            
                <label for="family_count" class="form-label">Family Count</label>
                <input type="number" name="family_count" min="1" max="20" value="" class="form-control">  
            </div>
            

            <div class="row mb-3">
            <div class="form-group col-md-4">
                <label for="package_id" class="form-label">Package Type </label>
                <select name="package_id" class="form-select">
                    <option value="" selected>Choose here</option>
                        <c:forEach items="${allPackages}" var="packages">
                            <option value="${packages.getPackageId()}"> ${packages.getPackageName()} (${packages.getWeightLb()} lb)
                            </option>
                        </c:forEach>
                </select>       
            </div>

            <div class="form-group col-md-4">
                <label for="food_amount" class="form-label">Package Count </label>
                <input type="number" name="food_amount" min="1" max="20" value="1" class="form-control">   
            </div>
           
           <div class="form-group col-md-4">
                <label for="mileage" class="form-label">Mileage </label>
                <div class="input-group">
                    <input class="form-control" type="number" name="mileage" min="1" max="20" value="1" aria-describedby="km">
                    <span class="input-group-text" id="km">km</span>
                </div>
           </div>
           </div>
  
        </c:if>

        <input class="btn btn-primary" type="submit" value="Submit Task" name="action"> 
           
        </form>
        </div>
        
    </body>
    <script>
        $(document).ready(function(){
            $('#deliveryType').on('change', function() {
                console.log(this.value);
              if ( this.value == "Organization")
              //.....................^.......
              {
                $("#organization_id").show();
                $("#family_count").hide();
              }
              else if ( this.value == "Community")
              {
                $("#organization_id").hide();
                $("#family_count").show();
              }
            });
        });
    </script>
</html>
