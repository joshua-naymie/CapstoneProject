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
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"
			  integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
			  crossorigin="anonymous"></script>
	<script src="scripts/report.js"></script>
    </head>
    <body>
        <%@ include file="navbar.jsp" %>
	<div class="container">
		<p class="h1 text-center">Report Page</p>

		<div class="row mt-3">
		    <div class="col-md-6">
			<label class="form-label">From</label>
			<input type="date" class="form-control"/>
		    </div>

		    <div class="col-md-6">
			<label class="form-label">To</label>
			<input type="date" class="form-control"/>
		    </div>
		</div>
		
		<!--DropDown list for report generation options-->
		<div class="dropdown mt-3">
		    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
		      Generate report on
		    </button>
		    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
		      <li><h6 class="dropdown-header">Food Delivery</h6></li>
		      <li><button class="dropdown-item" id="programReport">Whole Program</button></li>
		      <li><button class="dropdown-item" id="cityReport">City</button></li>
		      <li><button class="dropdown-item" id="teamReport">Team</button></li>
		      <li><button class="dropdown-item" id="storeReport">Store</button></li>
		      <li><hr class="dropdown-divider"></li>
		      <li><h6 class="dropdown-header">Hotline</h6></li>
		      <li><button class="dropdown-item" id="hotlineReport">Hours Worked</button></li>
		      <li><hr class="dropdown-divider"></li>
		      <li><h6 class="dropdown-header">Individual</h6></li>
		      <li><button class="dropdown-item" id="individualReport">Task Completed and Hours Volunteered</button></li>
		    </ul>
		  </div>
		
		<div id="additionalInfo" class="mt-3">
			
		</div>
		
		<div class="d-grid gap-2 col-12 mx-auto">
			<button class="btn btn-primary mt-3">Generate</button>
		</div>
	</div>
    </body>
</html>
