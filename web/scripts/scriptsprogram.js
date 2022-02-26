
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


var currentListData;
var currentAction = "none";

var listArea;
var inputArea

var searchInput;
var filterCheckbox;

var inputForm;
var submitButton;
var actionInput;

var inputs;
var programNameInput,
    managerNameInput,
    statusInput;

function load()
{
    currentListData = data;
    
    inputArea = document.getElementById("input-area");
    inputArea.style.display = "none";
    
    listArea = document.getElementById("list-area");
    listArea.classList.add("visible");
    
    inputForm = document.getElementById("addProgramForm");
    inputForm.reset();
    
    actionInput = document.getElementById("action");
    statusInput = document.getElementById("status");
    submitButton = document.getElementById("ok__button");
    
    document.getElementById("cancel__button").addEventListener("click", cancelPressed);
    submitButton.addEventListener("click", () => { submitForm(currentAction) });
    
    searchInput = document.getElementById("search-input");
    searchInput.value = "";
    searchInput.addEventListener("input", searchList);

    filterCheckbox = document.getElementById("program-filter");
    filterCheckbox.checked = false;
    filterCheckbox.addEventListener("change", searchList);

    // create Store Name inputgroup
    programNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "program-name");
    programNameInput.setLabelText("Program Name");
    programNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    programNameInput.setPlaceHolderText("eg. Hotline");
    programNameInput.container = document.getElementById("program-name__input");
    configCustomInput(programNameInput);

    managerNameInput = new InputGroup(CSS_INPUTGROUP_MAIN, "manager-name");
    managerNameInput.setLabelText("Manager Name");
    managerNameInput.setPlaceHolderText("eg. Jin Chen");
    managerNameInput.container = document.getElementById("manager-name__input");
    configCustomInput(managerNameInput);

    inputs = new InputGroupCollection();
    inputs.add(programNameInput);
    inputs.add(managerNameInput);

    searchList();
}

function configCustomInput(group)
{
    group.container.classList.add("program__custom-input");
    group.container.appendChild(group.input);

    let labelMessageDiv = document.createElement("div");
    labelMessageDiv.classList.add("label-message__container");
    labelMessageDiv.appendChild(group.label);
    labelMessageDiv.appendChild(group.message);

    group.container.appendChild(labelMessageDiv);
}

function generateList()
{
    removeAllChildren(document.getElementById("list-base"));
    let showAll = document.getElementById("program-filter").checked;

    if (currentListData.length > 0)
    {
        let i;
        for (i = 0; i < currentListData.length - 1; i++)
        {
            if (showAll ? true : currentListData[i].active)
            {

            }
            document.getElementById("list-base").appendChild(generateRow(currentListData[i]));
            document.getElementById("list-base").appendChild(document.createElement("hr"));
        }
        document.getElementById("list-base").appendChild(generateRow(currentListData[i]));
    }
}

function generateRow(data)
{
    // item card
    let item = document.createElement("div");
    item.classList.add("list-item");
    item.addEventListener("click", () => { editProgram(data); });

    let managerDiv = document.createElement("div");
    managerDiv.classList.add("manager-area");

    let managerName = document.createElement("p");
    managerName.classList.add("manager-name");
    managerName.innerText = data.manager;
    
    managerDiv.appendChild(managerName);

    let programDiv = document.createElement("div");
    programDiv.classList.add("program-area");

    let programName = document.createElement("p");
    programName.classList.add("program-name");
    programName.innerText = data.program;

    programDiv.appendChild(programName);

    item.appendChild(programDiv);
    item.appendChild(managerDiv);

    return item;
}

function searchList()
{
    currentListData = [];
    let searchText = searchInput.value.toLowerCase();

    // checks if search text is included in either the program name or manager name
    for (let i = 0; i < data.length; i++)
    {
        if(data[i].program.toLowerCase().includes(searchText)
        || ((data[i].program != null) && data[i].manager.toLowerCase().includes(searchText)))
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

function cancelPressed()
{
    currentAction = "none";
    
    fadeOutIn(inputArea, listArea);
    setTimeout(() => {
        document.getElementById("addProgramForm").reset();
        inputs.resetInputs() }, 100);
}

function addProgram()
{
    currentAction = "add";
    submitButton.value = "Add";
    
    fadeOutIn(listArea, inputArea);
}

function editProgram(program)
{
    currentAction = "update";
    submitButton.value = "Update";

    programNameInput.setInputText(program.program);
    managerNameInput.setInputText(program.manager);
    statusInput.value = program.active ? "active" : "inactive";

    document.getElementById("program-ID").value = program.programId;


    fadeOutIn(listArea, inputArea);
}


function submitForm(action)
{
    if(inputs.validateAll())
    {
        postAction(action, "addProgramForm", "programs");
    }
}

function fadeOutIn(outElement, inElement)
{
    outElement.classList.remove("visible");
    
    setTimeout(() => {
        outElement.style.display = "none";
        
        inElement.style.display = "block";
        setTimeout(() => {inElement.classList.add("visible");}, 50);
        
    }, 100)
}