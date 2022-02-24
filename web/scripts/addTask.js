document.addEventListener("DOMContentLoaded", load, false);

const CSS_INPUTGROUP_MAIN = "main-input";
const MESSAGE_REQUIRED = "required";
const MESSAGE_INVALID = "invalid";

let inputCollection;

let programInput,
  cityInput,
  startTimeInput,
  endTimeInput,
  descriptionInput,
  dateInput,
  supervisorInput;

function load() {
  let containerLeft = document.getElementById("container-left"),
    containerRight = document.getElementById("container-right");

  (programInput = new InputGroup(CSS_INPUTGROUP_MAIN, "program")),
    (cityInput = new InputGroup(CSS_INPUTGROUP_MAIN, "city")),
    (startTimeInput = new InputGroup(CSS_INPUTGROUP_MAIN, "startTime")),
    (endTimeInput = new InputGroup(CSS_INPUTGROUP_MAIN, "endTime")),
    (dateInput = new InputGroup(CSS_INPUTGROUP_MAIN, "date")),
    (descriptionInput = new InputGroup(CSS_INPUTGROUP_MAIN, "description")),
    (supervisorInput = new InputGroup(CSS_INPUTGROUP_MAIN, "supervisor")),
    programInput.setLabelText("Program Name:");
  programInput.addValidator(
    REGEX_NOT_EMPTY,
    INPUTGROUP_STATE_ERROR,
    MESSAGE_REQUIRED
  );
  programInput.setPlaceHolderText("John");
  containerLeft.appendChild(programInput.container);

  cityInput.setLabelText("City: ");
  cityInput.addValidator(
    REGEX_NOT_EMPTY,
    INPUTGROUP_STATE_ERROR,
    MESSAGE_REQUIRED
  );
  cityInput.setPlaceHolderText("Calgary");
  containerRight.appendChild(cityInput.container);

  startTimeInput.setLabelText("Start Time: ");
  startTimeInput.input.type = "time";
  startTimeInput.addValidator(
    REGEX_NOT_EMPTY,
    INPUTGROUP_STATE_ERROR,
    MESSAGE_REQUIRED
  );
  containerLeft.appendChild(startTimeInput.container);

  endTimeInput.setLabelText("End Time: ");
  endTimeInput.input.type = "time";
  endTimeInput.addValidator(
    REGEX_NOT_EMPTY,
    INPUTGROUP_STATE_ERROR,
    MESSAGE_REQUIRED
  );
  containerRight.appendChild(endTimeInput.container);

  descriptionInput.setLabelText("Description: ");
  descriptionInput.setPlaceHolderText("Some short description for the task.");
  containerLeft.appendChild(descriptionInput.container);

  dateInput.setLabelText("Date: ");
  dateInput.input.type = "date";
  dateInput.addValidator(
    REGEX_NOT_EMPTY,
    INPUTGROUP_STATE_ERROR,
    MESSAGE_REQUIRED
  );
  containerRight.appendChild(dateInput.container);

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

  //
  inputCollection = new InputGroupCollection();
  //
  inputCollection.add(programInput);
  inputCollection.add(cityInput);
  inputCollection.add(startTimeInput);
  inputCollection.add(endTimeInput);
  inputCollection.add(descriptionInput);
  inputCollection.add(dateInput);
}

function validateUserInfo() {
  document.getElementById("body").style.backgroundColor =
    inputCollection.validateAll() ? "green" : "red";
}

function populateFields() {
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
