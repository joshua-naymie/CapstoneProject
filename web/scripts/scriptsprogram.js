document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";

/**
 * @type Array  The current, filtered list of programs
 */
var currentListData;
var currentUserData;
var currentManager;

/**
 * @type String  The action the server will take on POST
 */
var currentAction = "none";
var currentWidthClass = "container--list-size";

var listArea;
var inputArea

var programSearchInput;
var filterCheckbox;

var inputForm;
var submitButton;
var actionInput;
var inputHeader;

var inputs;
var userList;
var programList;
var programNameInput,
    managerNameDisplay,
    statusInput;

// const generateUserCell = (user) => {
//     let cell = document.createElement("div");
//     cell.classList.add("user__cell");
//     cell.addEventListener("click", () => {setManager(user)});
    
//     let name = document.createElement("p");
//     name.classList.add("user__cell-content__name");
//     name.innerText = user.name;
    
//     let email = document.createElement("p");
//     email.classList.add("user__cell-content__email");
//     email.innerText = user.email;
    
//     cell.appendChild(name);
//     cell.appendChild(email);
    
//     return cell;
// }

var userSearchTimer;

/**
 * Filters the user list after no input for 0.3s.
 * Restarts timer if already running.
 * @param {string} searchValue The value to search for in the user list
 */
const userSearchInputTimer = (searchValue) => {
    if(userSearchTimer !== null)
    {
        clearTimeout(userSearchTimer);
    }
    userSearchTimer = setTimeout(() => { searchUsers(searchValue) }, 300);
}

/**
 * Filters a program based on given search value.
 * Checks program and manager name, case-insensitive.
 * @param {type} program The program to filter
 * @param {string} searchValue The value to find in the program or manager name
 * @returns Whether the search value is contained in the program or manager name
 */
const filterProgram = (program, searchValue) => {
    if (program.name.toLowerCase().includes(searchValue)
    ||((program.managerId != null) && userData[program.managerId].name.toLowerCase().includes(searchValue)))
    {
        return filterCheckbox.checked ? true : program.isActive;
    }

    return false;
}

/**
 * Filters a user based on given search value.
 * Checks user's name and email, case-insensitive.
 * @param {type} user The user to filter
 * @param {string} searchValue The value to find in the user's name or email
 * @returns Whether the search value is contained in the program or manager name
 */
const filterUser = (user, searchValue) => {
    if(user.name.toLowerCase().includes(searchValue.toLowerCase())
    || user.email.toLowerCase().includes(searchValue.toLowerCase()))
    {
        return true;
    }

    return false;
}

/**
 * Run when DOM loads
 */
function load()
{
    // setup 'Show Inactive' checkbox
    filterCheckbox = document.getElementById("program-filter");
    filterCheckbox.checked = false;
    filterCheckbox.addEventListener("change", () => { searchProgramList(programSearchInput.value); });

    programList = new AutoList("grid");
    programList.container = document.getElementById("list-base");
    programList.setFilterMethod(filterProgram);
    programList.setSortMethod(compareProgram);
    generateProgramList();

    userList = new AutoList("flex");
    userList.container = document.getElementById("user-list");
    userList.setFilterMethod(filterUser);
    userList.setSortMethod(compareUser);
    generateUserTable();
    
    // setup input area
    inputArea = document.getElementById("input-area");
    inputArea.style.display = "none";
    
    // setup list area
    listArea = document.getElementById("list-area");
    listArea.classList.add("visible");
    
    // setup input form
    inputForm = document.getElementById("addProgramForm");
    inputForm.reset();
    
    statusInput = document.getElementById("status");
    statusInput.addEventListener("change", setStatusSelectColor);
    setStatusSelectColor();
    
    inputHeader = document.getElementById("input-panel__header");
    
    // setup form buttons
    submitButton = document.getElementById("ok__button");
    document.getElementById("cancel__button").addEventListener("click", cancelPressed);
    submitButton.addEventListener("click", () => { submitForm(currentAction) });
    
    // setup list search input
    programSearchInput = document.getElementById("search-input");
    programSearchInput.value = "";
    programSearchInput.addEventListener("input", () => { searchProgramList(programSearchInput.value) });

    // setup store name InputGroup
    programNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "program-name");
    programNameInput.setLabelText("Program Name");
    programNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    programNameInput.setPlaceHolderText("eg. Hotline");
    programNameInput.container = document.getElementById("program-name__input");
    configCustomInput(programNameInput);

    // setup manager name InputGroup
    managerNameDisplay = new InputGroup(CSS_INPUTGROUP_MAIN, "manager-name");
    managerNameDisplay.setLabelText("Manager Name");
    managerNameDisplay.setPlaceHolderText("No manager");
    managerNameDisplay.input.setAttribute("autocomplete", "off");
    managerNameDisplay.input.setAttribute("disabled", "disabled");
    managerNameDisplay.container = document.getElementById("manager-name__display");
    configCustomInput(managerNameDisplay);
    
    let userSearchInput = document.getElementById("user-search");
    userSearchInput.addEventListener("input", () => {userSearchInputTimer(userSearchInput.value)});;

    // searchUsers("");

    // add InputGroups to a collection
    inputs = new InputGroupCollection();
    inputs.add(programNameInput);
    inputs.add(managerNameDisplay);

 
}

