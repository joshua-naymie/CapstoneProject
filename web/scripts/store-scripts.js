document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";

/**
 * @type Array  The current, filtered list of stores
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

var storeSearchInput;
var filterCheckbox;

var inputForm;
var submitButton;
var actionInput;

var inputs;
var removeManagerInput;
var userList;
var storeList;
var storeNameInput,
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
 * Filters a store based on given search value.
 * Checks store and manager name, case-insensitive.
 * @param {type} store The store to filter
 * @param {string} searchValue The value to find in the store or manager name
 * @returns Whether the search value is contained in the store or manager name
 */
const filterstore = (store, searchValue) => {
    if (store.name.toLowerCase().includes(searchValue)
    ||((store.managerId != null) && userData[store.managerId].name.toLowerCase().includes(searchValue)))
    {
        return filterCheckbox.checked ? true : store.isActive;
    }

    return false;
}

/**
 * Filters a user based on given search value.
 * Checks user's name and email, case-insensitive.
 * @param {type} user The user to filter
 * @param {string} searchValue The value to find in the user's name or email
 * @returns Whether the search value is contained in the store or manager name
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
    filterCheckbox = document.getElementById("store-filter");
    filterCheckbox.checked = false;
    filterCheckbox.addEventListener("change", () => { searchstoreList(storeSearchInput.value); });

    storeList = new AutoList("grid");
    storeList.container = document.getElementById("list-base");
    storeList.setFilterMethod(filterstore);
    storeList.setSortMethod(comparestore);
    generatestoreList();
    
    // setup input area
    inputArea = document.getElementById("input-area");
    inputArea.style.display = "none";
    
    // setup list area
    listArea = document.getElementById("list-area");
    listArea.classList.add("visible");
    
    // setup input form
    inputForm = document.getElementById("addStoreForm");
    inputForm.reset();
    
    statusInput = document.getElementById("status");
    statusInput.addEventListener("change", setStatusSelectColor);
    setStatusSelectColor();
    
    // setup form buttons
    submitButton = document.getElementById("ok__button");
    document.getElementById("cancel__button").addEventListener("click", cancelPressed);
    submitButton.addEventListener("click", () => { submitForm(currentAction) });
    
    // setup store search input
    storeSearchInput = document.getElementById("search-input");
    storeSearchInput.value = "";
    storeSearchInput.addEventListener("input", () => { searchstoreList(storeSearchInput.value) });

    // setup store name InputGroup
    storeNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "store-name");
    storeNameInput.setLabelText("store Name");
    storeNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    storeNameInput.setPlaceHolderText("eg. Hotline");
    storeNameInput.container = document.getElementById("store-name__input");
    configCustomInput(storeNameInput);

    // setup manager name InputGroup
    managerNameDisplay = new InputGroup(CSS_INPUTGROUP_MAIN, "manager-name");
    managerNameDisplay.setLabelText("Manager Name");
    managerNameDisplay.setPlaceHolderText("No manager");
    managerNameDisplay.input.setAttribute("autocomplete", "off");
    managerNameDisplay.input.setAttribute("disabled", "disabled");
    managerNameDisplay.input.type = "search";
    managerNameDisplay.container = document.getElementById("manager-name__display");
    configCustomInput(managerNameDisplay);
    
    // add EventListener to remove manager button
    removeManagerInput = document.getElementById("remove-manager");
    removeManagerInput.addEventListener("click", () => { setManager(); });
    
    // setup user search input
//    let userSearchInput = document.getElementById("user-search");
//    userSearchInput.addEventListener("input", () => {userSearchInputTimer(userSearchInput.value)});

    // add InputGroups to a collection
    inputs = new InputGroupCollection();
    inputs.add(storeNameInput);
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
function generatestoreList()
{
    removeAllChildren(document.getElementById("list-base"));

    // let keys = Object.keys(storeData);
    for(let i=0; i<storeData.length; i++)
    {
        let store = storeData[i];
        storeList.addItem(generatestoreRow(store), store);
    }

    storeList.generateList();
}

/**
 * Creates and returns a row in a store list
 * @param {store} store  The store whose info will populate the row
 * @returns {Element}  A div representing a row in a store list.
 */
function generatestoreRow(store)
{
    // item card
    let item = document.createElement("div");
    item.classList.add("list-item");
    item.addEventListener("click", () => { editstore(store); });

    let managerDiv = document.createElement("div");
    managerDiv.classList.add("manager-area");

    let managerName = document.createElement("p");
    managerName.classList.add("manager-name");
    managerName.innerText = store.managerId == null ? "" : userData[store.managerId].name;
    
    managerDiv.appendChild(managerName);

    let storeDiv = document.createElement("div");
    storeDiv.classList.add("store-area");

    let storeName = document.createElement("p");
    storeName.classList.add("store-name");
    storeName.innerText = store.name;

    storeDiv.appendChild(storeName);

    item.appendChild(storeDiv);
    item.appendChild(managerDiv);

    return item;
}

/**
 * Filters the list by matching store name or manager name.
 * Regenerates the list with filtered stores
 */
function searchstoreList(searchValue)
{
    searchValue = searchValue == null ? "" : searchValue;
    storeList.filter(searchValue);
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
    changeHeaderText("stores");
    fadeOutIn(inputArea, listArea);
    setTimeout(() => {
        document.getElementById("addstoreForm").reset();
        userList.filter();
        inputs.resetInputs();
    }, 200);
}

/**
 * Called when the new store button is clicked.
 * Sets the currentAction and the submit button text.
 * Fades ui to input panel.
 */
function addstore()
{
    currentAction = "add";
    submitButton.value = "Add";
    setManager();
    setStatusSelectColor();
    
    setContainerWidth("container--input-size");
    changeHeaderText("Add store");
    fadeOutIn(listArea, inputArea);
}

/**
 * Called when an item in the store list is clicked.
 * Sets the currentAction and the submit button text.
 * Fades ui to pre-populated input panel.
 * @param {type} store
 * @returns {undefined}
 */
function editstore(store)
{
    currentAction = "update";
    submitButton.value = "Update";

    storeNameInput.setInputText(store.name);
    setManager(userData[store.managerId]);
    statusInput.value = store.isActive ? "active" : "inactive";
    setStatusSelectColor();

    document.getElementById("store-ID").value = store.storeId;
    
    setContainerWidth("container--input-size");
    changeHeaderText("Edit store");
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
            managerNameDisplay.input.value = managerNameDisplay.input.value.split(":")[0];
            postAction(currentAction, "addstoreForm", "stores")
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
function comparestore(store1, store2)
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
//function generateUserTable()
//{
//    let keys = Object.keys(userData);
//    for(let i=0; i<keys.length; i++)
//    {
//        let user = userData[keys[i]];
//        userList.addItem(generateUserRow(user), user);
//    }
//
//    userList.generateList();
//}

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
        managerNameDisplay.setInputText("");
        removeManagerInput.disabled = true;
        removeManagerInput.classList.remove("remove-manager");
        removeManagerInput.classList.add("remove-manager--hidden");
    }
    else
    {
        document.getElementById("manager-ID").value = user.id;
        managerNameDisplay.setInputText(user.name);
        removeManagerInput.disabled = false;
        removeManagerInput.classList.add("remove-manager");
        removeManagerInput.classList.remove("remove-manager--hidden");
    }
}

function changeHeaderText(text)
{
    let header = document.getElementById("stores-header");
    header.classList.add("header--hidden");
    
    setTimeout(() => { header.innerText = text; header.classList.remove("header--hidden") }, 150);
}