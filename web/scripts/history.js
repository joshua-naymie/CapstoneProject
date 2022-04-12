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
    setTotalHours();
    let programCol = new DataColumn("Program", "program", CSS_TABLE_CELL);
    let dateCol = new DataColumn("Date", "startTime", CSS_TABLE_CELL);
    let stateCol = new DataColumn("Status", "status", CSS_TABLE_CELL);
    let cityCol = new DataColumn("City", "city", CSS_TABLE_CELL);
    let hoursCol = new DataColumn("Hours", "hours", CSS_TABLE_CELL);
//    let viewCol = new CustomColumn("View", CSS_TABLE_CELL, generateViewCell);

    // Create and generate table with declared columns
    table = new AutoTable("table", historyData, [programCol, dateCol, stateCol, cityCol, hoursCol]);
    table.generateTable();
    
    document.getElementById("main").appendChild(table.container);
}

function setTotalHours()
{
    let total = 0;
    for(let i=0; i<historyData.length; i++)
    {
        total += historyData[i].hours;
    }
    document.getElementById("total-hours").innerText = "" + total;
}