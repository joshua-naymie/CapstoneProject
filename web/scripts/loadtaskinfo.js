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
        hoursWorkedInput;

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
            approvalNotesFromDB = new InputGroup(CSS_INPUTGROUP_MAIN, "approval_notes_db"),
            hoursWorkedInput = new InputGroup(CSS_INPUTGROUP_MAIN, "hours_worked");

    //taskIDFromDB.setLabelText("Task ID:");
    var divTaskID = document.createElement("Div");
    divTaskID.id = "task-id-label";
    divTaskID.innerHTML = "Task ID:";
    containerLeft.appendChild(divTaskID);
    taskIDFromDB.setPlaceHolderText("Placeholder Text");
    containerLeft.appendChild(taskIDFromDB.container);
    document.getElementById("task_id_db").setAttribute("readonly", true);
    document.getElementById("task_id_db").style.display = "none";
    document.getElementById("task-id-label").style.display = "none";

    var divProgramID = document.createElement("Div");
    divProgramID.id = "program-id-label";
    divProgramID.innerHTML = "Program ID:";
    containerRight.appendChild(divProgramID);
    //programIDFromDB.setLabelText("Program ID:");
    programIDFromDB.setPlaceHolderText("Placeholder Text");
    containerRight.appendChild(programIDFromDB.container);
    document.getElementById("program_id_db").setAttribute("readonly", true);
    document.getElementById("program_id_db").style.display = "none";
    document.getElementById("program-id-label").style.display = "none";

//    teamIDFromDB.setLabelText("Team ID:");
//    teamIDFromDB.setPlaceHolderText("n");
//    containerLeft.appendChild(teamIDFromDB.container);
//    document.getElementById("team_id_db").setAttribute("readonly", true);
//
//    userIDFromDB.setLabelText("User ID:");
//    userIDFromDB.setPlaceHolderText("n");
//    containerRight.appendChild(userIDFromDB.container);
//    document.getElementById("user_id_db").setAttribute("readonly", true);

    firstNameInput.setLabelText("First Name:");
    firstNameInput.setPlaceHolderText("John");
    containerLeft.appendChild(firstNameInput.container);
    document.getElementById("user_firstname").setAttribute("readonly", true);
    
    lastNameInput.setLabelText("Last Name:");
    lastNameInput.setPlaceHolderText("Smith");
    containerRight.appendChild(lastNameInput.container);
    document.getElementById("user_lastname").setAttribute("readonly", true);

    programNameFromDB.setLabelText("Program Name:");
    programNameFromDB.setPlaceHolderText("Hotline");
    containerLeft.appendChild(programNameFromDB.container);
    document.getElementById("program_name_db").setAttribute("readonly", true);

    taskCityInput.setLabelText("City:");
    taskCityInput.setPlaceHolderText("Calgary");
    containerRight.appendChild(taskCityInput.container);
    document.getElementById("user_city").setAttribute("readonly", true);

    startTimeInput.setLabelText("Start Time:");
    //startTimeInput.input.type = "datetime-local";
    containerLeft.appendChild(startTimeInput.container);
    document.getElementById("start_time").setAttribute("readonly", true);

    endTimeInput.setLabelText("End Time:");
   // endTimeInput.input.type = "datetime-local";
    //endTimeInput.input.value = new Date().toISOString().substring(0, 10);
    endTimeInput.input.value = JSON.stringify(new Date().toISOString());
    containerRight.appendChild(endTimeInput.container);
    document.getElementById("end_time").setAttribute("readonly", true);

    var divMileage = document.createElement("Div");
    divMileage.id = "mileage-label";
    divMileage.innerHTML = "Mileage:";
    containerLeft.appendChild(divMileage);
    //mileageInput.setLabelText("Mileage:"); // FOOD PROGRAM ONLY
    mileageInput.setPlaceHolderText("100");
    containerLeft.appendChild(mileageInput.container);
    document.getElementById("food_mileage").setAttribute("readonly", true);

    var divWeightLabel = document.createElement("Div");
    divWeightLabel.id = "weight-label";
    divWeightLabel.innerHTML = "Quantity:";
    containerRight.appendChild(divWeightLabel);
//  weightFoodInput.setLabelText("Weight (lbs):"); // FOOD PROGRAM ONLY
    weightFoodInput.setPlaceHolderText("100");
    containerRight.appendChild(weightFoodInput.container);
    document.getElementById("food_weight").setAttribute("readonly", true);
    
    var divHoursWorkedLabel = document.createElement("Div");
    divHoursWorkedLabel.id = "hoursWorked-label";
    divHoursWorkedLabel.innerHTML = "Hours Worked:";
    containerRight.appendChild(divHoursWorkedLabel);
