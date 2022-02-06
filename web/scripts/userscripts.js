document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";
const MESSAGE_REQUIRED = "required";
const MESSAGE_INVALID = "invalid";

var inputCollection;

function load()
{
    let containerLeft = document.getElementById("container-left"),
        containerRight = document.getElementById("container-right");
    
    let firstNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "fname"),
        lastNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "lname"),
        emailInput = new InputGroup(CSS_INPUTGROUP_MAIN, "email"),
        phoneInput = new InputGroup(CSS_INPUTGROUP_MAIN, "phone"),
        birthdayInput = new InputGroup(CSS_INPUTGROUP_MAIN, "birthday"),
        streetInput = new InputGroup(CSS_INPUTGROUP_MAIN, "street"),
        cityInput = new InputGroup(CSS_INPUTGROUP_MAIN, "city"),
        postalCodeInput = new InputGroup(CSS_INPUTGROUP_MAIN, "postalcode");

    
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
    birthdayInput.input.type = "date";
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
    cityInput.input.type = "date";
    cityInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
//    cityInput.setEnterFunction(phoneInput);
    containerLeft.appendChild(cityInput.container);
    
    postalCodeInput.setLabelText("Postal Code:");
    postalCodeInput.input.type = "date";
    postalCodeInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    postalCodeInput.addValidator(REGEX_POSTAL_CODE, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
//    postalCodeInput.setEnterFunction(phoneInput);
    containerRight.appendChild(postalCodeInput.container);


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
}

function validateUserInfo()
{
    document.getElementById("body").style.backgroundColor = inputCollection.validateAll() ? "green" : "red";
}