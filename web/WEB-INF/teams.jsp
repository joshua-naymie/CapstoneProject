<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/png" href="content/images/favicon-32x32.png" sizes="32x32">
        <link rel="icon" type="image/svg+xml" href="content/images/favicon-svg.svg">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="scripts/models/inputgroup.js"></script>
        <script type="text/javascript" src="scripts/models/inputgroupcollection.js"></script>
        <script type="text/javascript" src="scripts/models/regexs.js"></script>
        <script type="text/javascript" src="scripts/team-scripts.js"></script>
        <script type="text/javascript" src="scripts/models/validator.js"></script>
        <script type="text/javascript" src="scripts/models/autolist.js"></script>
        <script type="text/javascript" src="scripts/models/functions.js"></script>
        <script>${teamData}</script>
        <script>${storeData}</script>
        <script>${supervisorData}</script>
        <script>${programData}</script>

        <link rel="stylesheet" href="css/teams-styles.css">
        <title>ECSSEN Teams</title>
    </head>
    <body class="body">
        <%@ include file="confirmationModal.jsp" %>
        <%@ include file="navbar.jsp" %>
        
        
        <div class="main">
            <div style="margin: 40px 0 40px 0;">
                <div id="container" class="container container--list-size">          
                    <h1 id="team-header" class="header">Teams</h1>
                    <!--<h3 id="input-panel__header" class="panel-header">Add</h3>-->
                    <div class="list-panel" id="list-area">

    <!--                    <h3 class="panel-header">Programs</h3>-->
                        <div class="list-panel__filter">
                            <label style="color: gray;">Show Inactive</label>
                            <input id="team-filter" type="checkbox">
                        </div>
                        <div class="list-panel__top-buttons">
                            <input id="search-input" type="search" class="search-teams__input" placeholder="Search by Program, Manager">
                            <input id="notempty" type="button" class="add-team__button" onclick="addTeam()" value="New Team">
                        </div>
                        <div id="list-base" class="team-list__base"></div>
                    </div>
                    <div class="input-panel" id="input-area">
                        <form id="addTeamForm">
                            <input id="action" name="action" type="hidden">
                            <input id="team-ID" name="team-ID" type="hidden">
                            
                            <div id="team-name__input" style="margin: 2px 0 10px 0;"></div>
                            <div id="company__input" style="margin: 2px 0 50px 0;"></div>
                            <datalist id="company-list">
                            </datalist>
                            
                            <div id="street-address__input" style="margin: 2px 0 10px 0;"></div>
                            
                            <div class="address-inputs">
                                <div id="city__input"></div>
                                <div id="province__input"></div>
                                <div id="postal-code__input"></div>
                            </div>
                            
                            <div id="contact__input" style="margin: 2px 0 10px 0;"></div>
                            
                            <div class="status-input">
                                <div id="phone__input" style="margin: 2px 0 30px 0;"></div>
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