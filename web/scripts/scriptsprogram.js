
document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";

var data = [ { "storeId":1,
                "street":"Calgary",
                "city":"Manager Name",
                "company":"Cobbs Bread",
                "phone":"9875554434",
                "active":true },

                { "storeId":2,
                "street":"Edmonton",
                "city":"Manager Name",
                "company":"Hotline",
                "phone":"5555551234",
                "active":true },

                { "storeId":1,
                "street":"Red Deer",
                "city":"Manager Name",
                "company":"Other",
                "phone":"9875554434",
                "active":false } ];
var inputs;
var currentListData;
var searchInput;
var filterCheckbox;
function load()
{
    searchInput = document.getElementById("search-input");
    searchInput.value = "";

    filterCheckbox = document.getElementById("store-filter");
    filterCheckbox.checked = false;
    filterCheckbox.addEventListener("change", searchList);

    currentListData = data;

    // create Store Name inputgroup
    let storeNameInput = new InputGroupProgram(CSS_INPUTGROUP_MAIN, "store-name");
    storeNameInput.setLabelText("Program Name");
    storeNameInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    storeNameInput.setPlaceHolderText("eg. Hotline");
    storeNameInput.container = document.getElementById("store-name__input");
    configCustomInput(storeNameInput);

    let streetAddressInput = new InputGroupProgram(CSS_INPUTGROUP_MAIN, "street-address");
    streetAddressInput.setLabelText("Manager Name");
    streetAddressInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, "*required");
    streetAddressInput.setPlaceHolderText("eg. Jin Chen");
    streetAddressInput.container = document.getElementById("street-address__input");
    configCustomInput(streetAddressInput);

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
    inputs.add(storeNameInput);
    inputs.add(streetAddressInput);
    inputs.add(cityInput);
    inputs.add(phoneInput);

    document.getElementById("search-input").addEventListener("input", searchList);
    document.getElementById("ok__button").addEventListener("click", okPressed);

    searchList();
}

function configCustomInput(group)
{
    group.container.classList.add("store__custom-input");
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
    let showAll = document.getElementById("store-filter").checked;
    
    if(currentListData.length > 0)
    {
        let i;
        for(i=0; i<currentListData.length-1; i++)
        {
            if(showAll ? true : currentListData[i].active)
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

    let streetAddress = document.createElement("p");
    streetAddress.classList.add("address__street");
    streetAddress.innerText = data.street;

    let remainingAddress = document.createElement("p");
    remainingAddress.classList.add("secondary-card__field");
    remainingAddress.innerText = `${data.city}`;

    addressDiv.appendChild(streetAddress);
    addressDiv.appendChild(remainingAddress);

    let rightDiv = document.createElement("div");
    rightDiv.classList.add("right-align");

    let companyName = document.createElement("p");
    companyName.classList.add("company-name");
    companyName.innerText = data.company;

    let phoneNum = document.createElement("p");
    phoneNum.classList.add("secondary-card__field");
    phoneNum.innerText = formatPhone(data.phone);

    
    rightDiv.appendChild(companyName);
    rightDiv.appendChild(phoneNum);

    item.appendChild(addressDiv);
    item.appendChild(rightDiv);

    return item;
}

function formatPhone(phone)
{
    return `(${phone.substr(0,3)}) ${phone.substr(3,3)}-${phone.substr(6,4)}`;
}

function searchList()
{
    currentListData = [];
    let searchText = searchInput.value.toLowerCase();

    for(let i=0; i<data.length; i++)
    {
        if(data[i].city.toLowerCase().includes(searchText)
        || data[i].street.toLowerCase().includes(searchText)
        || data[i].company.toLowerCase().includes(searchText)
        || data[i].phone.toLowerCase().startsWith(searchText))
        {
            if(filterCheckbox.checked ? true : data[i].active)
            {
                currentListData.push(data[i]);
            }
        }
    }

    generateList();
}

function removeAllChildren(element)
{
    while(element.firstChild)
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