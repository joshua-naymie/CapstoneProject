<%-- 
    Document   : ConfirmationModal
    Created on : Feb 26, 2022, 9:11:44 PM
    Author     : Main
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link rel="stylesheet" href="css/confirmation-modal.css">
<script type="text/javascript" src="scripts/confirmation-modal.js"></script>


<div>
    <div id="modal-body" class="modal-background modal-background--invisible">
    </div>

    <div id="modal-container" class="modal-container modal-container--invisible">
        <div id="modal-window" class="modal-window">
            <p id="modal-message">You forgot to set the modal message!</p>
            <div class="modal-buttons">
                <input id="no-button" class="cancel__button default__button" type="button" value="No">
                <input id="yes-button" class="default__button" type="button" value="Yes">
            </div>
        </div>
    </div>
</div>

