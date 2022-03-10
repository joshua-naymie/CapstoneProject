class DataColumn
{
    /**
     * Constructor for DataColumn. Custom content generating function is optional. Basic text is the default content.
     * @param {string} header               The name of the column displayed in the header 
     * @param {string} keyName              They key name of the data to display in the column
     * @param {Function} contentGenerator   A function to create custom content. Must return an appendable HTML object. Optional
     */
    constructor(header, keyName, className, contentGenerator)
    {
        if(typeof header !== "string")
        {
            throw `header is not a string - ${header}`;
        }
        if(typeof keyName !== "string")
        {
            throw "objectName is not a string!";
        }

        this.header = header;
        this.keyName = keyName;
        this.className = className;

        this.contentGenerator = contentGenerator == null ? this.generateContentText : contentGenerator;
    }

    /**
     * 
     * @param {object} element 
     * @returns An HTML div with the cell content appended to it
     */
    generateContent(element)
    {
        let cell = document.createElement("div");
        cell.classList.add(this.className);
        
        let content = this.contentGenerator(element[this.keyName]);
//        content.classList.add(`${this.className}-content`);

        cell.appendChild(content);
        
        
        return cell;
    }

    /**
     * The default function for generating content. Generates a cell with basic text as content.
     * @param {*} value The value to be displayed as text
     * @returns An HTML div with the cell text inside of it
     */
    generateContentText(value)
    {
        let content = document.createElement("p");
        content.innerText = value;

        return content;
    }

    /**
     * Sets the CSS class name that will be applied to the cell
     * @param {string} className The name of the CSS class to apply to the cell 
     */
    setClassName(className)
    {
        this.className = className;
    }
}