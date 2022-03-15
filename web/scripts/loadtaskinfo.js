document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";
const MESSAGE_REQUIRED = "required";
const MESSAGE_INVALID = "invalid";

var inputCollection;

var taskIDFromDB,
        programIDFromDB,
        teamIDFromDB,
        userIDFromDB,
        firstNameInput,
        lastNameInput,
        programNameFromDB,
        taskCityInput,
        startTimeInput,
        endTimeInput,
        approvalNotesFromDB;

function load()
{
    let containerLeft = document.getElementById("container-left"),
            containerRight = document.getElementById("container-right");

    taskIDFromDB = new InputGroup(CSS_INPUTGROUP_MAIN, "task_id_db"),
            programIDFromDB = new InputGroup(CSS_INPUTGROUP_MAIN, "program_id_db"),
            teamIDFromDB = new InputGroup(CSS_INPUTGROUP_MAIN, "team_id_db"),
            userIDFromDB = new InputGroup(CSS_INPUTGROUP_MAIN, "user_id_db"),
            firstNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_firstname"),
            lastNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_lastname"),
            programNameFromDB = new InputGroup(CSS_INPUTGROUP_MAIN, "program_name_db"),
            taskCityInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_city"),
            startTimeInput = new InputGroup(CSS_INPUTGROUP_MAIN, "start_time"),
            endTimeInput = new InputGroup(CSS_INPUTGROUP_MAIN, "end_time"),
            approvalNotesFromDB = new InputGroup(CSS_INPUTGROUP_MAIN, "approval_notes_db");

    taskIDFromDB.setLabelText("Task ID:");
    taskIDFromDB.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    taskIDFromDB.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    taskIDFromDB.setPlaceHolderText("n");
    containerLeft.appendChild(taskIDFromDB.container);

    programIDFromDB.setLabelText("Program ID:");
    programIDFromDB.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    programIDFromDB.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    programIDFromDB.setPlaceHolderText("n");
    containerRight.appendChild(programIDFromDB.container);

    teamIDFromDB.setLabelText("Team ID:");
    teamIDFromDB.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    teamIDFromDB.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    teamIDFromDB.setPlaceHolderText("n");
    containerLeft.appendChild(teamIDFromDB.container);

    userIDFromDB.setLabelText("User ID:");
    userIDFromDB.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    userIDFromDB.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    userIDFromDB.setPlaceHolderText("n");
    containerRight.appendChild(userIDFromDB.container);

    firstNameInput.setLabelText("First Name:");
    firstNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    firstNameInput.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    firstNameInput.setPlaceHolderText("John");
    containerLeft.appendChild(firstNameInput.container);

    lastNameInput.setLabelText("Last Name:");
    lastNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    lastNameInput.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    lastNameInput.setPlaceHolderText("Smith");
    containerRight.appendChild(lastNameInput.container);

    programNameFromDB.setLabelText("Program Name:");
    programNameFromDB.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    programNameFromDB.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    programNameFromDB.setPlaceHolderText("Hotline");
    containerLeft.appendChild(programNameFromDB.container);

    taskCityInput.setLabelText("City:");
    taskCityInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    taskCityInput.setPlaceHolderText("Calgary");
    containerRight.appendChild(taskCityInput.container);

    startTimeInput.setLabelText("Start Time:");
    startTimeInput.input.type = "datetime-local";
    startTimeInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    startTimeInput.addValidator(REGEX_EMAIL, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    containerLeft.appendChild(startTimeInput.container);

    endTimeInput.setLabelText("End Time:");
    endTimeInput.input.type = "datetime-local";
    endTimeInput.input.value = new Date().toISOString().substring(0, 10);
    endTimeInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    containerRight.appendChild(endTimeInput.container);
    console.log(endTimeInput.input.value);

    approvalNotesFromDB.setLabelText("Comments:");
    approvalNotesFromDB = document.createElement("TEXTAREA");
    var comments = document.createTextNode("Additional Notes");
    approvalNotesFromDB.appendChild(comments);
    container.appendChild(approvalNotesFromDB);

    // Create buttons for the form
    let approveButton = document.createElement("button");
    approveButton.innerHTML = "Approve";
    approveButton.type = "submit";
    approveButton.className = "btn";
    approveButton.id = "approve-submit-button";
    approveButton.name = "action";
    approveButton.value = "Add";

    let disapproveButton = document.createElement("button");
    disapproveButton.innerHTML = "Disapprove";
    disapproveButton.type = "submit";
    disapproveButton.className = "btn";
    disapproveButton.id = "disapprove-submit-button";
    disapproveButton.name = "action";
    disapproveButton.value = "Add";
    
    let cancelButton = document.createElement("button");
    cancelButton.innerHTML = "Cancel";
    cancelButton.type = "reset";
    cancelButton.className = "btn";
    cancelButton.id = "cancel-button";

    // Add "Submit" and "Cancel" button to the DOM
    containerLeft.appendChild(disapproveButton);
    containerRight.appendChild(approveButton);
    containerRight.appendChild(cancelButton);

    //--------------
//
    inputCollection = new InputGroupCollection();
//
    inputCollection.add(taskIDFromDB);
    inputCollection.add(programIDFromDB);
    inputCollection.add(teamIDFromDB);
    inputCollection.add(userIDFromDB);
    inputCollection.add(firstNameInput);
    inputCollection.add(lastNameInput);
    inputCollection.add(programNameFromDB);
    inputCollection.add(taskCityInput);
    inputCollection.add(startTimeInput);
    inputCollection.add(endTimeInput);

    if (typeof editUser !== "undefined")
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
    taskIDFromDB.setInputText(taskData.taskID);
    programIDFromDB.setInputText(taskData.programID);
    teamIDFromDB.setInputText(taskData.teamID);
    userIDFromDB.setInputText(editUser.userID);
    firstNameInput.setInputText(editUser.firstName);
    lastNameInput.setInputText(editUser.lastName);
    programNameFromDB.setInputText(editUser.programName); // script.JSON name
    taskCityInput.setInputText(taskData.city);
    startTimeInput.setInputText(taskData.startTime);
    endTimeInput.setInputText(taskData.endTime);
}