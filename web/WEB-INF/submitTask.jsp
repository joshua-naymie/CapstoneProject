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
	<%-- <link rel="stylesheet" href="css/task.css"> --%>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
		<!-- JavaScript Bundle with Popper -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
			<script src="https://code.jquery.com/jquery-3.6.0.min.js"
			  integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
			  crossorigin="anonymous"></script>
    </head>
    <body>
	<script>var taskDataSet = ${taskData}</script>
	<script src="scripts/submitTask.js"></script>
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

		</table>

	</div>
		<!------------------------------- Task Modal --------------------------->
		<div class="modal fade" id="taskModal" tabindex="-1" aria-labelledby="taskModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
				
					<div class="modal-header">
						<h5 class="modal-title" id="taskModalLabel">View Task</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>

					<div class="modal-body">
						<%@ include file="viewSingleTask.jsp" %>
					</div>

		 		   	<div class="modal-footer">
   		    			<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
   		    		</div>
				</div>
			</div>
		</div>

    </body>
</html>