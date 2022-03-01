<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="scripts/inputgroup.js"></script>
        <script type="text/javascript" src="scripts/inputgroupcollection.js"></script>
        <script type="text/javascript" src="scripts/regexprogram.js"></script>
        <script type="text/javascript" src="scripts/scriptsprogram.js"></script>
        <script type="text/javascript" src="scripts/validator.js"></script>
        <script type="text/javascript" src="scripts/autotable.js"></script>
        <script type="text/javascript" src="scripts/rowmanager.js"></script>
        <script type="text/javascript" src="scripts/datacolumn.js"></script>
        <script type="text/javascript" src="scripts/customcolumn.js"></script>
        <script type="text/javascript" src="scripts/functions.js"></script>
        <script>${programData}</script>
        <script>${userData}</script>
        <link rel="stylesheet" href="css/program.css">
        <title>ECSSEN Programs</title>
    </head>
    <body class="body">
        <%@ include file="confirmation-modal.jsp" %>
        <%@ include file="navbar.jsp" %>
        
        
        <div class="main">
            <div style="margin: 40px 0 40px 0;">
                <div id="container" class="container container--list-size">          
                    <h1>Programs</h1>
                    <!--<h3 id="input-panel__header" class="panel-header">Add</h3>-->
                    <div class="list-panel" id="list-area">

    <!--                    <h3 class="panel-header">Programs</h3>-->
                        <div class="list-panel__filter">
                            <label style="color: gray;">Show Inactive</label>
                            <input id="program-filter" type="checkbox">
                        </div>
                        <div class="list-panel__top-buttons">
                            <input id="search-input" type="search" class="search-programs__input" placeholder="Search by Program, Manager">
                            <input id="notempty" type="button" class="add-program__button" onclick="addProgram()" value="New Program">
                        </div>
                        <div id="list-base" class="program-list__base">

                        </div>
                    </div>
                    <div class="input-panel" id="input-area">
                        <h3 id="input-panel__header" class="panel-header">Add Program</h3>
                        <form id="addProgramForm">
                            <input id="action" name="action" type="hidden">
                            <input id="program-ID" name="program-ID" type="hidden">
                            <input id="manager-ID" name="manager-ID" type="hidden">
                            <div id="program-name__input" style="margin: 30px 0 30px 0;"></div>
                            
                            <!--<div id="user-list"></div>-->

                            <div class="status-input">
                                <div class="DUMMY">
                                    <div id="manager-name__input"></div>
                                    <input id="user-search" placeholder="Search Users">
                                    <div id="user-list"></div>
                                </div>
                                
                                <!--<div class="DUMMY"></div>-->
                                <div id="status__input" >
                                    <select class="status__select" id="status" name="status">
                                        <option disabled hidden style='display: none' value=''></option>
                                        <option value="active">Active</option>
                                        <option value="inactive">Inactive</option>
                                    </select>
                                    <label class="status__label">Status</label>
                                </div>
                            </div>
                            <div class="input-action-buttons">
                                <input id="cancel__button" class="cancel__button" type="button" value="Cancel">
                                <input id="ok__button" class="ok__buton" type="button" value="Add">
                            </div>
                        </form>
                      </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>