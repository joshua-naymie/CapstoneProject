
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


                    <div class="container">


                        <p class="h1 text-center">Edit Task</p>
                        <form action="" method="post" class="mt-3">

                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="form-label">Description:</label>
                                    <input class="form-control" type="text" name="task_description" value="" placeholder="" id="task_description">
                                </div>

                                <div class="form-group col-md-4">
                                    <label for="task_program" class="form-label">Program</label>
                                    <select name="task_program" id="task_program" class="form-control" id="task_program">
                                        <c:forEach items="${allPrograms}" var="program">
                                            <option value="${program.getProgramName()};${program.getProgramId()}">
                                                ${program.getProgramName()}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group col-md-2">
                                    <label for="task_city" class="form-label">City</label>
                                    <select name="task_city" id="task_city" class="form-control">
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
                                    <input class="form-control" type="date" name="task_date" value="" placeholder="" id="task_date">
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="form-label">Start Time:</label>
                                    <input class="form-control" type="time" name="task_start_time" value="" placeholder="" id="task_start_time" step="3600000">
                                </div>


                                <div class="form-group col-md-4">
                                    <label class="form-label">End Time:</label>
                                    <input class="form-control" type="time" name="task_end_time" value=""
                                        placeholder="" id="task_end_time">
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label for="task_max_users" class="form-label">Max Users: </label>
                                    <input type="number" id="task_max_users" name="task_max_users" min="1" max="10" value="1" class="form-control">
                                </div>

                                <div class="form-group col-md-4">
                                    <label for="spotsAdd" class="form-label">Spots Taken: </label>
                                    <input type="number" id="task_spots_taken" name="spotsAdd" min="1" max="10" value="1" class="form-control" disabled>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="form-label" for="chosen_users">Signed Up Volunteers</label>
                                    <ul class="list-group" id="signed_up_volunteers_list">
                                        <c:if test="${empty assigned_users || assigned_users.size() == 0}">
                                            <li class="list-group-item">
                                                There are no volunteers signed up yet
                                            </li>
                                        </c:if>
                                        <c:forEach items="${assigned_users}" var="assigned_user">
                                            <li class="list-group-item selected_users">
                                                <input class="form-check-input me-1" type="checkbox" value="${assigned_user.userId}">
                                                <label class="form-label" >${assigned_user.firstName} ${assigned_user.lastName}</label>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>

                                <div class="form-group col-md-6">
                                    <label class="form-label" for="available_volunteers">Other Available Volunteers</label>
                                    <ul class="list-group" id="available_volunteers_list">
                                        <c:if test="${empty can_be_assigned_users || can_be_assigned_users.size() == 0}">
                                            <li class="list-group-item">
                                                There are no volunteers can be assigned 
                                            </li>
                                        </c:if>
                                        <c:forEach items="${can_be_assigned_users}" var="can_be_assigned_user">
                                            <li class="list-group-item selected_users">
                                                <input class="form-check-input me-1" type="checkbox" value="${can_be_assigned_user.userId}" >
                                                <label class="form-label">${can_be_assigned_user.firstName} ${can_be_assigned_user.lastName}</label>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>

                                <div class="form-group col-md-4 mb-3">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="task_available" id="task_available">
                                        <label class="form-check-label" for="isAvailable">
                                            Is Available 
                                        </label>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Number of volunteers for the task -->
                            <button type="submit" name="action" class="btn btn-primary" id="submit">Confirm</button>

                        </form>
                    </div>

            </body>

            <script type="text/javascript">
                $company = $('#companyAdd');

                $company.change(
                    function () {
                        $('#task_program').find('option').remove();
                        $('#storeAdd').append( '<option value="">Choose here'+ '</option>');
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
            <script src="scripts/editTask.js"></script>

            </html>
