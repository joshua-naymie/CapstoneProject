document.addEventListener('DOMContentLoaded', load, false);

const CSS_TABLE_CELL = "table-cell";


const generateViewContent = (task) => {

    let viewButton = document.createElement("a");
    viewButton.innerText = "View";
    viewButton.href = `viewtask?id=${task.ID}`;
    
    return viewButton;
}

function load()
{
    main = document.getElementById("table-container");
    
    let programCol = new DataColumn("Program", "programName", CSS_TABLE_CELL);
    let dateCol = new DataColumn("Date", "startTime", CSS_TABLE_CELL);
    let teamCol = new DataColumn("Team", "teamName", CSS_TABLE_CELL);
    let userCol = new DataColumn("Volunteer", "userList", CSS_TABLE_CELL);
//    let storeCol = new DataColumn("Store", "storeName", CSS_TABLE_CELL);
    let viewCol = new CustomColumn("Approve", "table-cell__view", generateViewContent)
    

    // Create and generate table with declared columns
    table = new AutoTable("table", taskData, [programCol, dateCol, teamCol, userCol, viewCol]);
    table.generateTable();
    
    main.appendChild(table.container);
    
    if(taskData.length == 0)
    {
        document.getElementById("no-task-message").style.display = "block";
    }
}