document.addEventListener("DOMContentLoaded", load, false);

const CSS_INPUTGROUP_MAIN = "main-input";
const MESSAGE_REQUIRED = "required";
const MESSAGE_INVALID = "invalid";

var inputCollection;

var userID,
        firstNameInput,
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
        activeInput,
        teamInput,
        roleInput;

function load() {
    let containerLeft = document.getElementById("container-left"),
            containerRight = document.getElementById("container-right");

    (userID = new InputGroup(CSS_INPUTGROUP_MAIN, "id")),
            (firstNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_firstname")),
            (lastNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_lastname")),
            (emailInput = new InputGroup(CSS_INPUTGROUP_MAIN, "userEmail")),
            (phoneInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_phone")),
            (birthdayInput = new InputGroup(CSS_INPUTGROUP_MAIN, "birthday")),
            (streetInput = new InputGroup(CSS_INPUTGROUP_MAIN, "street")),
            (cityInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_city")),
            (postalCodeInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_postalcode")),
            (passwordInput = new InputGroup(CSS_INPUTGROUP_MAIN, "user_password")),
            (signupDateInput = new InputGroup(CSS_INPUTGROUP_MAIN, "signupdate")),
            (adminInput = new InputGroup(CSS_INPUTGROUP_MAIN, "admin")),
            (activeInput = new InputGroup(CSS_INPUTGROUP_MAIN, "active")),
            (teamInput = new InputGroup(CSS_INPUTGROUP_MAIN, "team"));
//    (roleInput = new InputGroup(CSS_INPUTGROUP_MAIN, "role"));
    roleInput = document.createElement("select");
    roleInput.name= "roleID";

    userID.setLabelText("User ID:");
    userID.addValidator(
            REGEX_NOT_EMPTY,
            INPUTGROUP_STATE_ERROR,
            MESSAGE_REQUIRED
            );
    userID.addValidator(
            REGEX_LETTERS,
            INPUTGROUP_STATE_WARNING,
            MESSAGE_INVALID
            );
    //    firstNameInput.setEnterFunction(phoneInput);
    userID.setPlaceHolderText("#");
    containerLeft.appendChild(userID.container);

    signupDateInput.setLabelText("Signup Date:");
    // signupDateInput.input.type = "date";
    signupDateInput.input.value = new Date().toISOString().substring(0, 10);
    //    signupDateInput.input.disabled = "true";
    signupDateInput.addValidator(
            REGEX_NOT_EMPTY,
            INPUTGROUP_STATE_ERROR,
            MESSAGE_REQUIRED
            );
    //    cityInput.setEnterFunction(phoneInput);
    containerRight.appendChild(signupDateInput.container);
    console.log(signupDateInput.input.value);
    document.getElementById("signupdate").setAttribute("readonly", true);

    firstNameInput.setLabelText("First Name:");
    firstNameInput.addValidator(
            REGEX_NOT_EMPTY,
            INPUTGROUP_STATE_ERROR,
            MESSAGE_REQUIRED
            );
    firstNameInput.addValidator(
            REGEX_LETTERS,
            INPUTGROUP_STATE_WARNING,
            MESSAGE_INVALID
            );
    //    firstNameInput.setEnterFunction(phoneInput);
    firstNameInput.setPlaceHolderText("John");
    containerLeft.appendChild(firstNameInput.container);

    lastNameInput.setLabelText("Last Name:");
    lastNameInput.addValidator(
            REGEX_NOT_EMPTY,
            INPUTGROUP_STATE_ERROR,
            MESSAGE_REQUIRED
            );
    lastNameInput.addValidator(
            REGEX_LETTERS,
            INPUTGROUP_STATE_WARNING,
            MESSAGE_INVALID
            );
    //    lastNameInput.setEnterFunction(phoneInput);
    lastNameInput.setPlaceHolderText("Smith");
    containerRight.appendChild(lastNameInput.container);

    emailInput.setLabelText("Email:");
    emailInput.addValidator(
            REGEX_NOT_EMPTY,
            INPUTGROUP_STATE_ERROR,
            MESSAGE_REQUIRED
            );
    emailInput.addValidator(
            REGEX_EMAIL,
            INPUTGROUP_STATE_WARNING,
            MESSAGE_INVALID
            );
    //    emailInput.setEnterFunction(phoneInput);
    emailInput.setPlaceHolderText("jsmith@aol.com");
    containerLeft.appendChild(emailInput.container);

    phoneInput.setLabelText("Phone:");
    phoneInput.addValidator(
            REGEX_NOT_EMPTY,
            INPUTGROUP_STATE_ERROR,
            MESSAGE_REQUIRED
            );
    phoneInput.addValidator(
            REGEX_PHONE,
            INPUTGROUP_STATE_WARNING,
            MESSAGE_INVALID
            );
    //    phoneInput.setEnterFunction(phoneInput);
    phoneInput.setPlaceHolderText("555-555-5555");
    containerRight.appendChild(phoneInput.container);

    birthdayInput.setLabelText("Birthday:");
    birthdayInput.input.type = "date";
    birthdayInput.addValidator(
            REGEX_NOT_EMPTY,
            INPUTGROUP_STATE_ERROR,
            MESSAGE_REQUIRED
            );
    birthdayInput.addValidator(
            REGEX_EMAIL,
            INPUTGROUP_STATE_WARNING,
            MESSAGE_INVALID
            );
    //    birthdayInput.setEnterFunction(phoneInput);
    containerLeft.appendChild(birthdayInput.container);

    streetInput.setLabelText("Street:");
    streetInput.addValidator(
            REGEX_NOT_EMPTY,
            INPUTGROUP_STATE_ERROR,
            MESSAGE_REQUIRED
            );
    //    streetInput.setEnterFunction(phoneInput);
    streetInput.setPlaceHolderText("112 Edgedale Dr. NW");
    containerRight.appendChild(streetInput.container);

    cityInput.setLabelText("City:");
    cityInput.addValidator(
            REGEX_NOT_EMPTY,
            INPUTGROUP_STATE_ERROR,
            MESSAGE_REQUIRED
            );
    //    cityInput.setEnterFunction(phoneInput);
    containerLeft.appendChild(cityInput.container);

    postalCodeInput.setLabelText("Postal Code:");
    postalCodeInput.addValidator(
            REGEX_NOT_EMPTY,
            INPUTGROUP_STATE_ERROR,
            MESSAGE_REQUIRED
            );
    postalCodeInput.addValidator(
            REGEX_POSTAL_CODE,
            INPUTGROUP_STATE_WARNING,
            MESSAGE_INVALID
            );
    //    postalCodeInput.setEnterFunction(phoneInput);
    postalCodeInput.setPlaceHolderText("A0A0A0");
    containerRight.appendChild(postalCodeInput.container);

    //    passwordInput.setLabelText("Password:");
    //    passwordInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    ////    cityInput.setEnterFunction(phoneInput);
    //    containerLeft.appendChild(passwordInput.container);

    // team input
    teamInput.setLabelText("Team:");
    teamInput.input.type = "text";
    containerLeft.appendChild(teamInput.container);
    let additionalInfo = document.createElement("div");
    additionalInfo.id = "additionalInfo";
    containerLeft.appendChild(additionalInfo);
    $("#team").on("input", () => searchTeam());

    // role input
//    roleInput.setLabelText("Role:");
    // roleInput.input.type = "select";
    
    // roleID --> name of dropdown
    var roleDiv = document.createElement("Div");
    roleDiv.id = "role-label";
    roleDiv.innerHTML = "Role:";
    containerRight.appendChild(roleDiv);
    //let roleDivBox = document.createElement("div");
   // roleDivBox.id = "roleID";
    roleInput.id = "role";
    containerRight.appendChild(roleInput);
    //containerRight.appendChild(roleDivBox);
    //$('<div>hello</div>').appendTo('#role');
    $('#role').append('<option value="1">Admin</option>');
    $('#role').append('<option value="2">Volunteer</option>');
    $('#role').append('<option value="3">Manager</option>');
    $('#role').append('<option value="4">Coordinator</option>');
    $('#role').append('<option value="5">Supervisor</option>');

    // 
//    adminInput.setLabelText("Admin:");
//    adminInput.input.type = "checkbox";
    //    postalCodeInput.setEnterFunction(phoneInput);
//    containerLeft.appendChild(adminInput.container);

    activeInput.setLabelText("Active:");
    activeInput.input.type = "checkbox";
    //    postalCodeInput.setEnterFunction(phoneInput);
    containerRight.appendChild(activeInput.container);

    // containerLeft.appendChild(moreInfo);
//    $("#role").on("input", () => onRoleSelection());

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
    cancelButton.type = "submit";
    cancelButton.className = "btn";
    cancelButton.id = "cancel-button";
    cancelButton.name = "action";
    cancelButton.value = "Cancel";

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

    if (typeof editUser !== "undefined") {
        document.getElementById("header").innerText = "Edit User";
        populateFields();
        submitButton.name = "action";
        submitButton.value = "Save";
    }
}

function validateUserInfo() {
    document.getElementById("body").style.backgroundColor =
            inputCollection.validateAll() ? "green" : "red";
}

function populateFields() {
    userID.setInputText(editUser.id);
    firstNameInput.setInputText(editUser.firstName);
    lastNameInput.setInputText(editUser.lastName);
    emailInput.setInputText(editUser.email);
    phoneInput.setInputText(editUser.phoneNum);
    birthdayInput.setInputText(editUser.DOB);
    streetInput.setInputText(editUser.address);
    cityInput.setInputText(editUser.city);
    postalCodeInput.setInputText(editUser.postalCode);
    passwordInput.setInputText(editUser.password);
    signupDateInput.setInputText(editUser.regDate);

    adminInput.input.checked = editUser.isAdmin;
    activeInput.input.checked = editUser.isActive;
    
    document.getElementById('role').selectedIndex = editUser.roleId - 1;

//    teamInput.input.checked = editUser.teamInput;
    teamInput.setInputText(editUser.teamInput);
    searchTeam();
    // roleInput.input.checked = editUser.roleInput;
}

function searchTeam() {
    $("#additionalInfo").append("<div class='form-group' id='formGroup'></div>");
    $("#formGroup").empty();
    $("#formGroup").append("<label class='form-label'>Select a team:</label>");
    $("#formGroup").append(
            "<select name='teamId' id='individualSelection' class='form-select'></select>"
            );

    let teamName = $("#team").val();
    console.log(teamName);
    $.ajax({
        type: "get",
        url: "data",
        data: {operation: "findTeam", teamName: teamName},
        success: function (data) {
            let obj = JSON.parse(data);

            obj.forEach((team) => {
                console.log(team);
                $("#individualSelection").append(
                        '<option value="' + team.team_id + '">' + team.team_name + "</option>"
                        );
            });
        },
    });
}

//function onRoleSelection() {
//
//    $('#moreInfo').empty();
//    $('#moreInfo').append("<div class='form-group' id='formGroup'></div>");
//    $('#formGroup').append("<label class='form-label'>Role:</label>");
//    $('#formGroup').append("<select name='role' id='roleSelection' class='form-select'></select>");
//
//    let roleName = $("#role").val();
//    console.log(roleName);
//    $.ajax({
//        type: "get",
//        url: "data",
//        data: {operation: "findRole", roleName: roleName},
//        success: function (data) {
//            let obj = JSON.parse(data);
//
//            obj.forEach((role) => {
//                console.log(role);
//                $("#individualSelection").append(
//                        '<option value="' + role.role_id + '">' + role.role_name + "</option>"
//                        );
//            });
//        },
//    });
////    var actionInput = document.forms['reportForm']['action'];
////    actionInput.value = 'foodProgramCityReport';
//}