/**
 * Creates a custom layout for an InputGroup. Adds input first then label, message.
 * @param {InputGroup} group The InputGroup to customize
 */
function configCustomInput(group)
{
    if(!group instanceof InputGroup)
    {
        throw `not an InputGroup - ${group}`;
    }
    group.container.classList.add("program__custom-input");
    group.container.appendChild(group.input);

    let labelMessageDiv = document.createElement("div");
    labelMessageDiv.classList.add("label-message__container");
    labelMessageDiv.appendChild(group.label);
    labelMessageDiv.appendChild(group.message);

    group.container.appendChild(labelMessageDiv);
}

/**
 * Generates the list of programs displayed to the user.
 * Creates program cards and inserts line breaks between them.
 */
function generateProgramList()
{
    removeAllChildren(document.getElementById("list-base"));

    // let keys = Object.keys(programData);
    for(let i=0; i<programData.length; i++)
    {
        let program = programData[i];
        programList.addItem(generateProgramRow(program), program);
    }

    programList.generateList();
}

/**
 * Creates and returns a row in a program list
 * @param {program} program  The program whose info will populate the row
 * @returns {Element}  A div representing a row in a program list.
 */
function generateProgramRow(program)
{
    // item card
    let item = document.createElement("div");
    item.classList.add("list-item");
    item.addEventListener("click", () => { editProgram(program); });

    let managerDiv = document.createElement("div");
    managerDiv.classList.add("manager-area");

    let managerName = document.createElement("p");
    managerName.classList.add("manager-name");
    managerName.innerText = program.managerId == null ? "" : userData[program.managerId].name;
    
    managerDiv.appendChild(managerName);

    let programDiv = document.createElement("div");
    programDiv.classList.add("program-area");

    let programName = document.createElement("p");
    programName.classList.add("program-name");
    programName.innerText = program.name;

    programDiv.appendChild(programName);

    item.appendChild(programDiv);
    item.appendChild(managerDiv);

    return item;
}

/**
 * Filters the list by matching program name or manager name.
 * Regenerates the list with filtered programs
 */
function searchProgramList(searchValue)
{
    searchValue = searchValue == null ? "" : searchValue;
    programList.filter(searchValue);
}

/**
 * Called when the cancel button on the input panel is clicked.
 * Sets the currentAction and resets the input form.
 * Fades ui back to list panel.
 */
function cancelPressed()
{
    currentAction = "none";
    
    setContainerWidth("container--list-size");
    fadeOutIn(inputArea, listArea);
    setTimeout(() => {
        document.getElementById("addProgramForm").reset();
        userList.filter("");
        inputs.resetInputs() }, 200);
}

/**
 * Called when the new program button is clicked.
 * Sets the currentAction and the submit button text.
 * Fades ui to input panel.
 */
function addProgram()
{
    currentAction = "add";
    submitButton.value = "Add";
    inputHeader.innerText = "New";
    setStatusSelectColor();
    searchUsers("");
    
    setContainerWidth("container--input-size");
    fadeOutIn(listArea, inputArea);
}

/**
 * Called when an item in the program list is clicked.
 * Sets the currentAction and the submit button text.
 * Fades ui to pre-populated input panel.
 * @param {type} program
 * @returns {undefined}
 */
