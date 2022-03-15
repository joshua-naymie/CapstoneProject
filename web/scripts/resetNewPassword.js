document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";
const MESSAGE_REQUIRED = "required";
const MESSAGE_INVALID = "invalid";

var newInput;// = new InputGroupCollection();
var newPassInput;
var confirmPassInput;

var myInput = document.getElementById("newPassUser"); // Password validation list start
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var length = document.getElementById("length"); // Password validation list end

function load()
{
    newInput = new InputGroupCollection();
    let inputArea = document.getElementById("inputs");

    newPassInput = new InputGroup(CSS_INPUTGROUP_MAIN, "newPassUser");
    confirmPassInput = new InputGroup(CSS_INPUTGROUP_MAIN, "confirmPassUser");

    newPassInput.setLabelText("Enter a New Password:");
    newPassInput.setPlaceHolderText("Enter new password");
    newPassInput.setEnterFunction(newPass);
    inputArea.appendChild(newPassInput.container);
    newInput.add(newPassInput);

    confirmPassInput.setLabelText("Confirm New Password:");
    confirmPassInput.setPlaceHolderText("Retype password");
    confirmPassInput.setEnterFunction(newPass);
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

    newPassInput.input.addEventListener("focus", focusFunction, true); // Y U no work
    newPassInput.input.addEventListener("blur", blurFunction, true); // Y U no work x2

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
        postAction("forgot", "newPassword-form", "forgot");
    }
}


// NEW STUFF

// When the user clicks on the password field, show the message box




//// When the user starts to type something inside the password field
myInput.input.onkeyup = function () {
    // Validate lowercase letters
    var lowerCaseLetters = /[a-z]/g;
    if (myInput.value.match(lowerCaseLetters)) {
        letter.classList.remove("invalid");
        letter.classList.add("valid");
    } else {
        letter.classList.remove("valid");
        letter.classList.add("invalid");
    }

    // Validate capital letters
    var upperCaseLetters = /[A-Z]/g;
    if (myInput.value.match(upperCaseLetters)) {
        capital.classList.remove("invalid");
        capital.classList.add("valid");
    } else {
        capital.classList.remove("valid");
        capital.classList.add("invalid");
    }

    // Validate numbers
    var numbers = /[0-9]/g;
    if (myInput.value.match(numbers)) {
        number.classList.remove("invalid");
        number.classList.add("valid");
    } else {
        number.classList.remove("valid");
        number.classList.add("invalid");
    }

    // Validate length
    if (myInput.value.length >= 8) {
        length.classList.remove("invalid");
        length.classList.add("valid");
    } else {
        length.classList.remove("valid");
        length.classList.add("invalid");
    }
}
