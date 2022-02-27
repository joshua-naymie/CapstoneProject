document.addEventListener('DOMContentLoaded', load, false);

var background;
var modalWindow;

var modalMessage;

var yesButton,
    noButton;

function load()
{
    background = document.getElementById("modal-body");
    modalWindow = document.getElementById("modal-window__container");
    
    modalMessage = document.getElementById("modal-message");
    
    yesButton = document.getElementById("yes-button");
    noButton = document.getElementById("no-button");
}

function showModal(message, onYes, onNo)
{
    background.style.display = "flex";
    modalWindow.style.display = "flex";
    
    setTimeout(() => { setVisibleCss(); }, 50);
    
    modalMessage.innerText = message;

    yesButton.addEventListener("click", onYes != null ? onYes : hideModal);
    noButton.addEventListener("click", onNo != null ? onNo : hideModal);
}

function setVisibleCss()
{
    background.classList.remove("modal-background--invisible");
    background.classList.add("modal-background--visible");

    modalWindow.classList.remove("modal-window--invisible");
    modalWindow.classList.add("modal-window--visible");
}


function hideModal()
{
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

    modalWindow.classList.add("modal-window--invisible");
    modalWindow.classList.remove("modal-window--visible");
}