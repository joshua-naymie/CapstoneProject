document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";
const MESSAGE_REQUIRED = "required";
const MESSAGE_INVALID = "invalid";

const validateNumber = (input) => {
    input = Number.parseFloat(input);
    return (input > 0) && (input < 100);
};

var inputCollection;

function load()
{
    let emailInput = new InputGroup(CSS_INPUTGROUP_MAIN, "email"),
        phoneInput = new InputGroup(CSS_INPUTGROUP_MAIN, "phone"),
        amountInput = new InputGroup("secondary-input", "amount");

    
    emailInput.setLabelText("Email:");
    emailInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    emailInput.addValidator(REGEX_EMAIL, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    emailInput.setEnterFunction(phoneInput);
    emailInput.setPlaceHolderText("joe@aol.com")
    document.getElementById("main").appendChild(emailInput.container);

    //--------------

    phoneInput.setLabelText("Phone:");
    phoneInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    phoneInput.addValidator(REGEX_PHONE, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    phoneInput.setEnterFunction(amountInput);
    phoneInput.setPlaceHolderText("555-555-5555");
    document.getElementById("main").appendChild(phoneInput.container);

    //--------------

    amountInput.setLabelText("Amount:");
    amountInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    amountInput.addValidator(REGEX_NUMBERS, INPUTGROUP_STATE_WARNING, "non-number");
    amountInput.addValidator(validateNumber, INPUTGROUP_STATE_WARNING, "out of range");
    amountInput.setEnterFunction(validate);
    amountInput.setInputText("10");
    document.getElementById("main").appendChild(amountInput.container);


    //--------------

    inputCollection = new InputGroupCollection();

    inputCollection.add(emailInput);
    inputCollection.add(phoneInput);
    inputCollection.add(amountInput);
}

function validate()
{
    document.getElementById("body").style.backgroundColor = inputCollection.validateAll() ? "green" : "red";
}