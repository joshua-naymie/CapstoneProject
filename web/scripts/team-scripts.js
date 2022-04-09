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
var filterCheckbox;

var inputForm;
var submitButton;
var actionInput;

var inputs;
var removeManagerInput;
var userList;
var teamList;
var teamNameInput,
    companyInput,
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
    if ((team.teamName != null) && (team.teamName.toLowerCase().includes(searchValue))
    ||((team.managerId != null) && userData[team.managerId].name.toLowerCase().includes(searchValue)))
    {
        return filterCheckbox.checked ? true : team.isActive;
    }

    return false;
}

/**
 * Filters a user based on given search value.
 * Checks user's name and email, case-insensitive.
 * @param {type} user The user to filter
 * @param {string} searchValue The value to find in the user's name or email
 * @returns Whether the search value is contained in the team or manager name
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
    filterCheckbox = document.getElementById("team-filter");
    filterCheckbox.checked = false;
    filterCheckbox.addEventListener("change", () => { searchTeamList(teamSearchInput.value); });

    teamList = new AutoList("flex");
    teamList.container = document.getElementById("list-base");
    teamList.setFilterMethod(filterTeam);
    teamList.setSortMethod(compareTeam);
    generateTeamList();
    
    // setup input area
    inputArea = document.getElementById("input-area");
    inputArea.style.display = "none";
    
    // setup list area
    listArea = document.getElementById("list-area");
    listArea.classList.add("visible");
    
    // setup input form
    inputForm = document.getElementById("addTeamForm");
    inputForm.reset();
    
    statusInput = document.getElementById("status");
    statusInput.addEventListener("change", setStatusSelectColor);
    setStatusSelectColor();
    
    // setup form buttons
    submitButton = document.getElementById("ok__button");
    document.getElementById("cancel__button").addEventListener("click", cancelPressed);
    submitButton.addEventListener("click", () => { submitForm(currentAction) });
    
    // setup team search input
    teamSearchInput = document.getElementById("search-input");
    teamSearchInput.value = "";
    teamSearchInput.addEventListener("input", () => { searchTeamList(teamSearchInput.value) });

    // setup team name InputGroup
    teamNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "team-name");
    teamNameInput.setLabelText("Store Name");
    teamNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    teamNameInput.setPlaceHolderText("eg. Kensington Starbucks");
    teamNameInput.container = document.getElementById("team-name__input");
    configCustomInput(teamNameInput);
    
    // setup company InputGroup
    companyInput = new InputGroup(CSS_INPUTGROUP_MAIN, "company-name");
    companyInput.input.setAttribute("list", "company-list");
    companyInput.setLabelText("Company");
    companyInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    companyInput.setPlaceHolderText("eg. Starbucks");
    companyInput.container = document.getElementById("company__input");
    configCustomInput(companyInput);
    
    // setup team street address InputGroup
    streetAddressInput = new InputGroup(CSS_INPUTGROUP_MAIN, "team-address");
    streetAddressInput.setLabelText("Street Address");
    streetAddressInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    streetAddressInput.setPlaceHolderText("eg. 1234 Main St.");
    streetAddressInput.container = document.getElementById("street-address__input");
    configCustomInput(streetAddressInput);

    // setup team city InputGroup
    cityInput = new InputGroup(CSS_INPUTGROUP_MAIN, "team-city");
    cityInput.setLabelText("City");
    cityInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    cityInput.setPlaceHolderText("eg. Calgary");
    cityInput.container = document.getElementById("city__input");
    configCustomInput(cityInput);

    // setup store province InputGroup
    provinceInput = new InputGroup(CSS_INPUTGROUP_MAIN, "store-province");
    provinceInput.input.disabled = true;
    provinceInput.setLabelText("Prov.");
    provinceInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*");
    provinceInput.setPlaceHolderText("eg. AB");
    provinceInput.container = document.getElementById("province__input");
    configCustomInput(provinceInput);

    // setup store postal code InputGroup
    postalCodeInput = new InputGroup(CSS_INPUTGROUP_MAIN, "store-postal-code");
    postalCodeInput.setLabelText("Postal");
    postalCodeInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*");
    postalCodeInput.setPlaceHolderText("A1A 1A1");
    postalCodeInput.container = document.getElementById("postal-code__input");
    configCustomInput(postalCodeInput);
    
    contactInput = new InputGroup(CSS_INPUTGROUP_MAIN, "store-contact");
    contactInput.setLabelText("Contact Name");
    contactInput.setPlaceHolderText("John Smith");
    contactInput.container = document.getElementById("contact__input");
    configCustomInput(contactInput);
    
    phoneInput = new InputGroup(CSS_INPUTGROUP_MAIN, "store-phone");
    phoneInput.setLabelText("Phone Number");
    phoneInput.addValidator(REGEX_PHONE_OPTIONAL, INPUTGROUP_STATE_WARNING, "*invalid");
    phoneInput.setPlaceHolderText("555-555-5555");
    phoneInput.container = document.getElementById("phone__input");
    configCustomInput(phoneInput);
    
    // add InputGroups to a collection
    inputs = new InputGroupCollection();
//    inputs.add(storeNameInput);
    inputs.add(companyInput);
    inputs.add(streetAddressInput);
    inputs.add(cityInput);
    inputs.add(provinceInput);
    inputs.add(postalCodeInput);
    inputs.add(phoneInput);
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
    group.container.classList.add("store__custom-input");
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
    removeAllChildren(document.getElementById("list-base"));

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
    name.classList.add("---===REPLACE===---");
    
    let program = document.createElement("p");
    program.innerText = getProgramByID(team.programID).name;
    name.classList.add("---===REPLACE===---");
    
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
    changeHeaderText("Stores");
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
    provinceInput.setInputText("AB");
    setStatusSelectColor();
    
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
function editTeam(store)
{
    currentAction = "update";
    submitButton.value = "Update";

    document.getElementById("store-ID").value = store.storeId;
    storeNameInput.setInputText(store.name);
    let company = getCompanyByID(store.companyId);
    companyInput.setInputText(company.name);
    streetAddressInput.setInputText(store.streetAddress);
    cityInput.setInputText(store.city);
    provinceInput.setInputText("AB");
    postalCodeInput.setInputText(store.postalCode);
    contactInput.setInputText(store.contactName);
    phoneInput.setInputText(store.phoneNum);
    statusInput.value = store.isActive ? "active" : "inactive";
    setStatusSelectColor();

    document.getElementById("store-ID").value = store.storeId;
    
    setContainerWidth("container--input-size");
    changeHeaderText("Edit Team");
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
        showConfirmationModal(`Are you sure you want to ${currentAction} this store?`, () => {
            postAction(currentAction, "addStoreForm", "stores")
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
 * Sets the border color of the store status select element depending on the status
 */
function setStatusSelectColor()
{
    switch(statusInput.value)
    {
        case "active":
            // green for active
            statusInput.style.borderColor = "#00a200";
            break;
            
        case "inactive":
            // red for inactive
            statusInput.style.borderColor = "#f20000";
            break;
        
        default:
            statusInput.style.borderColor = "black";
    }
}

function changeHeaderText(text)
{
    let header = document.getElementById("team-header");
    header.classList.add("header--hidden");
    
    setTimeout(() => { header.innerText = text; header.classList.remove("header--hidden") }, 150);
}

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