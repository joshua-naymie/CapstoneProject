<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="scripts/inputgroupprogram.js"></script>
        <script type="text/javascript" src="scripts/inputgroupcollectionprogram.js"></script>
        <script type="text/javascript" src="scripts/regexprogram.js"></script>
        <script type="text/javascript" src="scripts/scriptsprogram.js"></script>
        <script type="text/javascript" src="scripts/validator.js"></script>
        <link rel="stylesheet" href="css/program.css">
        <title>ECSSEN Programs</title>
    </head>
    <body>
            <%@ include file="navbar.jsp" %>
                     
                    <div class="main">
                        
        <div class="container">
            
            <div class="content">
                
                <div class="left-panel">
                    <h1>ECSSEN Program Manager</h1>
<!--                    <h3 class="panel-header">Programs</h3>-->
                    <div>
                        <label style="color: gray;">show inactive</label>
                        <input id="store-filter" type="checkbox">
                    </div>
                    <div class="left-panel__top-buttons">
                        <input id="search-input" type="search" class="search-stores__input" placeholder="name, address, phone#">
                        <input id="notempty" type="button" class="add-store__button" onclick="hideShowAddProgram()" value="+">
                    </div>
                    <div id="list-base" class="store-list__base">
                        
                    </div>
                    </div>
                  <form id = "addProgramForm"><div class="right-panel" id="input-area"  style="display:none">

<!--                    <h3 class="panel-header">Add Program</h3>-->
                    <div id="store-name__input" style="margin: 37px 0 30px 0;"></div>
                    <div id="street-address__input"></div>
                    <div class="address-inputs">
                        <div id="city__input"></div>
                        <div id="province__input"></div>
                        <div id="postal-code__input"></div>
                    </div>
                    <div class="phone-status-inputs">
                        <div id="phone__input"></div>
                        <div id="status__input" >
                            <select name="status">
                                <option value="active">Active</option>
                                <option value="inactive">Inactive</option>
                            </select>
                            <label class="status__label">Status</label>
                        </div>
                    </div>
                    <div class="edit-store__buttons">
                        <input class="cancel__button" type="button" value="Cancel" onClick = "resetAddProgramForm()">
                        <input id="ok__button" class="ok__buton" type="button" value="Save">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>
