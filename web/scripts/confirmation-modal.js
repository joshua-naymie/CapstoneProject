document.addEventListener('DOMContentLoaded', load, false);

var background;
var modalWindow;

var modalMessage;

var yesButton,
    noButton;

function load()
{
    background = document.getElementById("modal-body");
    modalWindow = document.getElementById("modal-container");
    
    modalMessage = document.getElementById("modal-message");
    
    yesButton = document.getElementById("yes-button");
    noButton = document.getElementById("no-button");
}

function showConfirmationModal(message, onYes, onNo)
{
    background.style.display = "flex";
    modalWindow.style.display = "flex";
    
    setTimeout(() => { setVisibleCss(); }, 50);
    
    modalMessage.innerText = message;

    yesButton.addEventListener("click", onYes != null ? onYes : hideConfirmationModal);
    noButton.addEventListener("click", onNo != null ? onNo : hideConfirmationModal);
}

function setVisibleCss()
{
    background.classList.remove("modal-background--invisible");
    background.classList.add("modal-background--visible");

    modalWindow.classList.remove("modal-container--invisible");
    modalWindow.classList.add("modal-container--visible");
    document.getElementById("modal-window").classList.add("modal-zoom");
}


function hideConfirmationModal()
{
    document.getElementById("modal-window").classList.remove("modal-zoom");
    setInvisibleCss();
    
    setTimeout(() => { 
        background.style.display = "none";
        modalWindow.style.display = "none";
    }, 340);
}

function setInvisibleCss()
{
    background.classList.add("modal-background--invisible");
    background.classList.remove("modal-background--visible");

    modalWindow.classList.add("modal-container--invisible");
    modalWindow.classList.remove("modal-container--visible");
}