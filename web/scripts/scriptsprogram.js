
document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";

var data = [{"storeId": 1,
        "city": "Calgary",
        "manager": "Jin Chen",
        "program": "Cobbs Bread",
        "phone": "9875554434",
        "active": true},

    {"storeId": 2,
        "city": "Edmonton",
        "manager": "Jin Chen",
        "program": "Hotline",
        "phone": "5555551234",
        "active": true},

    {"storeId": 1,
        "city": "Red Deer",
        "manager": "Jin Chen",
        "program": "Other",
        "phone": "9875554434",
        "active": false}];
var inputs;
var currentListData;
var searchInput;
var filterCheckbox;

function load()
{
    searchInput = document.getElementById("search-input");
    searchInput.value = "";

    filterCheckbox = document.getElementById("program-filter");
    filterCheckbox.checked = false;
    filterCheckbox.addEventListener("change", searchList);

    currentListData = data;

    // create Store Name inputgroup
    let programNameInput = new InputGroupProgram(CSS_INPUTGROUP_MAIN, "program-name");
    programNameInput.setLabelText("Program Name");
    programNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    programNameInput.setPlaceHolderText("eg. Hotline");
    programNameInput.container = document.getElementById("program-name__input");
    configCustomInput(programNameInput);

    let managerNameInput = new InputGroupProgram(CSS_INPUTGROUP_MAIN, "manager-name");
    managerNameInput.setLabelText("Manager Name");
    managerNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    managerNameInput.setPlaceHolderText("eg. Jin Chen");
    managerNameInput.container = document.getElementById("manager-name__input");
    configCustomInput(managerNameInput);

    let cityInput = new InputGroupProgram(CSS_INPUTGROUP_MAIN, "city");
    cityInput.setLabelText("City");
    cityInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    cityInput.setPlaceHolderText("eg. Calgary");
    cityInput.container = document.getElementById("city__input");
    configCustomInput(cityInput);

    let phoneInput = new InputGroupProgram(CSS_INPUTGROUP_MAIN, "phone");
    phoneInput.setLabelText("Phone");
    phoneInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    phoneInput.addValidator(REGEX_PHONE, INPUTGROUP_STATE_WARNING, "*invalid");
    phoneInput.setPlaceHolderText("555-555-5555");
    phoneInput.container = document.getElementById("phone__input");
    configCustomInput(phoneInput);

    inputs = new InputGroupCollectionProgram();
    inputs.add(programNameInput);
    inputs.add(managerNameInput);
    inputs.add(cityInput);
    inputs.add(phoneInput);

    document.getElementById("search-input").addEventListener("input", searchList);
    document.getElementById("ok__button").addEventListener("click", okPressed);

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
    let item = document.createElement("div");
    item.classList.add("list-item");

    let addressDiv = document.createElement("div");
    addressDiv.classList.add("address");

    let cityLocation = document.createElement("p");
    cityLocation.classList.add("city__location");
    cityLocation.innerText = data.city;

    let remainingAddress = document.createElement("p");
    remainingAddress.classList.add("secondary-card__field");
    remainingAddress.innerText = `${data.manager}`;

    addressDiv.appendChild(cityLocation);
    addressDiv.appendChild(remainingAddress);

    let rightDiv = document.createElement("div");
    rightDiv.classList.add("right-align");

    let programName = document.createElement("p");
    programName.classList.add("program-name");
    programName.innerText = data.program;

    let phoneNum = document.createElement("p");
    phoneNum.classList.add("secondary-card__field");
    phoneNum.innerText = formatPhone(data.phone);


    rightDiv.appendChild(programName);
    rightDiv.appendChild(phoneNum);

    item.appendChild(addressDiv);
    item.appendChild(rightDiv);

    return item;
}

function formatPhone(phone)
{
    return `(${phone.substr(0, 3)}) ${phone.substr(3, 3)}-${phone.substr(6, 4)}`;
}

function searchList()
{
    currentListData = [];
    let searchText = searchInput.value.toLowerCase();

    for (let i = 0; i < data.length; i++)
    {
        if (data[i].manager.toLowerCase().includes(searchText)
                || data[i].city.toLowerCase().includes(searchText)
                || data[i].program.toLowerCase().includes(searchText)
                || data[i].phone.toLowerCase().startsWith(searchText))
        {
            if (filterCheckbox.checked ? true : data[i].active)
            {
                currentListData.push(data[i]);
            }
        }
    }

    generateList();
}

function removeAllChildren(element)
{
    while (element.firstChild)
    {
        element.removeChild(element.firstChild);
    }
}

function okPressed()
{
    inputs.validateAll();
}

function  hideShowAddProgram() {
    var x = document.getElementById("input-area");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }

}

function resetAddProgramForm() {
    document.getElementById("addProgramForm").reset();
    document.getElementById("input-area").style.display = "none";
} 