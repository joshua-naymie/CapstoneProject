document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";
const MESSAGE_REQUIRED = "required";
const MESSAGE_INVALID = "invalid";

var inputCollection;

var firstNameInput,
    lastNameInput,
    emailInput,
    phoneInput,
    birthdayInput,
    streetInput,
    cityInput,
    postalCodeInput,
    passwordInput,
    signupDateInput,
    adminInput,
    activeInput;

function load()
{
    let containerLeft = document.getElementById("container-left"),
        containerRight = document.getElementById("container-right");
    
    firstNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_firstname"),
    lastNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_lastname"),
    emailInput = new InputGroup(CSS_INPUTGROUP_MAIN, "username"),
    phoneInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_phone"),
    birthdayInput = new InputGroup(CSS_INPUTGROUP_MAIN, "birthday"),
    streetInput = new InputGroup(CSS_INPUTGROUP_MAIN, "street"),
    cityInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_city"),
    postalCodeInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_postalcode"),
    passwordInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_password"),
    signupDateInput = new InputGroup(CSS_INPUTGROUP_MAIN, "signupdate"),
    adminInput = new InputGroup(CSS_INPUTGROUP_MAIN, "admin"),
    activeInput = new InputGroup(CSS_INPUTGROUP_MAIN, "active");

    
    firstNameInput.setLabelText("First Name:");
    firstNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    firstNameInput.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
//    firstNameInput.setEnterFunction(phoneInput);
    firstNameInput.setPlaceHolderText("John");
    containerLeft.appendChild(firstNameInput.container);
    
    lastNameInput.setLabelText("Last Name:");
    lastNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    lastNameInput.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
//    lastNameInput.setEnterFunction(phoneInput);
    lastNameInput.setPlaceHolderText("Smith");
    containerRight.appendChild(lastNameInput.container);
    
    emailInput.setLabelText("Email:");
    emailInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    emailInput.addValidator(REGEX_EMAIL, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
//    emailInput.setEnterFunction(phoneInput);
    emailInput.setPlaceHolderText("jsmith@aol.com");
    containerLeft.appendChild(emailInput.container);
    
    phoneInput.setLabelText("Phone:");
    phoneInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    phoneInput.addValidator(REGEX_PHONE, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
//    phoneInput.setEnterFunction(phoneInput);
    phoneInput.setPlaceHolderText("555-555-5555");
    containerRight.appendChild(phoneInput.container);

    birthdayInput.setLabelText("Birthday:");
    //birthdayInput.input.type = "date";
    birthdayInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    birthdayInput.addValidator(REGEX_EMAIL, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
//    birthdayInput.setEnterFunction(phoneInput);
    containerLeft.appendChild(birthdayInput.container);
    
    streetInput.setLabelText("Street:");
    streetInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
//    streetInput.setEnterFunction(phoneInput);
    streetInput.setPlaceHolderText("112 Edgedale Dr. NW");
    containerRight.appendChild(streetInput.container);
    
    cityInput.setLabelText("City:");
    cityInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
//    cityInput.setEnterFunction(phoneInput);
    containerLeft.appendChild(cityInput.container);
    
    postalCodeInput.setLabelText("Postal Code:");
    postalCodeInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    postalCodeInput.addValidator(REGEX_POSTAL_CODE, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
//    postalCodeInput.setEnterFunction(phoneInput);
    containerRight.appendChild(postalCodeInput.container);
    
//    passwordInput.setLabelText("Password:");
//    passwordInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
////    cityInput.setEnterFunction(phoneInput);
//    containerLeft.appendChild(passwordInput.container);
    
    signupDateInput.setLabelText("Signup Date:");
   // signupDateInput.input.type = "date";
    signupDateInput.input.value = new Date().toISOString().substring(0,10);
//    signupDateInput.input.disabled = "true";
    signupDateInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
//    cityInput.setEnterFunction(phoneInput);
    containerRight.appendChild(signupDateInput.container);
    console.log(signupDateInput.input.value);
    
    adminInput.setLabelText("Admin:");
    adminInput.input.type = "checkbox";
//    postalCodeInput.setEnterFunction(phoneInput);
    containerLeft.appendChild(adminInput.container);
    
    activeInput.setLabelText("Active:");
    activeInput.input.type = "checkbox";
//    postalCodeInput.setEnterFunction(phoneInput);
    containerRight.appendChild(activeInput.container);
    
//    containerRight.appendChild(checkboxDiv);

    // Create buttons for the form
    let submitButton = document.createElement("button");
    submitButton.innerHTML = "Submit";
    submitButton.type = "submit";
    submitButton.className = "btn";
    submitButton.id = "submit-button";
    submitButton.name = "action";
    submitButton.value = "Add";
    
    let cancelButton = document.createElement("button");
    cancelButton.innerHTML = "Cancel";
    cancelButton.type = "reset";
    cancelButton.className = "btn";
    cancelButton.id = "cancel-button";
    
    // Add "Submit" and "Cancel" button to the DOM
    containerLeft.appendChild(cancelButton);
    containerRight.appendChild(submitButton);

    

    //--------------
//
    inputCollection = new InputGroupCollection();
//
    inputCollection.add(firstNameInput);
    inputCollection.add(lastNameInput);
    inputCollection.add(emailInput);
    inputCollection.add(phoneInput);
    inputCollection.add(birthdayInput);
    inputCollection.add(streetInput);
    inputCollection.add(cityInput);
    inputCollection.add(postalCodeInput);
    
    if(typeof editUser !== "undefined")
    {
        document.getElementById("header").innerText = "Edit User";
        populateFields();
        submitButton.name = "action";
        submitButton.value = "Save";
    }
}

function validateUserInfo()
{
    document.getElementById("body").style.backgroundColor = inputCollection.validateAll() ? "green" : "red";
}

function populateFields()
{
    firstNameInput.setInputText(editUser.firstName);
    lastNameInput.setInputText(editUser.lastName);
    emailInput.setInputText(editUser.id);
    phoneInput.setInputText(editUser.phoneNum);
    birthdayInput.setInputText(editUser.DOB);
    streetInput.setInputText(editUser.address);
    cityInput.setInputText(editUser.city);
    postalCodeInput.setInputText(editUser.postalCode);
    passwordInput.setInputText(editUser.password);
    signupDateInput.setInputText(editUser.regDate);
    
    adminInput.input.checked = editUser.isAdmin;
    activeInput.input.checked = editUser.isActive;
}