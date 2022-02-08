document.addEventListener('DOMContentLoaded', load, false);

const CSS_TABLE_CELL = "table-cell";

//const data = [
//                { 
//                    email: "d.wei@telus.net",
//                    firstName: "David",
//                    lastName: "Wei",
//                    phoneNum: "111-222-3333",
//                    address: "123 1st St."
//                },
//                {
//                    email: "t.mutter@aol.com",
//                    firstName: "Tara",
//                    lastName: "Mutter",
//                    phoneNum: "999-888-7654",
//                    address: "333 3rd Ave."
//                },
//                {
//                    email: "s.adhikari@hotmail.com",
//                    firstName: "Saurav",
//                    lastName: "Adhikari",
//                    phoneNum: "147-369-0258",
//                    address: "3002 Rickam Rd."
//                },
//                { 
//                    email: "i.skatchedub@yahoo.ca",
//                    firstName: "Irina",
//                    lastName: "Skatchedub",
//                    phoneNum: "444-555-6666",
//                    address: "1101 Deli Dr."
//                },
//                { 
//                    email: "l.xiaomeng@gmail.com",
//                    firstName: "Xiaomeng",
//                    lastName: "Li",
//                    phoneNum: "777-888-9999",
//                    address: "54 Main St."
//                },
//                { 
//                    email: "a.brar@gmail.com",
//                    firstName: "Agambeer",
//                    lastName: "Brar",
//                    phoneNum: "987-654-3210",
//                    address: "99 Percent Wy."
//                }
//            ];

const detailsContent = (element) => {
    let editButton = document.createElement("div");
    editButton.innerText = "Edit";
    editButton.addEventListener("click", () => showDetails(element));

    return editButton;
};

const addressLink = (value) => {
    let container = document.createElement("div");
    let link = document.createElement("a");
    link.href = `mailto:${value}`;
    link.innerText = value;
    
    container.appendChild(link);

    return container;
}

// const fullName = (user) => {
//     let content = document.createElement("p");
//     content.innerText = `${user.firstName} ${user.lastName}`;
//     content.style.margin = "0";

//     return content;
// }

var table;
var main;

function load()
{
    document.getElementById("searchbar").addEventListener("input", (e) => {searchTable(e.target.value);});
    
    main = document.getElementById("table-container");

    let col1 = new DataColumn("First Name", "firstName", CSS_TABLE_CELL);
    let col2 = new DataColumn("Last Name", "lastName", CSS_TABLE_CELL);
    let col3 = new DataColumn("Email", "id", CSS_TABLE_CELL, addressLink);
    let col4 = new CustomColumn("Edit", "table-cell__edit", detailsContent);
    // let col4 = new CustomColumn("Full Name", fullName);

    table = new AutoTable("table", data, [col2, col1, col4]);
    table.addColumn(col3, 2);

    table.generateTable();

    main.appendChild(table.container);
}

function showDetails(user)
{
    alert(`Id: ${user.id}\n` +
          `Name: ${user.firstName} ${user.lastName}\n` +
          `Address: ${user.address}\n` + 
          `Phone #: ${user.phoneNum}`);
}

function removeAllChildren(element)
{
    while(element.firstChild)
    {
        element.removeChild(element.firstChild);
    }
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