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

    //taskIDFromDB.setLabelText("Task ID:");
    const labelZero = document.createElement('p');
    labelZero.setAttribute('id','label-zero');
    containerLeft.appendChild(labelZero);
    document.getElementsByTagName("p")[0].innerHTML="Task ID:";
    taskIDFromDB.setPlaceHolderText("Placeholder Text");
    containerLeft.appendChild(taskIDFromDB.container);
    document.getElementById("task_id_db").setAttribute("readonly", true);
    document.getElementById("task_id_db").style.display = "none";
    document.getElementById("label-zero").style.display = "none";

    const labelOne = document.createElement('p');
    labelOne.setAttribute('id','label-one');
    containerRight.appendChild(labelOne);
    document.getElementsByTagName("p")[1].innerHTML="Program ID:";
    //programIDFromDB.setLabelText("Program ID:");
    programIDFromDB.setPlaceHolderText("Placeholder Text");
    containerRight.appendChild(programIDFromDB.container);
    document.getElementById("program_id_db").setAttribute("readonly", true);
    document.getElementById("program_id_db").style.display = "none";
    document.getElementById("label-one").style.display = "none";

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
//    const labelTwo = document.createElement('p');
//    labelOne.setAttribute('id','label-two');
//    containerLeft.appendChild(labelTwo);
//    document.getElementsByTagName("p")[2].innerHTML="First Name:";
    firstNameInput.setPlaceHolderText("John");
    containerLeft.appendChild(firstNameInput.container);
    document.getElementById("user_firstname").setAttribute("readonly", true);
    // document.getElementById("label-two").style.display = "block";

//const labelThree = document.createElement('p');
//    labelThree.setAttribute('id','label-three');
//    containerRight.appendChild(labelThree);
//    document.getElementsByTagName("p")[3].innerHTML="Last Name:";
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
    startTimeInput.input.type = "datetime-local";
    containerLeft.appendChild(startTimeInput.container);
    document.getElementById("start_time").setAttribute("readonly", true);

    endTimeInput.setLabelText("End Time:");
    endTimeInput.input.type = "datetime-local";
    endTimeInput.input.value = new Date().toISOString().substring(0, 10);
    containerRight.appendChild(endTimeInput.container);
    document.getElementById("end_time").setAttribute("readonly", true);
    
    mileageInput.setLabelText("Mileage:"); // FOOD PROGRAM ONLY
    mileageInput.setPlaceHolderText("100");
    containerLeft.appendChild(mileageInput.container);
    document.getElementById("food_mileage").setAttribute("readonly", true);

    packageIDInput.setLabelText("Package ID:"); // FOOD PROGRAM ONLY
    packageIDInput.setPlaceHolderText("123");
    containerRight.appendChild(packageIDInput.container);
    document.getElementById("food_package_id").setAttribute("readonly", true);

    packageNameInput.setLabelText("Package Name:"); // FOOD PROGRAM ONLY
    packageNameInput.setPlaceHolderText("Food");
    containerLeft.appendChild(packageNameInput.container);
    document.getElementById("food_package_name").setAttribute("readonly", true);

    weightFoodInput.setLabelText("Weight (lbs):"); // FOOD PROGRAM ONLY
    weightFoodInput.setPlaceHolderText("100");
    containerRight.appendChild(weightFoodInput.container);
    document.getElementById("food_weight").setAttribute("readonly", true);
    
    orgNameInput.setLabelText("Organization Name:"); // FOOD PROGRAM ONLY
    orgNameInput.setPlaceHolderText("ECSSEN");
    containerLeft.appendChild(orgNameInput.container);
    document.getElementById("org_name").setAttribute("readonly", true);

    familyCountInput.setLabelText("Family Count:"); // FOOD PROGRAM ONLY
    familyCountInput.setPlaceHolderText("10");
    containerRight.appendChild(familyCountInput.container);
    document.getElementById("family_count").setAttribute("readonly", true);

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
    cancelButton.type = "reset";
    cancelButton.className = "btn";
    cancelButton.id = "cancel-button";

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
    //mileageInput.setInputText(taskData.xxxxx);
   //packageIDInput.setInputText(taskData.xxxxx);
    //packageNameInput.setInputText(taskData.xxxxx);
    //weightFoodInput.setInputText(taskData.xxxxx);
    //orgNameInput.setInputText(taskData.xxxxx);
    familyCountInput.setInputText(taskData.familyCount);
}


function checkTask(that) // how do I create an onChange that passes in the value to that? onchange="checkTask(this);
{
      if (that == 1) { // if program_Id == 1 show food inputs
        if (taskData.familyCount === false) { // task falls under an organization so display org name but not family count;
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
    } else if (that == 2) { // if program_Id == 2 show hotline but hide food inputs
        console.log("programID:" + that);
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