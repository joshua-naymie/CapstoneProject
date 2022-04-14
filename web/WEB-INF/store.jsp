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
        <script type="text/javascript" src="scripts/store-scripts.js"></script>
        <script type="text/javascript" src="scripts/models/validator.js"></script>
        <script type="text/javascript" src="scripts/models/autolist.js"></script>
        <script type="text/javascript" src="scripts/models/functions.js"></script>
        <script>${storeData}</script>
        <script>${companyData}</script>

        <link rel="stylesheet" href="css/store-styles.css">
        <title>ECSSEN Stores</title>
    </head>
    <body class="body">
        <%@ include file="confirmationModal.jsp" %>
        <%@ include file="navbar.jsp" %>
        
        
        <div class="main">
            <div style="margin: 40px 0 40px 0;">
                <div id="container" class="container container--list-size">          
                    <h1 id="store-header" class="header">Stores</h1>
                    <!--<h3 id="input-panel__header" class="panel-header">Add</h3>-->
                    <div class="list-panel" id="list-area">

    <!--                    <h3 class="panel-header">Programs</h3>-->
                        <div class="list-panel__filter">
                            <label style="color: gray;">Show Inactive</label>
                            <input id="store-filter" type="checkbox">
                        </div>
                        <div class="list-panel__top-buttons">
                            <input id="search-input" type="search" class="search-stores__input" placeholder="Search by Store, Company or Address">
                            <input id="notempty" type="button" class="default__button" onclick="addStore()" value="New Store">
                        </div>
                        <div id="list-base" class="store-list__base">

                        </div>
                    </div>
                    <div class="input-panel" id="input-area">
                        <form id="addStoreForm">
                            <input id="action" name="action" type="hidden">
                            <input id="store-ID" name="store-ID" type="hidden">
                            
                            <div id="store-name__input" style="margin: 2px 0 10px 0;"></div>
                            <div id="company__input" style="margin: 2px 0 50px 0;"></div>
                            <datalist id="company-list">
                            </datalist>
                            
                            <div id="street-address__input" style="margin: 2px 0 10px 0;"></div>
                            
                            <div class="address-inputs">
                                <div id="city__input"></div>
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