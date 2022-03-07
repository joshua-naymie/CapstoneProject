<%-- 
    Document   : task
    Created on : 9-Feb-2022, 5:41:04 PM
    Author     : lixia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Task Page</title>
		<link rel="stylesheet" href="css/task.css">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
		<!-- JavaScript Bundle with Popper -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
		<script src="scripts/util.js"></script>
	
	</head>
	<body>
		<%@ include file="navbar.jsp" %>
		<script>let taskDataSet = ${taskData}</script>
		<script src="scripts/task.js"></script>
		
		<!------------------------------ Accordion Start ------------------------------------>
		<div class="accordion col-md-9" id="accordionWeek">
		</div>
	</body>
</html>
