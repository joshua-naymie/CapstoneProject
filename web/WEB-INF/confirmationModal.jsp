<%-- 
    Document   : ConfirmationModal
    Created on : Feb 26, 2022, 9:11:44 PM
    Author     : Main
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript" src="scripts/modal.js"></script>
<link rel="stylesheet" href="css/modal.css">

<div>
    <div id="modal-body" class="modal-background modal-background--invisible">
    </div>

    <div id="modal-window__container" class="modal-window__container modal-window--invisible">
        <div id="modal-window" class="modal-window">
        <p id="modal-message">You forgot to set the modal message!</p>
        <div class="modal-buttons">
            <input id="no-button" type="button" value="No">
            <input id="yes-button" type="button" value="Yes">
        </div>
    </div>
    </div>
    
</div>

