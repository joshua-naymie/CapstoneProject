
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Edit Task</title>
                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
                    integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
                    crossorigin="anonymous">
                <script src="https://code.jquery.com/jquery-3.2.1.min.js"
                    integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
                    crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
                    integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
                    crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
                    integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
                    crossorigin="anonymous"></script>
                <script type="text/javascript"
                    src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		    <link rel="stylesheet" href="css/addTask.css"/>

            </head>

            <body>
                <%@ include file="navbar.jsp" %>
		<script>${taskData}</script>
		<%-- <script src="scripts/editTask.js"></script> --%>


                    <div class="container">


                        <p class="h1 text-center">Edit Task</p>
                        <form action="" method="post" class="mt-3">

                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="form-label">Description:</label>
                                    <input class="form-control" type="text" name="description" value="" placeholder="" id="task_description">
                                </div>

                                <div class="form-group col-md-4">
                                    <label for="programAdd" class="form-label">Program</label>
                                    <select name="programAdd" id="task_program" class="form-control" id="task_program">
                                        <c:forEach items="${allPrograms}" var="program">
                                            <option value="${program.getProgramName()};${program.getProgramId()}">
                                                ${program.getProgramName()}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group col-md-2">
                                    <label for="cityAdd" class="form-label">City</label>
                                    <select name="cityAdd" id="task_city" class="form-control">
                                        <option value="Calgary">Calgary</option>
                                        <option value="Airdrie">Airdrie</option>
                                        <option value="Lethbridge">Lethbridge</option>
                                        <option value="Edmonton">Edmonton</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label class="form-label">Date:</label>
                                    <input class="form-control" type="date" name="taskDate" value="" placeholder="" id="task_date">
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="form-label">Start Time:</label>
                                    <input class="form-control" type="time" name="taskStart" value="" placeholder="" id="task_start_time" step="3600000">
                                </div>


                                <div class="form-group col-md-4">
                                    <label class="form-label">End Time:</label>
                                    <input class="form-control" type="time" name="taskEnd" value=""
                                        placeholder="" id="task_end_time">
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group">
                                    <label for="spotsAdd" class="form-label">Spots: </label>
                                    <input type="number" id="spotsAdd" name="spotsAdd" min="1" max="10" value="1" class="form-control">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="form-label" for="chosen_users">Signed Up Volunteers</label>
                                    <ul class="list-group">
                                        <c:if test="${empty chosenUser || chosenUsers.size() == 0}">
                                            <li class="list-group-item">
                                                There are no volunteers signed up yet
                                            </li>
                                        </c:if>
                                        <c:forEach items="${chosenUsers}" var="chosenUser">
                                            <li class="list-group-item">
                                                <input class="form-check-input me-1" type="checkbox" value="${chosenUser.getFirstName} ${choseUser.getLastName}">
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>

                                <div class="form-group col-md-6">
                                    <label class="form-label" for="available_volunteers">Other Available Volunteers</label>
                                    <ul class="list-group">
                                        <c:if test="${empty canbeAssignedUsers || canbeAssignedUsers.size() == 0}">
                                            <li class="list-group-item">
                                                There are no volunteers can be assigned 
                                            </li>
                                        </c:if>
                                        <c:forEach items="${canbeAssignedUser}" var="canbeAssignedUsers">
                                            <li class="list-group-item">
                                                <input class="form-check-input me-1" type="checkbox" value="${canbeAssignedUser.getFirstName} ${canbeAssignedUser.getLastName}">
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                            
                            <!-- Number of volunteers for the task -->
                            <%-- <input type="submit" value="Add" name="action" class="btn btn-primary">  --%>

                        </form>
                    </div>

            </body>

            <script type="text/javascript">
                $company = $('#companyAdd');

                $company.change(
                    function () {
                        $('#storeAdd').find('option').remove();
                        $('#storeAdd').append(
                                            '<option value="">Choose here'+ '</option>'
                                        );
                        let cid = $('#companyAdd').val();
                        $.ajax({
                            type: "GET",
                            url: "data",
                            data: { "companyId": cid, "operation": "store" },
                            success:
                                function (data) {
                                    console.log(data);
                                    let obj = $.parseJSON(data);
                                    $.each(obj, function (key, value) {
                                        $('#storeAdd').append(
                                            '<option value="' + value.store_id + '">' + value.store_name + '</option>'
                                        )
                                    })
                                }
                        });
                    }
                );
            </script>

            </html>
