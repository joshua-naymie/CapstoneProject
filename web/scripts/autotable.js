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
        
//        this.headerdiv = document.createElement("div");     //change
//        this.container.appendChild(this.headerdiv);         //change
//        this.contentdiv = document.createElement("div");    //change
//        this.contentdiv.style.overflowY = "scroll";         //change
//        this.contentdiv.style.height = "80vh";              //change
//        this.contentdiv.classList.add("test");              //change
//        this.container.appendChild(this.contentdiv);        //change
        
        this.className = className;
        this.data = data;
        this.columns = new RowManager(`${this.className}-row`, columnMaps);
        
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
//        let row = document.createElement("div");
//        row.classList.add(`${this.className}-row`);//table-row-header
//
//        for(let i=0; i<this.columns.getSize(); i++)
//        {
//            let cell = document.createElement("div");
//            cell.classList.add(`${this.className}-header`);
//            
//            if(i === this.columns.getSize()-1)
//            {
//                cell.classList.add(`${this.className}-cell__last-column`);
//            }
//
//            let content = document.createElement("p");
//            content.classList.add(`${this.className}-content`);
//            content.innerText = this.columns.columns[i].header;
//
//            cell.appendChild(content);
//            row.appendChild(cell);
//        }
        this.container.appendChild(this.columns.generateHeader());  //change
    }

    /**
     * Generates the content for the table using provided data
     */
    generateContent()
    {
        for(let i=0; i<this.data.length; i++)
        {
            let row = i === this.data.length-1 ? this.columns.generateContent(i, data[i]) : this.columns.generateContent(i, data[i], true);
            this.rows.push(row);
            
            
            this.container.appendChild(row);   //change
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

        return row;
    }
    
    toggleRow(index, show)
    {
        this.rows[index].classList.toggle("display__none", !show);
    }
}