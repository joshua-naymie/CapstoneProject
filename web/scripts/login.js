document.addEventListener('DOMContentLoaded', load, false);


const CSS_INPUTGROUP_MAIN = "main-input";
const MESSAGE_REQUIRED = "required";
const MESSAGE_INVALID = "invalid";

var loginInputs;// = new InputGroupCollection();
var forgotPWD;

function load()
{
    loginInputs = new InputGroupCollection();
    let inputArea = document.getElementById("inputs");

    let usernameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "username");
    let passwordInput = new InputGroup(CSS_INPUTGROUP_MAIN, "password");

    usernameInput.setLabelText("Email:");
    usernameInput.setPlaceHolderText("j.smith@gmail.com");
    usernameInput.setEnterFunction(login);
    usernameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    usernameInput.addValidator(REGEX_EMAIL, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    inputArea.appendChild(usernameInput.container);
    loginInputs.add(usernameInput);

    passwordInput.setLabelText("Password:");
    passwordInput.input.type = "password";
    passwordInput.setEnterFunction(login);
    passwordInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    inputArea.appendChild(passwordInput.container);
    loginInputs.add(passwordInput);

    // Forgot password link
    forgotPWD = document.createElement("a");
    var link = document.createTextNode("Forgot Password?");
    forgotPWD.classList.add("forgot-link");
    forgotPWD.appendChild(link);
    forgotPWD.href = "forgot";
    inputArea.appendChild(forgotPWD);

    inputArea.appendChild(document.createElement("hr"));

    let loginButton = document.createElement("a");
    loginButton.classList.add("login-button");
    loginButton.innerText = "Login";
    loginButton.addEventListener("click", login);

    inputArea.appendChild(loginButton);
}

function login()
{
    if (loginInputs.validateAll())
    {
        postAction("login", "login-form", "login");
    }
}