//  weightFoodInput.setLabelText("Weight (lbs):"); // FOOD PROGRAM ONLY
    containerRight.appendChild(hoursWorkedInput.container);
    document.getElementById("hours_worked").setAttribute("readonly", true);

    var divFamilyLabel = document.createElement("Div");
    divFamilyLabel.id = "family-label";
    divFamilyLabel.innerHTML = "Family Count:";
    containerRight.appendChild(divFamilyLabel);
    // familyCountInput.setLabelText("Family Count:"); // FOOD PROGRAM ONLY
    familyCountInput.setPlaceHolderText("10");
    containerRight.appendChild(familyCountInput.container);
    document.getElementById("family_count").setAttribute("readonly", true);

    var divPackageID = document.createElement("Div");
    divPackageID.id = "package-id-label";
    divPackageID.innerHTML = "Package ID:";
    containerRight.appendChild(divPackageID);
    // packageIDInput.setLabelText("Package ID:"); // FOOD PROGRAM ONLY
    packageIDInput.setPlaceHolderText("123");
    containerRight.appendChild(packageIDInput.container);
    document.getElementById("food_package_id").setAttribute("readonly", true);
    document.getElementById("food_package_id").style.display = "none";
    document.getElementById("package-id-label").style.display = "none";

    var divPackageName = document.createElement("Div");
    divPackageName.id = "package-name-label";
    divPackageName.innerHTML = "Package Name:";
    containerLeft.appendChild(divPackageName);
    // packageNameInput.setLabelText("Package Name:"); // FOOD PROGRAM ONLY
    packageNameInput.setPlaceHolderText("Food");
    containerLeft.appendChild(packageNameInput.container);
    document.getElementById("food_package_name").setAttribute("readonly", true);

    var divOrganizationName = document.createElement("Div");
    divOrganizationName.id = "organization-name-label";
    divOrganizationName.innerHTML = "Organization Name:";
    containerLeft.appendChild(divOrganizationName);
    // orgNameInput.setLabelText("Organization Name:"); // FOOD PROGRAM ONLY
    orgNameInput.setPlaceHolderText("ECSSEN");
    containerLeft.appendChild(orgNameInput.container);
    document.getElementById("org_name").setAttribute("readonly", true);

    approvalNotesFromDB.setLabelText("Comments:");
    approvalNotesFromDB = document.createElement("TEXTAREA");
    var comments = document.createTextNode("Additional Notes");
    approvalNotesFromDB.appendChild(comments);
    containerLeft.appendChild(approvalNotesFromDB);

    // Create buttons for the form
    let approveButton = document.createElement("button"); // after clicking this, add the task to the approved tasks list in DB
    approveButton.innerHTML = "Approve";
    approveButton.type = "submit";
    approveButton.className = "btn";
    approveButton.id = "approve-submit-button";
    approveButton.name = "action";
    approveButton.value = "Approve";

    let disapproveButton = document.createElement("button"); // after clicking this, add the task to the disapproved tasks list in DB
    disapproveButton.innerHTML = "Disapprove";
    disapproveButton.type = "submit";
    disapproveButton.className = "btn";
    disapproveButton.id = "disapprove-submit-button";
    disapproveButton.name = "action";
    disapproveButton.value = "Disapprove";

    let cancelButton = document.createElement("button"); // after clicking this, send the user back to the previous page (tasks list)
    cancelButton.innerHTML = "Cancel";
    cancelButton.type = "submit";
    cancelButton.className = "btn";
    cancelButton.id = "cancel-button";
    cancelButton.name = "action";
    cancelButton.value = "Cancel";

    // Add "Submit" and "Cancel" button to the DOM
    containerLeft.appendChild(disapproveButton);
    containerRight.appendChild(cancelButton);
    containerRight.appendChild(approveButton);

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
    inputCollection.add(hoursWorkedInput);

    if (typeof taskData !== "undefined")
    {
        var that = taskData.programID;
        populateFields();
        checkTask(that);
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
    userIDFromDB.setInputText(taskData.userID);
    firstNameInput.setInputText(taskData.firstName);
    lastNameInput.setInputText(taskData.lastName);
    programNameFromDB.setInputText(taskData.programName); // script.JSON name
    taskCityInput.setInputText(taskData.city);
    startTimeInput.setInputText(taskData.startTime);
    endTimeInput.setInputText(taskData.endTime);
    mileageInput.setInputText(taskData.mileage);
    //packageIDInput.setInputText(taskData.xxxxx);
    packageNameInput.setInputText(taskData.packageType);
    weightFoodInput.setInputText(taskData.foodAmount);
    orgNameInput.setInputText(taskData.organizationName);
    familyCountInput.setInputText(taskData.familyCount);
    hoursWorkedInput.setInputText(taskData.hoursWorked);
}


function checkTask(that) // how do I create an onChange that passes in the value to that? onchange="checkTask(this);
{
    if (that == 1) { // if program_Id == 1 show food inputs
        if (!taskData.familyCount) { // task falls under an organization so display org name but not family count;
            document.getElementById("food_mileage").style.display = "block";
            //   document.getElementById("food_package_id").style.display = "block";
            document.getElementById("food_package_name").style.display = "block";
            document.getElementById("food_weight").style.display = "block";
            document.getElementById("org_name").style.display = "block";
            document.getElementById("family_count").style.display = "none";
            document.getElementById("family-label").style.display = "none";
        } else { // task falls under a community so display family count but not org name;
            document.getElementById("food_mileage").style.display = "block";
            //  document.getElementById("food_package_id").style.display = "block";
            document.getElementById("food_package_name").style.display = "block";
            document.getElementById("food_weight").style.display = "block";
            document.getElementById("org_name").style.display = "none";
            document.getElementById("family_count").style.display = "block";
            document.getElementById("organization-name-label").style.display = "none";
        }
    } else if (that == 2) { // if program_Id == 2 show hotline but hide food inputs
        console.log("programID:" + that);
        document.getElementById("food_mileage").style.display = "none";
        //   document.getElementById("food_package_id").style.display = "none";
        document.getElementById("food_package_name").style.display = "none";
        document.getElementById("food_weight").style.display = "none";
        document.getElementById("org_name").style.display = "none";
        document.getElementById("family_count").style.display = "none";
        document.getElementById("mileage-label").style.display = "none";
        document.getElementById("package-name-label").style.display = "none";
        document.getElementById("weight-label").style.display = "none";
        document.getElementById("family-label").style.display = "none";
        document.getElementById("organization-name-label").style.display = "none";

    } else {
        console.log("problem in checktask function");
    }
}