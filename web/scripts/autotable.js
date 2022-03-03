class AutoTable
{
    /**
     * Constructor for AutoTable
     * @param {string} className The CSS class structure to give the table
     * @param {Array<object>} data The data to populate the table with
     * @param {Array<DataColumn | CustomColumn>} columnMaps The columns of data to display
     */
    constructor(className, data, columnMaps, showHeader)
    {
        this.container = document.createElement("div");
        this.container.classList.add(className);
        
        this.className = className;
        this.data = data;
        this.rowManager = new RowManager(`${this.className}-row`, columnMaps);
        this.showHeader = showHeader == null ? true : showHeader;
        
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
        if(this.showHeader)
        {
            this.generateHeader();
        }
        else
        {
            this.container.style.borderTop = "1px solid black";
        }
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
            let row = i === this.data.length-1 ? this.rowManager.generateContent(this.data[i]) : this.rowManager.generateContent(this.data[i], true);
            this.rows.push(row);
            
            
            this.container.appendChild(row);   //change
        }
    }
    
    toggleRow(index, show)
    {
        this.rows[index].classList.toggle("display__none", !show);
    }
}