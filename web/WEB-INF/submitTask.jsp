<%-- 
    Document   : submitTask
    Created on : Mar. 7, 2022, 1:05:45 p.m.
    Author     : srvad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Submit Task Page</title>
	<link rel="stylesheet" href="css/task.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
		<!-- JavaScript Bundle with Popper -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    	<script src="scripts/util.js"></script>
    </head>
    <body>
	<%@ include file="navbar.jsp" %>
	<div class="container main">
		<p class="text-center h1">Submit Task</p>
		<table class="table table-striped table-hover align-middle">
			<thead>
				<tr>
					<th scope="col">Program Name</th>
					<th scope="col">Date</th>
					<th scope="col">Start Time</th>
					<th scope="col">End Time</th>
					<th scope="col">Description</th>
					<th scope="col" colspan="2">Operation</th>
				</tr>
			</thead>

			<tbody>
				<tr>
					<td scope="col">2022-03-08</td>
					<td>12:30 p.m.</td>
					<td>3:30 p.m.</td>
					<td>Pickup boxes and drop to Wilson Family</td>
					<td><button class="btn btn-primary" type="button" task_id="3">Edit</button></td><td><button class="btn btn-secondary" type="button">Submit</button></td>
				</tr>
			</tbody>
		</table>
	</div>

    </body>
</html>
