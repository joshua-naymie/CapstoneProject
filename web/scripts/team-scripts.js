document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";

/**
 * @type Array  The current, filtered list of teams
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

var teamSearchInput;
var storeSearchInput;

var inputForm;
var submitButton;
var actionInput;

var inputs;
var removeManagerInput;
var storeList;
var teamList;
var teamNameInput,
    programInput,
    supervisorInput,
    streetAddressInput,
    provinceInput,
    postalCodeInput,
    cityInput,
    
    managerNameDisplay,
    statusInput;

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
 * Filters a team based on given search value.
 * Checks team and manager name, case-insensitive.
 * @param {type} team The team to filter
 * @param {string} searchValue The value to find in the team or manager name
 * @returns Whether the search value is contained in the team or manager name
 */
const filterTeam = (team, searchValue) => {
    if((team.name != null) && (team.name.toLowerCase().includes(searchValue)))
    {
        return true;
    }

    return false;
}

/**
 * Filters a store based on given search value.
 * Checks store and manager name, case-insensitive.
 * @param {type} store The store to filter
 * @param {string} searchValue The value to find in the store or manager name
 * @returns Whether the search value is contained in the store or manager name
 */
const filterStore = (store, searchValue) => {
    if (store.name.toLowerCase().includes(searchValue)
    || (store.address.toLowerCase().includes(searchValue)))
    {
        return true;
    }
    return false;
}

const maxTeamSizeValidation = (size) => {
    if(size < 1
    || size > 300)
    {
        return false;
    }
    
    return true;
}

const programChanged = () =>
{
    setProgramId(programInput.value);
}

/**
 * Run when DOM loads
 */
function load()
{
    programInput = document.getElementById("programs_select");
    programInput.addEventListener("click", programChanged);
    
    supervisorInput = document.getElementById("supervisors_select");
    
    teamList = new AutoList("flex");
    teamList.container = document.getElementById("team-list");
    teamList.setFilterMethod(filterTeam);
    teamList.setSortMethod(compareTeam);
    generateTeamList();
    
    storeList = new AutoList("flex");
    storeList.container = document.getElementById("store-list");
    storeList.setFilterMethod(filterStore);
    storeList.setSortMethod(compareStore);
    generateStoreList();
    
    // setup input area
    inputArea = document.getElementById("input-area");
    inputArea.style.display = "none";
    
    // setup list area
    listArea = document.getElementById("list-area");
    listArea.classList.add("visible");
    
    // setup input form
    inputForm = document.getElementById("addTeamForm");
    inputForm.reset();
    
    statusInput = document.getElementById("programs_select");
    
    // setup form buttons
    submitButton = document.getElementById("ok__button");
    document.getElementById("cancel__button").addEventListener("click", cancelPressed);
    submitButton.addEventListener("click", () => { submitForm(currentAction) });
    
    // setup team search input
    teamSearchInput = document.getElementById("search-input");
    teamSearchInput.value = "";
    teamSearchInput.addEventListener("input", () => { teamList.filter(teamSearchInput.value) });

    storeSearchInput = document.getElementById("store-search");
    storeSearchInput.value = "";
    storeSearchInput.addEventListener("input", () => { storeList.filter(storeSearchInput.value); });

    // setup team name InputGroup
    teamNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "team-name");
    teamNameInput.setLabelText("Team Name");
    teamNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    teamNameInput.setPlaceHolderText("eg. Hotline");
    teamNameInput.container = document.getElementById("team-name__input");
    configCustomInput(teamNameInput);
    
    maxSizeInput = new InputGroup(CSS_INPUTGROUP_MAIN, "max-size");
    maxSizeInput.input.type = "number";
    maxSizeInput.setInputText(30);
    maxSizeInput.input.max = 300;
    maxSizeInput.input.min = 1;
    maxSizeInput.setLabelText("Max Size");
    maxSizeInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    maxSizeInput.addValidator(maxTeamSizeValidation, INPUTGROUP_STATE_WARNING, "*invalid");
    maxSizeInput.setPlaceHolderText("eg. 20");
    maxSizeInput.container = document.getElementById("max-size__input");
    configCustomInput(maxSizeInput);
    
    // add InputGroups to a collection
    inputs = new InputGroupCollection();
    inputs.add(teamNameInput);
    inputs.add(maxSizeInput);
    
    setProgramSelect();
    setSupervisorSelect();
    
    setProgramId(1);
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
    group.container.classList.add("team__custom-input");
    group.container.appendChild(group.input);

    let labelMessageDiv = document.createElement("div");
    labelMessageDiv.classList.add("label-message__container");
    labelMessageDiv.appendChild(group.label);
    labelMessageDiv.appendChild(group.message);

    group.container.appendChild(labelMessageDiv);
}