function editProgram(program)
{
    currentAction = "update";
//    currentUserData.unshift(currentUserData.splice(currentUserData.indexOf(program), 1));
    for(let i=0; i<userData; i++)
    {
        if(userData[i].ID === program.manager)
        {
            // todo: set manager as currentManager
        }
    }
    submitButton.value = "Update";
    inputHeader.innerText = "Edit";

    programNameInput.setInputText(program.name);
    console.log(program.managerId);
    setManager(userData[program.managerId]);
    statusInput.value = program.isActive ? "active" : "inactive";
    setStatusSelectColor();

    document.getElementById("program-ID").value = program.programId;
    searchUsers("");
    
    setContainerWidth("container--input-size");
    fadeOutIn(listArea, inputArea);
}


/**
 * Checks input validation and gets user confirmation.
 * Submits the form with the currentAction.
 */
function submitForm()
{
    if(inputs.validateAll())
    {
        showConfirmationModal(`Are you sure you want to ${currentAction} this program?`, () => {
            managerNameDisplay.input.value = managerNameDisplay.input.value.split(":")[0];
            postAction(currentAction, "addProgramForm", "programs")
        });
    }
}

/**
 * Fades out one element, then fades in another.
 * @param {type} outElement The element to fade out.
 * @param {type} inElement The element to fade in.
 */
function fadeOutIn(outElement, inElement)
{
    outElement.classList.remove("visible");
    
    setTimeout(() => {
        outElement.style.display = "none";
        
        inElement.style.display = "block";
        setTimeout(() => {inElement.classList.add("visible");}, 50);
        
    }, 100)
}

/**
 * Sorting algorithm for program objects.
 * Sorts by program name.
 * @param {type} program1   the first program to compare
 * @param {type} program2   the second program to compare
 * @returns {Number}
 */
function compareProgram(program1, program2)
{
    if(program1.object.name > program2.object.name)
    {
        return 1;
    }
    else if(program1.object.name < program2.object.name)
    {
        return -1;
    }
    else
    {
        return 0;
    }
}

/**
 * Sorting algorithm for user objects.
 * Sorts by user name.
 * @param {type} user1   the first user to compare
 * @param {type} user2   the second user to compare
 * @returns {Number}
 */
function compareUser(user1, user2)
{
    if(user1.object.name > user2.object.name)
    {
        return 1;
    }
    else if(user1.object.name < user2.object.name)
    {
        return -1;
    }
    else
    {
        return 0;
    }
}

/**
 * Removes the current container CSS class specifying max-width and replaces it
 * with the specified one.
 * @param {string} widthClass The new CSS class specifying max-width to replace the old class with.
 */
function setContainerWidth(widthClass)
{
    let container = document.getElementById("container");
    container.classList.remove(currentWidthClass);
    currentWidthClass = widthClass;
    container.classList.add(currentWidthClass);
}

/**
 * 
 */
function setStatusSelectColor()
{
    switch(statusInput.value)
    {
        case "active":
            statusInput.style.borderColor = "#00a200";
            break;
            
        case "inactive":
            statusInput.style.borderColor = "#f20000";
            break;
        
        default:
            statusInput.style.borderColor = "black";
    }
}

/**
 * Filters the user list by the search value
 * Destroys input timer
 * @param {string} searchValue The value to search for in the user list
 */
function searchUsers(searchValue)
{
    userSearchTimer = null;
    userList.filter(searchValue);
}

/**
 * Generates the user list.
 * Adds all users to the AutoList.
 * Generates the list from the AutoList.
 */
function generateUserTable()
{
    let keys = Object.keys(userData);
    for(let i=0; i<keys.length; i++)
    {
        let user = userData[keys[i]];
        userList.addItem(generateUserRow(user), user);
    }

    userList.generateList();
}

/**
 * Generates the content for a user row in the user list
 * @param {type} user The user to generate content from 
 * @returns A div populated with the user's content
 */
function generateUserRow(user)
{
    let item = document.createElement("li");
    item.classList.add("user-item");
    item.addEventListener("click", () => {setManager(user)});
    
    let name = document.createElement("p");
    name.classList.add("user-name");
    name.innerText = user.name;
    
    let email = document.createElement("p");
    email.classList.add("user-email");
    email.innerText = user.email;

    item.appendChild(name);
    item.appendChild(email);
    
    return item;
}

/**
 * Sets the manager display and hidden field.
 * @param {type} user The manager to set. Can be null.
 */
function setManager(user)
{
    if(user == null)
    {
        document.getElementById("manager-ID").value = -1;
    }
    else
    {
        managerNameDisplay.setInputText(user.name);
        document.getElementById("manager-ID").value = user.id;
    }
}