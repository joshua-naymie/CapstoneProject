document.addEventListener('DOMContentLoaded', load, false);

const data = [
                { 
                    id: 1,
                    firstName: "David",
                    lastName: "Wei",
                    phoneNum: "111-222-3333",
                    address: "123 1st St."
                },
                {
                    id: 2,
                    firstName: "Tara",
                    lastName: "Mutter",
                    phoneNum: "999-888-7654",
                    address: "333 3rd Ave."
                },
                {
                    id: 3,
                    firstName: "Saurav",
                    lastName: "Adhikari",
                    phoneNum: "147-369-0258",
                    address: "3002 Rickam Rd."
                },
                { 
                    id: 4,
                    firstName: "Irina",
                    lastName: "Skatchedub",
                    phoneNum: "444-555-6666",
                    address: "1101 Deli Dr."
                },
                { 
                    id: 5,
                    firstName: "Xiaomeng",
                    lastName: "Li",
                    phoneNum: "777-888-9999",
                    address: "54 Main St."
                },
                { 
                    id: 6,
                    firstName: "Agambeer",
                    lastName: "Brar",
                    phoneNum: "987-654-3210",
                    address: "99 Percent Wy."
                }
            ];

const detailsContent = (element) => {
    let editButton = document.createElement("button");
    editButton.innerText = "Show Details";
    editButton.addEventListener("click", () => showDetails(element));

    return editButton;
};

const addressLink = (value) => {
    let link = document.createElement("a");
    link.href = `https://www.google.ca/maps/search/${value}`;
    link.innerText = value;

    return link;
}

// const fullName = (user) => {
//     let content = document.createElement("p");
//     content.innerText = `${user.firstName} ${user.lastName}`;
//     content.style.margin = "0";

//     return content;
// }

function load()
{
    let main = document.getElementById("main");

    let col1 = new DataColumn("First Name", "firstName");
    let col2 = new DataColumn("Last Name", "lastName");
    let col3 = new DataColumn("Address", "address", addressLink);
    let col4 = new CustomColumn("Details", detailsContent);
    // let col4 = new CustomColumn("Full Name", fullName);

    let table = new AutoTable("table", data, [col1, col2, col4]);
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


function searchTable(searchValue)
{
    
}