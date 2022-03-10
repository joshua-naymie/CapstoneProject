document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";
const MESSAGE_REQUIRED = "required";
const MESSAGE_INVALID = "invalid";

var newInput;// = new InputGroupCollection();

function load()
{
    newInput = new InputGroupCollection();
    let inputArea = document.getElementById("inputs");

    let newPassInput = new InputGroup(CSS_INPUTGROUP_MAIN, "fEmail");

    newPassInput.setLabelText("Enter a New Password:");
    newPassInput.setPlaceHolderText("Enter new password");
    newPassInput.setEnterFunction(newPass);
    inputArea.appendChild(newPassInput.container);
    newInput.add(newPassInput);

    inputArea.appendChild(document.createElement("hr"));

    let submitButton = document.createElement("a");
    submitButton.classList.add("submit-button");
    submitButton.innerText = "Submit";
    submitButton.addEventListener("click", newPass);

    inputArea.appendChild(submitButton);
}

function newPass()
{
    if (newInput.validateAll())
    {
        postAction("forgot", "newPassword-form", "forgot");
    }
}