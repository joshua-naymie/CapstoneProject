document.addEventListener('DOMContentLoaded', load, false);

const CSS_TABLE_CELL = "table-cell";

const generateViewContent = (task) => {
    let container = document.createElement("div");
    let button = document.createElement("a");
    button.href = `viewtask?id=${task.id}`;
    button.innerText = "View";
    container.appendChild(button);
    
    return container;
}

function load()
{
    let programCol = new DataColumn("Program", "programName", CSS_TABLE_CELL);
    let dateCol = new DataColumn("Date", "startTime", CSS_TABLE_CELL);
    let teamCol = new DataColumn("Team", "teamName", CSS_TABLE_CELL);
    let userCol = new DataColumn("Volunteers", "userList", CSS_TABLE_CELL);
    let storeCol = new DataColumn("Store", "storeName", CSS_TABLE_CELL);
    let viewCol = new CustomColumn("View", CSS_TABLE_CELL, generateViewContent)
    

    // Create and generate table with declared columns
    table = new AutoTable("table", taskData, [programCol, dateCol, teamCol, userCol, storeCol, viewCol]);
    table.generateTable();
    
    document.getElementById("main").appendChild(table.container);
}