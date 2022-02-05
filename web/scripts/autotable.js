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
        this.columns = new RowManager(this.className, columnMaps);
    }

    /**
     *  Adds a DataColumn or CustomColumn to the table
     * @param {DataColumn | CustomColumn} column    The column to add to the table
     * @param {number} position                     The position to insert the column into.
     *                                              Added to the end if null.
     */
    addColumn(column, position)
    {
        this.columns.addColumn(column, position);
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
        
        let row = document.createElement("div");
        row.classList.add(`${this.className}-row`);

        for(let i=0; i<this.columns.getSize(); i++)
        {
            let cell = document.createElement("div");
            cell.classList.add(`${this.className}`);
            cell.classList.add(`${this.className}-cell`);
            cell.classList.add(`${this.className}-header`);
            cell.style.alignItems = "center";
            cell.style.margin = "0";
            if(i === this.columns.getSize()-1)
            {
                cell.classList.add(`${this.className}-cell__last-column`);
            }

            let content = document.createElement("p");
            content.classList.add(`${this.className}-content`);
            content.style.margin = "10px 0 10px 0";
            
            
            content.innerText = this.columns.columns[i].header;

            cell.appendChild(content);
            row.appendChild(cell);
        }

        this.container.appendChild(row);
    }

    /**
     * Generates the content for the table using provided data
     */
    generateContent()
    {
        for(let i=0; i<this.data.length; i++)
        {
            this.container.appendChild(i === this.data.length-1 ? this.getRowContent(data[i], true) : this.getRowContent(data[i]));
        }
    }

    getRowContent(data, isLastRow)
    {
        let row = document.createElement("div");
            row.classList.add(`${this.className}-row`);
    
        for(let j=0; j<this.columns.getSize(); j++)
        {
            let cell = this.columns.generateContent(j, data);
            cell.classList.add(this.className);

            if(j === this.columns.getSize() - 1)
            {
                cell.classList.add(`${this.className}-cell__last-column`);
            }

            if(isLastRow)
            {
                cell.classList.add(`${this.className}-cell__last-row`);
            }

            row.appendChild(cell);
        }

        return row
    }
}