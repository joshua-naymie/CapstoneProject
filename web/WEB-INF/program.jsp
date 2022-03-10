<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/png" href="content/images/favicon-32x32.png" sizes="32x32">
        <link rel="icon" type="image/svg+xml" href="content/images/favicon-svg.svg">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="scripts/inputgroup.js"></script>
        <script type="text/javascript" src="scripts/inputgroupcollection.js"></script>
        <script type="text/javascript" src="scripts/regexprogram.js"></script>
        <script type="text/javascript" src="scripts/scriptsprogram.js"></script>
        <script type="text/javascript" src="scripts/validator.js"></script>
        <script type="text/javascript" src="scripts/autolist.js"></script>
        <script type="text/javascript" src="scripts/functions.js"></script>
        <script>${programData}</script>
        <script>${userData}</script>
        <link rel="stylesheet" href="css/program.css">
        <title>ECSSEN Programs</title>
    </head>
    <body class="body">
        <%@ include file="confirmationModal.jsp" %>
        <%@ include file="navbar.jsp" %>
        
        
        <div class="main">
            <div style="margin: 40px 0 40px 0;">
                <div id="container" class="container container--list-size">          
                    <h1 id="programs-header" class="header">Programs</h1>
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
                        <form id="addProgramForm">
                            <input id="action" name="action" type="hidden">
                            <input id="program-ID" name="program-ID" type="hidden">
                            <input id="manager-ID" name="manager-ID" type="hidden">
                            
                            <div id="program-name__input" style="margin: 2px 0 30px 0;"></div>
                            
                            <div class="status-input">
                                <div id="manager-name__display" class="manager-name__display"></div>
                                <!--<img src="content/images/cancel-red_1.png" width="25px">-->
                                <input id="remove-manager" class="remove-manager--hidden" type="button"  value="remove">
                                <div id="status__input" >
                                    <select class="status__select" id="status" name="status">
                                        <option disabled hidden style='display: none' value=''></option>
                                        <option value="active">Active</option>
                                        <option value="inactive">Inactive</option>
                                    </select>
                                    <label class="status__label">Status</label>
                                </div>
                            </div>
                            
                            <input id="user-search" class="user-search" placeholder="Search by Name, Email">
                            <div id="user-list" class="user-list"></div>
                            
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