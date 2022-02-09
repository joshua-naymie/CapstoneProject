document.addEventListener('DOMContentLoaded', load, false);

const CSS_TABLE_CELL = "table-cell";

const editContent = (element) => {
    let editButton = document.createElement("div");
    editButton.innerText = "Edit";
    
    let x = element.email;
    editButton.addEventListener("click", () => editPressed(element.id));

    return editButton;
};

const emailLink = (value) => {
    let container = document.createElement("div");
    
    let link = document.createElement("a");
    link.href = `mailto:${value}`;
    link.innerText = value;
    
    container.appendChild(link);

    return container;
}

var table;
var main;

/**
 * Run when DOM loaded
 */
function load()
{
    window.addEventListener("resize", setListHeight);
    
    document.getElementById("searchbar").addEventListener("input", (e) => {searchTable(e.target.value);});
    main = document.getElementById("table-container");

    // Create columns
    let fNameCol = new DataColumn("First Name", "firstName", CSS_TABLE_CELL);
    let lNameCOl = new DataColumn("Last Name", "lastName", CSS_TABLE_CELL);
    let emailCol = new DataColumn("Email", "id", CSS_TABLE_CELL, emailLink);
    let editCol = new CustomColumn("Edit", "table-cell__edit", editContent);

    // Create and generate table with declared columns
    table = new AutoTable("table", data, [fNameCol, lNameCOl, emailCol, editCol]);
    table.generateTable();

    main.appendChild(table.container);
    
    setListHeight();
    
    document.getElementById("content-section").appendChild(document.createElement("div"));
}

function editPressed(email)
{
    document.getElementById("username").value = email;
    
    postAction("edit", "submit-form", "Account");
}

function searchTable(searchValue)
{
    if(searchValue.length > 1)
    {
        for(let i=0; i<table.data.length; i++)
        {
            if(table.data[i].firstName.toLowerCase().startsWith(searchValue.toLowerCase())
            || table.data[i].lastName.toLowerCase().startsWith(searchValue.toLowerCase()))
            {
                table.toggleRow(i, true);
            }
            else
            {
                table.toggleRow(i, false);
            }
        }
    }
    else
    {
        for(let i=0; i<table.rows.length; i++)
        {
            table.toggleRow(i, true);
        }
    }
}

function setListHeight()
{
    
    let controls = document.getElementById("controls");
    table.container.style.maxHeight = (window.innerHeight - 96 - 20) + "px";
    
    console.log(controls.offsetHeight);
}