/**
 * Generates the list of stores displayed to the user.
 * Creates store cards and inserts line breaks between them.
 */
function generateTeamList()
{
    removeAllChildren(teamList);

    console.log(teamData.length);
    for(let i=0; i<teamData.length; i++)
    {
        let team = teamData[i];
        teamList.addItem(generateTeamRow(team), team);
    }

    teamList.generateList();
}

/**
 * Creates and returns a row in a store list
 * @param {store} store  The store whose info will populate the row
 * @returns {Element}  A div representing a row in a store list.
 */
function generateTeamRow(team)
{
    // item card
    let item = document.createElement("div");
    item.classList.add("list-item");
    item.addEventListener("click", () => { editTeam(team); });

    let name = document.createElement("p");
    name.innerText = team.name;
    name.classList.add("team-name");
    
    let program = document.createElement("p");
    program.innerText = getProgramByID(team.programID).name;
    program.classList.add("program-name");
    
    item.appendChild(name);
    item.appendChild(program);

    console.log(`Item: ${item}`);
    return item;
}

/**
 * Filters the list by matching store name or manager name.
 * Regenerates the list with filtered stores
 */
function searchTeamList(searchValue)
{
    searchValue = searchValue == null ? "" : searchValue;
    teamList.filter(searchValue);
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
    changeHeaderText("Teams");
    fadeOutIn(inputArea, listArea);
    setTimeout(() => {
        document.getElementById("addTeamForm").reset();
        inputs.resetInputs();
    }, 200);
}

/**
 * Called when the new store button is clicked.
 * Sets the currentAction and the submit button text.
 * Fades ui to input panel.
 */
function addTeam()
{
    currentAction = "add";
    submitButton.value = "Add"; 
    document.getElementById("team-ID").value = -1;
    document.getElementById("store-ID").value = -1;
    
    setContainerWidth("container--input-size");
    changeHeaderText("Add Team");
    fadeOutIn(listArea, inputArea);
}

/**
 * Called when an item in the store list is clicked.
 * Sets the currentAction and the submit button text.
 * Fades ui to pre-populated input panel.
 * @param {type} store
 * @returns {undefined}
 */
function editTeam(team)
{
    currentAction = "update";
    submitButton.value = "Update";

    document.getElementById("store-ID").value = team.storeID;

    document.getElementById("team-ID").value = team.id;
    teamNameInput.setInputText(team.name);
    programInput.value = team.programID;
    programChanged();
    supervisorInput.value = team.supervisorID;
    maxSizeInput.setInputText(team.maxSize);
    
//    let company = getCompanyByID(store.companyId);
//    programInput.setInputText(company.name);
//    maxSizeInput.setInputText(store.phoneNum);
//
//    document.getElementById("store-ID").value = store.storeId;
    
    setContainerWidth("container--input-size");
    changeHeaderText("Edit Team");
    fadeOutIn(listArea, inputArea);
}

function getStoreNameById(id)
{
    for(let i=0; i<storeData.length; i++)
    {
        if(storeData[i].id = id)
        {
            return storeData[i].name;
        }
    }
    
    return null;
}


