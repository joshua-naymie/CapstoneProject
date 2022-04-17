document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";
const MESSAGE_REQUIRED = "required";
const MESSAGE_INVALID = "invalid";
const PASSWORDS_IMPROPER_FORMAT = "Check password format";
const PASSWORDS_DONT_MATCH = "Passwords do not match";

var newInput;// = new InputGroupCollection();
var newPassInput;
var confirmPassInput;

var myInput;
var letter;
var capital;
var number;
var length;

function load()
{
    newInput = new InputGroupCollection();
    let inputArea = document.getElementById("inputs");

    newPassInput = new InputGroup(CSS_INPUTGROUP_MAIN, "newPassUser");
    confirmPassInput = new InputGroup(CSS_INPUTGROUP_MAIN, "confirmPassUser");

    newPassInput.setLabelText("Enter a New Password:");
    newPassInput.setPlaceHolderText("Enter new password");
    newPassInput.setEnterFunction(newPass);
    newPassInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    inputArea.appendChild(newPassInput.container);
    newInput.add(newPassInput);

    newPassInput.input.addEventListener("keyup", keyUpFunction, true);

    confirmPassInput.setLabelText("Confirm New Password:");
    confirmPassInput.setPlaceHolderText("Retype password");
    confirmPassInput.setEnterFunction(newPass);
    confirmPassInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    inputArea.appendChild(confirmPassInput.container);
    newInput.add(confirmPassInput);

    document.getElementById('newPassUser').type = 'password'; // this code switches the password input from plaintext to password format
    document.getElementById('confirmPassUser').type = 'password'; // this code switches the password input from plaintext to password format

    inputArea.appendChild(document.createElement("hr"));

    let submitButton = document.createElement("a");
    submitButton.classList.add("submit-button");
    submitButton.innerText = "Submit";
    submitButton.addEventListener("click", newPass);

    inputArea.appendChild(submitButton);

    myInput = document.getElementById("newPassUser"); // Password validation list start
    letter = document.getElementById("letter"); // Retrieves element from JSP
    capital = document.getElementById("capital"); // Retrieves element from JSP
    number = document.getElementById("number"); // Retrieves element from JSP
    length = document.getElementById("length"); // Password validation list ends

    newPassInput.input.addEventListener("focus", focusFunction, true);
    newPassInput.input.addEventListener("blur", blurFunction, true);
    newPassInput.input.addEventListener("keyup", keyUpFunction, true);
    confirmPassInput.addValidator(() => {console.log(confirmPassInput.input.value); return confirmPassInput.input.value === newPassInput.input.value;}, INPUTGROUP_STATE_WARNING, PASSWORDS_DONT_MATCH);

}

function focusFunction() {
    document.getElementById("message").style.display = "block";
}

function blurFunction() {
    document.getElementById("message").style.display = "none";
}

function newPass()
{
    if (newInput.validateAll())
    {
        postAction("userAccount", "newPassword-form", "userAccount");
    }
}

// When the user starts to type something inside the password field
// SOURCE THAT HELPED IN CREATING THIS FUNCTION: w3schools.com
function keyUpFunction() {
    // Validate lowercase letters
    var lowerCaseLetters = /[a-z]/g;
    if (newPassInput.input.value.match(lowerCaseLetters)) {
        letter.classList.remove("invalid");
        letter.classList.add("valid");
    } else {
        letter.classList.remove("valid");
        letter.classList.add("invalid");
        newPassInput.addValidator(REGEX_PROPER_PASSWORD, INPUTGROUP_STATE_WARNING, PASSWORDS_IMPROPER_FORMAT);
    }

    // Validate capital letters
    var upperCaseLetters = /[A-Z]/g;
    if (newPassInput.input.value.match(upperCaseLetters)) {
        capital.classList.remove("invalid");
        capital.classList.add("valid");
    } else {
        capital.classList.remove("valid");
        capital.classList.add("invalid");
        newPassInput.addValidator(REGEX_PROPER_PASSWORD, INPUTGROUP_STATE_WARNING, PASSWORDS_IMPROPER_FORMAT);
    }

    // Validate numbers
    var numbers = /[0-9]/g;
    if (newPassInput.input.value.match(numbers)) {
        number.classList.remove("invalid");
        number.classList.add("valid");
    } else {
        number.classList.remove("valid");
        number.classList.add("invalid");
        newPassInput.addValidator(REGEX_PROPER_PASSWORD, INPUTGROUP_STATE_WARNING, PASSWORDS_IMPROPER_FORMAT);
    }

    // Validate length
    if (newPassInput.input.value.length >= 8) {
        length.classList.remove("invalid");
        length.classList.add("valid");
    } else {
        length.classList.remove("valid");
        length.classList.add("invalid");
        newPassInput.addValidator(REGEX_PROPER_PASSWORD, INPUTGROUP_STATE_WARNING, PASSWORDS_IMPROPER_FORMAT);
    }
}

function bothFieldsNeedToMatch() {
    if (confirmPassInput !== newPassInput) {
       alert("Works");
    }
    else {
        alert("Doesn't work");
    }
}




