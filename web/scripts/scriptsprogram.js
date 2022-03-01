
document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";

//var data = [{"programId": 1,
//        "city": "Calgary",
//        "manager": "Jin Chen",
//        "program": "Cobbs Bread",
//        "phone": "9875554434",
//        "active": true},
//
//    {"programId": 2,
//        "city": "Edmonton",
//        "manager": "Jin Chen",
//        "program": "Hotline",
//        "phone": "5555551234",
//        "active": true},
//
//    {"programId": 1,
//        "city": "Red Deer",
//        "manager": "Jin Chen",
//        "program": "Other",
//        "phone": "9875554434",
//        "active": false}];


/**
 * @type Array  The current, filtered list of programs
 */
var currentListData;

/**
 * @type String  The action the server will take on POST
 */
var currentAction = "none";
var currentWidthClass = "container--list-size";

var listArea;
var inputArea

var searchInput;
var filterCheckbox;

var inputForm;
var submitButton;
var actionInput;
var inputHeader;

var inputs;
var userTable;
var programNameInput,
    managerNameInput,
    statusInput;

const generateUserCell = (user) => {
    let cell = document.createElement("div");
    cell.classList.add("user__cell");
    cell.addEventListener("click", () => {setManager(user)});
    
    let name = document.createElement("p");
    name.classList.add("user__cell-content__name");
    name.innerText = user.name;
    
    let email = document.createElement("p");
    email.classList.add("user__cell-content__email");
    email.innerText = user.email;
    
    cell.appendChild(name);
    cell.appendChild(email);
    
    return cell;
}

/**
 * Run when DOM loads
 */
function load()
{
    // sort list
    currentListData = data.sort(compareProgram);
    
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
    searchInput = document.getElementById("search-input");
    searchInput.value = "";
    searchInput.addEventListener("input", searchList);

    // setup 'Show Inactive' checkbox
    filterCheckbox = document.getElementById("program-filter");
    filterCheckbox.checked = false;
    filterCheckbox.addEventListener("change", searchList);

    // setup store name InputGroup
    programNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "program-name");
    programNameInput.setLabelText("Program Name");
    programNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    programNameInput.setPlaceHolderText("eg. Hotline");
    programNameInput.container = document.getElementById("program-name__input");
    configCustomInput(programNameInput);

    // setup manager name InputGroup
    managerNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "manager-name");
    managerNameInput.setLabelText("Manager Name");
    managerNameInput.setPlaceHolderText("eg. Jin Chen");
    managerNameInput.input.setAttribute("autocomplete", "off");
    managerNameInput.input.addEventListener("input", () => {searchUsers(managerNameInput.input.value)});
    managerNameInput.container = document.getElementById("manager-name__input");
    configCustomInput(managerNameInput);
    
    // Create columns
    let mainCol = new CustomColumn("User", "user__cell", generateUserCell);
    userTable = new AutoTable("table", userData, [mainCol], false);
    userTable.generateTable();

    document.getElementById("user-list").appendChild(userTable.container);

    // add InputGroups to a collection
    inputs = new InputGroupCollection();
    inputs.add(programNameInput);
    inputs.add(managerNameInput);

    searchList();
    
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
function generateList()
{
    removeAllChildren(document.getElementById("list-base"));
    let showAll = document.getElementById("program-filter").checked;

    if (currentListData.length > 0)
    {
        let i;
        for (i = 0; i < currentListData.length - 1; i++)
        {
            document.getElementById("list-base").appendChild(generateRow(currentListData[i]));
            document.getElementById("list-base").appendChild(document.createElement("hr"));
        }
        document.getElementById("list-base").appendChild(generateRow(currentListData[i]));
    }
}

/**
 * Creates and returns a row in a program list
 * @param {program} currentRow  The program whose info will populate the row
 * @returns {Element}  A div representing a row in a program list.
 */
function generateRow(currentRow)
{
    // item card
    let item = document.createElement("div");
    item.classList.add("list-item");
    item.addEventListener("click", () => { editProgram(currentRow); });

    let managerDiv = document.createElement("div");
    managerDiv.classList.add("manager-area");

    let managerName = document.createElement("p");
    managerName.classList.add("manager-name");
    managerName.innerText = currentRow.manager;
    
    managerDiv.appendChild(managerName);

    let programDiv = document.createElement("div");
    programDiv.classList.add("program-area");

    let programName = document.createElement("p");
    programName.classList.add("program-name");
    programName.innerText = currentRow.program;

    programDiv.appendChild(programName);

    item.appendChild(programDiv);
    item.appendChild(managerDiv);

    return item;
}

/**
 * Filters the list by matching program name or manager name.
 * Regenerates the list with filtered programs
 */
function searchList()
{
    currentListData = [];
    let searchText = searchInput.value.toLowerCase();

    // checks if search text is included in either the program name or manager name
    for (let i = 0; i < data.length; i++)
    {
        // searches for programs with matching program name or manager name
        if(data[i].program.toLowerCase().includes(searchText)
        ||((data[i].manager != null) && data[i].manager.toLowerCase().includes(searchText)))
        {
            // adds program depending on whether "show inactive" is checked
            if (filterCheckbox.checked ? true : data[i].active)
            {
                currentListData.push(data[i]);
            }
        }
    }

    generateList();
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
    submitButton.value = "Update";
    inputHeader.innerText = "Edit";

    programNameInput.setInputText(program.program);
    managerNameInput.setInputText(program.manager);
    statusInput.value = program.active ? "active" : "inactive";
    setStatusSelectColor();

    document.getElementById("program-ID").value = program.programId;
    
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
            managerNameInput.input.value = managerNameInput.input.value.split(":")[0];
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
    if(program1.program > program2.program)
    {
        return 1;
    }
    else if(program1.program < program2.program)
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


function searchUsers(search)
{
    for(let i=0; i<userTable.data.length; i++)
    {
        if(userTable.data[i].name.toLowerCase().includes(search)
        || userTable.data[i].email.toLowerCase().includes(search))
        {
            userTable.toggleRow(i, true);
        }
        else
        {
            userTable.toggleRow(i, false);
        }
    }
}

function setManager(user)
{
    managerNameInput.setInputText(user.name);
    document.getElementById("manager-ID").value = user.ID;
}