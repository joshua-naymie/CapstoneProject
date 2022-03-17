document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";
const MESSAGE_REQUIRED = "required";
const MESSAGE_INVALID = "invalid";

var inputCollection;

var taskIDFromDB,
        programIDFromDB,
        teamIDFromDB,
        userIDFromDB,
        firstNameInput, // display for all programs
        lastNameInput, // display for all programs
        programNameFromDB, // display for all programs
        taskCityInput, // display for all programs
        startTimeInput, // display for all programs
        endTimeInput, // display for all programs
        mileageInput, // display for food only
        packageIDInput, // display for food only
        packageNameInput, // display for food only
        weightFoodInput, // display for food only
        orgNameInput, // display for food - is org only
        familyCountInput, // display for food - is community only
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
            mileageInput = new InputGroup(CSS_INPUTGROUP_MAIN, "food_mileage"),
            packageIDInput = new InputGroup(CSS_INPUTGROUP_MAIN, "food_package_id"),
            packageNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "food_package_name"),
            weightFoodInput = new InputGroup(CSS_INPUTGROUP_MAIN, "food_weight"),
            orgNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "org_name"),
            familyCountInput = new InputGroup(CSS_INPUTGROUP_MAIN, "family_count"),
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

    mileageInput.setLabelText("Mileage:"); // FOOD PROGRAM ONLY
    mileageInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    mileageInput.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    mileageInput.setPlaceHolderText("100");
    containerLeft.appendChild(mileageInput.container);

    packageIDInput.setLabelText("Package ID:"); // FOOD PROGRAM ONLY
    packageIDInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    packageIDInput.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    packageIDInput.setPlaceHolderText("123");
    containerRight.appendChild(packageIDInput.container);

    packageNameInput.setLabelText("Package Name:"); // FOOD PROGRAM ONLY
    packageNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    packageNameInput.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    packageNameInput.setPlaceHolderText("Food");
    containerLeft.appendChild(packageNameInput.container);

    weightFoodInput.setLabelText("Weight (lbs):"); // FOOD PROGRAM ONLY
    weightFoodInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    weightFoodInput.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    weightFoodInput.setPlaceHolderText("100");
    containerRight.appendChild(weightFoodInput.container);

    orgNameInput.setLabelText("Organization Name:"); // FOOD PROGRAM ONLY
    orgNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    orgNameInput.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    orgNameInput.setPlaceHolderText("ECSSEN");
    containerLeft.appendChild(orgNameInput.container);

    familyCountInput.setLabelText("Family Count:"); // FOOD PROGRAM ONLY
    familyCountInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    familyCountInput.addValidator(REGEX_LETTERS, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    familyCountInput.setPlaceHolderText("10");
    containerRight.appendChild(familyCountInput.container);

    approvalNotesFromDB.setLabelText("Comments:");
    approvalNotesFromDB = document.createElement("TEXTAREA");
    var comments = document.createTextNode("Additional Notes");
    approvalNotesFromDB.appendChild(comments);
    container.appendChild(approvalNotesFromDB);

    // Create buttons for the form
    let approveButton = document.createElement("button"); // after clicking this, add the task to the approved tasks list in DB
    approveButton.innerHTML = "Approve";
    approveButton.type = "submit";
    approveButton.className = "btn";
    approveButton.id = "approve-submit-button";
    approveButton.name = "action";
    approveButton.value = "Add";

    let disapproveButton = document.createElement("button"); // after clicking this, add the task to the disapproved tasks list in DB
    disapproveButton.innerHTML = "Disapprove";
    disapproveButton.type = "submit";
    disapproveButton.className = "btn";
    disapproveButton.id = "disapprove-submit-button";
    disapproveButton.name = "action";
    disapproveButton.value = "Add";

    let cancelButton = document.createElement("button"); // after clicking this, send the user back to the previous page (tasks list)
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
    inputCollection.add(mileageInput);
    inputCollection.add(packageIDInput);
    inputCollection.add(packageNameInput);
    inputCollection.add(weightFoodInput);
    inputCollection.add(orgNameInput);
    inputCollection.add(familyCountInput);

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
    mileageInput.setInputText(taskData.xxxxx);
    packageIDInput.setInputText(taskData.xxxxx);
    packageNameInput.setInputText(taskData.xxxxx);
    weightFoodInput.setInputText(taskData.xxxxx);
    orgNameInput.setInputText(taskData.xxxxx);
    familyCountInput.setInputText(taskData.xxxxx);
}

function checkTask(that) // how do I create an onChange that passes in the value to that? onchange="checkTask(this);
{
      if (that.value == 1) { // if program_Id == 1 show food inputs
        if (familyCountInput == null) { // task falls under an organization so display org name but not family count;
            document.getElementById("food_mileage").style.display = "block";
            document.getElementById("food_package_id").style.display = "block";
            document.getElementById("food_package_name").style.display = "block";
            document.getElementById("food_weight").style.display = "block";
            document.getElementById("org_name").style.display = "block";
            document.getElementById("family_count").style.display = "none";
        } else { // task falls under a community so display family count but not org name;
            document.getElementById("food_mileage").style.display = "block";
            document.getElementById("food_package_id").style.display = "block";
            document.getElementById("food_package_name").style.display = "block";
            document.getElementById("food_weight").style.display = "block";
            document.getElementById("org_name").style.display = "none";
            document.getElementById("family_count").style.display = "block";
        }
    } else if (that.value == 2) { // if program_Id == 2 show hotline but hide food inputs
        document.getElementById("food_mileage").style.display = "none";
        document.getElementById("food_package_id").style.display = "none";
        document.getElementById("food_package_name").style.display = "none";
        document.getElementById("food_weight").style.display = "none";
        document.getElementById("org_name").style.display = "none";
        document.getElementById("family_count").style.display = "none";
    } else {
        console.log("problem in checktask function");
    }
}