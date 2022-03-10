<%@page contentType="text/html" pageEncoding="UTF-8"%>
<form>        
		<div class="form-row">
                                <div class="form-group col-md-10">
                                    <label>Description:</label>
                                    <input class="form-control" type="text" name="description" value="" placeholder="">
                                </div>

                                <div class="form-group">
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

                                <div class="form-group col-md-6">
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
                                <div class="form-group col-md-8">
                                    <label>Date:</label>
                                    <input class="form-control" type="date" name="taskDate" value="" placeholder="">
                                </div>

                                <div class="form-group col-md-8">
                                    <label>Start Time:</label>
                                    <input class="form-control" type="time" name="taskStart" value="" placeholder="">
                                </div>


                                <div class="form-group col-md-8">
                                    <label>End Time:</label>
                                    <input class="form-control" type="time" name="taskEnd" value=""
                                        placeholder="">
                                </div>
                        </div>

                        <div class="form-group">
                                <label for="supervisorAdd" class="input-label">Approving Supervisor</label>
                                <select name="supervisorAdd" id="supervisorAdd" class="form-control col-md-5">
                                    <option value="" selected>Choose here</option>

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
</form>