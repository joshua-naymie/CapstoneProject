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
                    <div class="list-panel" id="list-area">
                        <div class="list-panel__top-buttons">
                            <input id="search-input" type="search" class="search-teams__input" placeholder="Search by Name">
                            <input id="notempty" type="button" class="default__button" onclick="addTeam()" value="New Team">
                        </div>
                        <div id="team-list" class="team-list__base"></div>
                    </div>
                    <div class="input-panel" id="input-area">
                        <form id="addTeamForm">
                            <input id="action" name="action" type="hidden">
                            <input id="team-ID" name="team-ID" type="hidden">
                            <input id="program-ID" name="program-ID" type="hidden">
                            <input id="store-ID" name="store-ID" type="hidden">
                            <!--<input id="supervisor-ID" name="supervisor-ID" type="hidden">-->
                            
                            <div id="team-name__input" style="margin: 2px 0 10px 0;"></div>
                            
                            <div class="select__input">
                                <select id="programs_select" name="program">
                                    <option disabled hidden style='display: none' value=''></option>
                                </select>
                                <label class="status__label">Program</label>
                            </div>
                            
                            <div class="select__input">
                                <select id="supervisors_select" name="supervisor-ID">
                                    <option disabled hidden style='display: none' value=''></option>
                                </select>
                                <label class="status__label">Supervisor</label>
                            </div>
                            
                            <div id="max-size__input" style="margin: 2px 0 26px 0;"></div>
                            
                            <input id="store-search" class="store-search" placeholder="Search by Name, Address ">
                            <div id="store-list" class="store-list__base"></div>
                            
                            <div class="input-action-buttons">
                                <input id="cancel__button" class="cancel__button default__button" type="button" value="Cancel">
                                <input id="ok__button" class="add__button default__button" type="button" value="Add">
                            </div>
                        </form>
                      </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>