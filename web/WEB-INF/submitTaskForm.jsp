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
    </head>
    <body>
        <p>
            Task: ${description}
        </p>
        
        <form action="" method="post" class="mt-3">   
        
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
            
            <div class="form-group col-md-2">
                <label for="deliveryType" class="input-label">Delivered to </label>
                    <select name="deliveryType" id="deliveryType" class="form-control">
                        <option value="" selected>Choose here</option>
                        <option value="Organization">Organization</option>
                        <option value="Community">Community</option>
                </select>
            </div>
            
            <div class="form-group col-md-4" id="organization_id" style='display:none;' >
                <label for="organization_id" class="input-label">Organization Name</label>
                <select name="organization_id" id="organization_id" class="form-control">
                    <option value="" selected>Choose here</option>
                        <c:forEach items="${organizations}" var="org">
                            <option value="${org.getOrganizationId()}"> ${org.getOrgName()}
                            </option>
                        </c:forEach>
                </select>
            </div>

            <div id="family_count" style='display:none;' >            
                <label for="family_count">Family Count</label>
                <input type="number" name="family_count" min="1" max="20" value="">  
            </div>
            
            <div class="form-group col-md-4">
                <label for="package_id" class="input-label">Package Type </label>
                <select name="package_id" class="form-control">
                    <option value="" selected>Choose here</option>
                        <c:forEach items="${allPackages}" var="packages">
                            <option value="${packages.getPackageId()}"> ${packages.getPackageName()} (${packages.getWeightLb()} lb)
                            </option>
                        </c:forEach>
                </select>       
                            
                <label for="food_amount">Package Count </label>
                <input type="number" name="food_amount" min="1" max="20" value="1">   
            </div>
           
           <br>
           <label for="mileage">Mileage </label>
           <input type="number" name="mileage" min="1" max="20" value="1"> km
  
        </c:if>
           <br><br>
        <input type="submit" value="Submit Task" name="action"> 
           
        </form>
        
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
