class AutoTable
{
    /**
     * Constructor for AutoTable
     * @param {string} className The CSS class structure to give the table
     * @param {Array<object>} data The data to populate the table with
     * @param {Array<DataColumn | CustomColumn>} columnMaps The columns of data to display
     */
    constructor(className, data, columnMaps)
    {
        this.container = document.createElement("div");
        this.container.classList.add(className);
        
        this.className = className;
        this.data = data;
        this.rowManager = new RowManager(`${this.className}-row`, columnMaps);
        
        this.rows = [];
    }

    /**
     * Adds a DataColumn or CustomColumn to the table
     * @param {DataColumn | CustomColumn} column    The column to add to the table
     * @param {number} position                     The position to insert the column into.
     *                                              Added to the end if null.
     */
    addColumn(column, position)
    {
        this.rowManager.addColumn(column, position);
    }

    /**
     * Generates both the header and content section of the table
     */
    generateTable()
    {
        this.generateHeader();
        this.generateContent();
    }

    /**
     * Generates the header of the table
     */
    generateHeader()
    {
        this.container.appendChild(this.rowManager.generateHeader());
    }

    /**
     * Generates the content for the table using provided data
     */
    generateContent()
    {
        for(let i=0; i<this.data.length; i++)
        {
            let row = i === this.data.length-1 ? this.rowManager.generateContent(i, data[i]) : this.rowManager.generateContent(i, data[i], true);
            this.rows.push(row);
            
            
            this.container.appendChild(row);   //change
        }
    }

    getRowContent(data, isLastRow)
    {
        let row = document.createElement("div");
            row.classList.add(`${this.className}-row`);
    
        for(let j=0; j<this.rowManager.getSize(); j++)
        {
            let cell = this.rowManager.generateContent(j, data);
            cell.classList.add(this.className);

            if(j === this.rowManager.getSize() - 1)
            {
                cell.classList.add(`${this.className}-cell__last-column`);
            }

            if(isLastRow)
            {
                cell.classList.add(`${this.className}-cell__last-row`);
            }

            row.appendChild(cell);
        }

        return row;
    }
    
    toggleRow(index, show)
    {
        this.rows[index].classList.toggle("display__none", !show);
    }
}