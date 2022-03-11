document.addEventListener('DOMContentLoaded', load, false);

const CSS_TABLE_CELL = "table-cell";

const generateViewCell = (task) => {
    let button = document.createElement("a");
//    button.classList.add(CSS_TABLE_CELL);
    button.style.width = "30px";
    button.href = `viewtask?id=${task.id}`;
    button.innerText = "View";
    
    return button;
}

function load()
{
    let programCol = new DataColumn("Program", "program", CSS_TABLE_CELL);
    let dateCol = new DataColumn("Date", "date", CSS_TABLE_CELL);
    let stateCol = new DataColumn("Status", "status", CSS_TABLE_CELL);
    let cityCol = new DataColumn("City", "city", CSS_TABLE_CELL);
//    let viewCol = new CustomColumn("View", CSS_TABLE_CELL, generateViewCell);

    // Create and generate table with declared columns
    table = new AutoTable("table", historyData, [programCol, dateCol, stateCol, cityCol]);
    table.generateTable();
    
    document.getElementById("main").appendChild(table.container);
}