/**
 * Checks input validation and gets user confirmation.
 * Submits the form with the currentAction.
 */
function submitForm()
{
    if(inputs.validateAll())
    {
        showConfirmationModal(`Are you sure you want to ${currentAction} this team?`, () => {
            postAction(currentAction, "addTeamForm", "teams")
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
        
    }, 100);
}

/**
 * Sorting algorithm for store objects.
 * Sorts by store name.
 * @param {type} store1   the first store to compare
 * @param {type} store2   the second store to compare
 * @returns {Number}
 */
function compareTeam(team1, team2)
{
    if(team1.object.name > team2.object.name)
    {
        return 1;
    }
    else if(team1.object.name < team2.object.name)
    {
        return -1;
    }
    else
    {
        return 0;
    }
}

/**
 * Sorting algorithm for store objects.
 * Sorts by store name.
 * @param {type} store1   the first store to compare
 * @param {type} store2   the second store to compare
 * @returns {Number}
 */
function compareStore(store1, store2)
{
    if(store1.object.name > store2.object.name)
    {
        return 1;
    }
    else if(store1.object.name < store2.object.name)
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
 * Changes the text of the header
 * @param {type} text The text to set the header
 */
function changeHeaderText(text)
{
    let header = document.getElementById("team-header");
    header.classList.add("header--hidden");
    
    setTimeout(() => { header.innerText = text; header.classList.remove("header--hidden") }, 150);
}

/**
 * Gets a company from companyData that matches a given id
 * @param {number} id The id to match
 * @returns The company that matches the given id. Returns null if no match found
 */
function getCompanyByID(id)
{
    for(let i=0; i<companyData.length; i++)
    {
        if(companyData[i].id === id)
        {
            return companyData[i];
        }
    }
    
    return null;
}

/**
 * Gets a program from programData that matches a given id
 * @param {number} id The id to match
 * @returns The program that matches the given id. Returns null if no match found
 */
function getProgramByID(id)
{
    for(let i=0; i<programData.length; i++)
    {
        if(programData[i].id === id)
        {
            return programData[i];
        }
    }
    
    return null;
}

/**
 * 
 * @returns {undefined}
 */
function setProgramSelect()
{
    let select = document.getElementById("programs_select");
    
    for(let i=0; i<programData.length; i++)
    {
        let temp = document.createElement("option");
        temp.label = programData[i].name;
        temp.value = programData[i].id;
        
        select.appendChild(temp);
    }
}

/**
 * Sets the current option for the supervisor select.
 * @returns {undefined}
 */
function setSupervisorSelect()
{
    let select = document.getElementById("supervisors_select");
    
    for(let i=0; i<supervisorData.length; i++)
    {
        let temp = document.createElement("option");
        temp.label = supervisorData[i].name;
        temp.value = supervisorData[i].id;
        
        select.appendChild(temp);
    }
}

function generateStoreList()
{
    for(let i=0; i<storeData.length; i++)
    {
        storeList.addItem(generateStoreRow(storeData[i]), storeData[i]);
    }
    
    storeList.generateList();
}

function generateStoreRow(store)
{
    let item = document.createElement("div");
    item.classList.add("list-item");
    item.addEventListener("click", () => { setTeamStore(store); });
    
    let address = document.createElement("p");
    address.classList.add("store-address");
    address.innerText = store.address;  
    
    let name = document.createElement("p");
    name.classList.add("store-name");
    name.innerText = store.name;
    
    item.appendChild(name);
    item.appendChild(address);
    

    return item;
}

function setTeamStore(store)
{
    document.getElementById("store-ID").value = store.id;
    teamNameInput.setInputText(store.name);
}

function setProgramId(id)
{
    document.getElementById("program-ID").value = id;
    
    teamNameInput.input.disabled = id == 1;
    
    if(id != 1)
    {
        document.getElementById("store-ID").value = -1;
    }
}