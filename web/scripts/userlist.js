document.addEventListener('DOMContentLoaded', load, false);

const CSS_TABLE_CELL = "table-cell";

const editContent = (user) => {
    let editButton = document.createElement("a");
    editButton.innerText = "Edit";
    editButton.href = `add?username=${user.id}`;
//    editButton.addEventListener("click", () => editPressed(user.id));

    return editButton;
};

/**
 * Creates an email link within a table cell
 * @param {string} email The email to create the link with
 * @returns {Element} The table cell with an email link in it
 */
const emailLink = (email) => {
    let container = document.createElement("div");
    
    let link = document.createElement("a");
    link.href = `mailto:${email}`;
    link.innerText = email;
    
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
    let lNameCol = new DataColumn("Last Name", "lastName", CSS_TABLE_CELL);
    let emailCol = new DataColumn("Email", "id", CSS_TABLE_CELL, emailLink);
    let editCol = new CustomColumn("Edit", "table-cell__edit", editContent);

    // Create and generate table with declared columns
    table = new AutoTable("table", data, [lNameCol, fNameCol, emailCol, editCol]);
    table.generateTable();

    main.appendChild(table.container);
    
    setListHeight();
    
    document.getElementById("content-section").appendChild(document.createElement("div"));
}

function editPressed(email)
{
    document.getElementById("username").value = email;
    
    postAction("edit", "submit-form", "users");
}

function exportPressed()
{
    postAction("export", "submit-form", "users");
}

function exportCSVPressed()
{
    postAction("export-csv", "submit-form", "users");
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
}