document.addEventListener('DOMContentLoaded', load, false);

const CSS_TABLE_CELL = "table-cell";

function load()
{
    let programCol = new DataColumn("Program", "programName", CSS_TABLE_CELL);
    let dateCol = new DataColumn("Date", "startTime", CSS_TABLE_CELL);
    let teamCol = new DataColumn("Team", "teamName", CSS_TABLE_CELL);
    let userCol = new DataColumn("Volunteers", "userList", CSS_TABLE_CELL);
    let storeCol = new DataColumn("Store", "storeName", CSS_TABLE_CELL);
    

    // Create and generate table with declared columns
    table = new AutoTable("table", taskData, [programCol, dateCol, teamCol, userCol, storeCol]);
    table.generateTable();
    
    document.getElementById("main").appendChild(table.container);
}