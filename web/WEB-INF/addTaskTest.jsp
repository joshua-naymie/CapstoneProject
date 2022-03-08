<%-- Document : addTaskTest Created on : Feb. 22, 2022, 2:06:08 p.m. Author : srvad --%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Add Task</title>
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
		    <<link rel="stylesheet" href="css/addTask.css"/>
            </head>

            <body>
                <%@ include file="navbar.jsp" %>

                    <div class="container">


                        <h2>Add Task</h2>
                        <form action="" method="post">

                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label>Description:</label>
                                    <input class="form-control" type="text" name="description" value="" placeholder="">
                                </div>

                                <div class="form-group col-md-4">
                                    <label for="programAdd" class="input-label">Program</label>
                                    <select name="programAdd" id="programAdd" class="form-control">
                                        <option value="" selected>Choose here</option>
                                        <c:forEach items="${allPrograms}" var="program">
                                            <option value="${program.getProgramName()};${program.getProgramId()}">
                                                ${program.getProgramName()}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group col-md-2">
                                    <label for="cityAdd" class="input-label">City</label>
                                    <select name="cityAdd" id="cityAdd" class="form-control">
                                        <option value="" selected>Choose here</option>
                                        <option value="Calgary">Calgary</option>
                                        <option value="Airdrie">Airdrie</option>
                                        <option value="Lethbridge">Lethbridge</option>
                                        <option value="Edmonton">Edmonton</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label>Date:</label>
                                    <input class="form-control" type="date" name="taskDate" value="" placeholder="">
                                </div>

                                <div class="form-group col-md-4">
                                    <label>Start Time:</label>
                                    <input class="form-control" type="time" name="taskStart" value="" placeholder="">
                                </div>


                                <div class="form-group col-md-4">
                                    <label>End Time:</label>
                                    <input class="form-control" type="time" name="taskEnd" value=""
                                        placeholder="">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="supervisorAdd" class="input-label">Approving Supervisor</label>
                                <select name="supervisorAdd" id="supervisorAdd" class="form-control col-md-5">
                                    <option value="" selected>Choose here</option>
                                    <c:forEach items="${allSupervisors}" var="supervisor">
                                        <option value="${supervisor.getUserId()}">
                                            ${supervisor.getFirstName()} ${supervisor.getLastName()}
                                        </option>
                                    </c:forEach>
                                </select>

                            </div>


                            <div class="form-group">
                                <label for="companyAdd" class="input-label">Company</label>
                                <select name="companyAdd" id="companyAdd" class="form-control col-md-5">
                                    <option value="" selected>Choose here</option>
                                    <c:forEach items="${allCompanies}" var="company">
                                        <option value="${company.getCompanyId()}">
                                            ${company.getCompanyName()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="storeAdd" class="input-label">Store Name:</label>
                                <select name="storeAdd" id="storeAdd" class="form-control col-md-5">
                                    <option value="" selected>Choose here</option>
                                </select>
                            </div>
                            
                            <label for="spotsAdd">Spots: </label>
                            <input type="number" id="spotsAdd" name="spotsAdd" min="1" max="10" value="1">
                            
                            <!-- Number of volunteers for the task -->
                            <input type="submit" value="Add" name="action"> 

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