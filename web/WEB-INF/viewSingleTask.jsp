<%@page contentType="text/html" pageEncoding="UTF-8"%>
<form>        
    <fieldset disabled>
		<div class="form-row">
                                <div class="form-group col-md-10">
                                    <label>Description:</label>
                                    <input class="form-control" type="text" name="description" value="" placeholder="" id="description">
                                </div>

                                <div class="form-group">
                                    <label for="programAdd" class="form-label">Program</label>
                                    <select name="programAdd" id="program" class="form-control">
                                        <option value="" selected>Choose here</option>
                                        <c:forEach items="${allPrograms}" var="program">
                                            <option value="${program.getProgramName()};${program.getProgramId()}">
                                                ${program.getProgramName()}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group col-md-6">
                                    <label for="cityAdd" class="form-label">City</label>
                                    <select name="cityAdd" id="city" class="form-control">
                                        <option value="" selected>Choose here</option>
                                        <option value="Calgary">Calgary</option>
                                        <option value="Airdrie">Airdrie</option>
                                        <option value="Lethbridge">Lethbridge</option>
                                        <option value="Edmonton">Edmonton</option>
                                    </select>
                                </div>
                        </div>

                        <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label>Date:</label>
                                    <input class="form-control" type="date" name="taskDate" value="" placeholder="" id="date">
                                </div>

                                <div class="form-group col-md-8">
                                    <label>Start Time:</label>
                                    <input class="form-control" type="time" name="taskStart" value="" placeholder="" id="start_time">
                                </div>


                                <div class="form-group col-md-8">
                                    <label>End Time:</label>
                                    <input class="form-control" type="time" name="taskEnd" value=""
                                        placeholder="" id="end_time">
                                </div>
                        </div>

                        <div class="form-group">
                                <label for="supervisorAdd" class="input-label">Approving Supervisor</label>
                                <select name="supervisorAdd" id="supervisorAdd" class="form-control col-md-5">
                                    <option value="" selected>Choose here</option>
                                </select>

                        </div>


                        <div class="form-group">
                                <label for="companyAdd" class="form-label">Company</label>
                                <select name="companyAdd" id="company" class="form-control col-md-5">
                                    <option value="" selected>Choose here</option>
                                    <c:forEach items="${allCompanies}" var="company">
                                        <option value="${company.getCompanyId()}">
                                            ${company.getCompanyName()}
                                        </option>
                                    </c:forEach>
                                </select>
                        </div>

                        <div class="form-group">
                                <label for="storeAdd" class="form-label">Store Name:</label>
                                <select name="storeAdd" id="store" class="form-control col-md-5">
                                    <option value="" selected>Choose here</option>
                                </select>
                        </div>
                            
                        <div class="form-group">
                            <label for="spotsAdd" class="form-label">Spots: </label>
                            <input type="number" id="spots" name="spotsAdd" min="1" max="10" value="1" class="form-control">
                        </div>
    </fieldset>
</form>