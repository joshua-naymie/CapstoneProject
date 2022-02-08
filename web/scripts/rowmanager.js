class RowManager
{
    /**
     * 
     * @param {string} className                            The CSS class name to be applied to the columns
     * @param {Array<DataColumn | CustomColumn>} columnMaps An array of DataColumns/CustomColumns
     */
    constructor(className, columnMaps)
    {
        this.columns = [];
        this.className = className;

        if(columnMaps != null)
        {
            columnMaps.forEach(column => {
                this.addColumn(column);
            });
        }
    }

    /**
     * Adds a column to the table
     * @param {DataColumn | CustomColumn} column The column to add to the table
     * @param {number} position The position to insert the column into.
     *                          Added to end if position is null.
     */
    addColumn(column, position)
    {
//        column.setClassName(this.className);
        if(typeof position !== "number")
        {
            this.columns.push(column);
        }
        else
        {
            this.columns.splice(position, 0, column);
        }
    }

    /**
     * Gets the header of each column in the table.
     * @returns {string[]} All column headers in order
     */
    getHeaders()
    {
        let headers = [];
        this.columns.forEach(column => {
            headers.push(column.header);
        });

        return headers;
    }

    /**
     * Gets the number of columns in the table
     * @returns {number} The number of columns in the table
     */
    getSize()
    {
        return this.columns.length;
    }

    /**
     * Gets the key name for the column at the specefied index
     * @param {number} index the index of the column
     * @returns {string} The key name of the column
     */
    getKeyName(index)
    {
        return this.columns[index].keyName;
    }

    generateHeader()
    {
        let row = document.createElement("div");
//        row.classList.add(`${this.className}-row`);
        row.classList.add(this.className);
        
        let i;
        for(i=0; i<this.columns.length; i++)
        {
            let cell = document.createElement("div");
            cell.classList.add(`${this.className}-header`);
            
            if(i === this.getSize()-1)
            {
                cell.classList.add("last-column");
            }

            let content = document.createElement("p");
            content.classList.add(`${this.className}-content`);
            content.innerText = this.columns[i].header;

            cell.appendChild(content);
            row.appendChild(cell);
        }
        
        return row;
    }

    /**
     * Generates the content for the specefied column based off the given element
     * @param {number} index The index of the column to generate content for
     * @param {*} entity     The entity to generate content from
     * @returns The generated content
     */
    generateContent(index, entity, isLast)
    {
        let row = document.createElement("div");
        row.classList.add(this.className);
        let i;
        for(i=0; i<this.columns.length-1; i++)
        {
            row.appendChild(this.columns[i].generateContent(entity));
        }
        row.appendChild(this.columns[i].generateContent(entity, true));
        return row;
    }
}