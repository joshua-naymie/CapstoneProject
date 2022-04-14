class AutoList
{
    /**
     * Constructor for AutoList. Specify the type of display the ListItems will use.
     * @param {string} displayType The type of display the ListItem will use | eg. 'flex'
     * @returns {AutoList}
     */
    constructor(displayType)
    {
        this.container = document.createElement("div");
        this.items = [];
        this.contentDisplay = displayType
    }

    /**
     * Adds an AutoListItem to the AutoList.
     * @param {type} content The HTML content to add to the list.
     * @param {type} object The JSON Object associated with the content.
     */
    addItem(content, object)
    {
        this.items.push(new AutoListItem(content, object, this.contentDisplay));
    }

    /**
     * Sets the method used to filter the AutoListItems.
     * @param {method} filterMethod The method used to filter the AutoListItems.
     */
    setFilterMethod(filterMethod)
    {
        this.filterMethod = filterMethod;
    }

    /**
     * Sets the method used to sort the AutoListItems.
     * @param {method} sortMethod The method used to sort the AutoListItems.
     */
    setSortMethod(sortMethod)
    {
        this.sortMethod = sortMethod;
    }

    /**
     * Filters the AutoListItems based on the filter value and the filter method
     * @param {type} filterValue The value to pass to the filter method.
     */
    filter(filterValue)
    {
        filterValue = filterValue == null ? "" : filterValue;
        
        let firstItem = true;
        for(let i=0; i<this.items.length; i++)
        {
            if(this.filterMethod(this.items[i].object, filterValue))
            {
                this.items[i].setItemVisibility(true);
                if(firstItem)
                {
                    this.items[i].setLineBreakVisibility(false);
                    firstItem = false;
                }
            }
            else
            {
                this.items[i].setItemVisibility(false);
            }
        }
    }

    /**
     * Sorts the AutoListItems based on the sort method.
     */
    sort()
    {
        if(this.sortMethod !== null)
        {
            this.items.sort(this.sortMethod);
        }
        else
        {
            this.items.sort();
        }
    }

    /**
     * Generates the list of AutoListItems
     */
    generateList()
    {
        this.sort();
        removeAllChildren(this.container);

        let temp = new DocumentFragment();
        this.items.forEach(item => {
            temp.appendChild(item.lineBreak);
            temp.appendChild(item.content);
        });

        this.container.appendChild(temp);
        this.filter("");
    }
}

class AutoListItem
{
    /**
     * Constructor for AutoListItem.
     * @param {type} content The HTML content of the AutoListItem
     * @param {type} object The JSON object associated with the AutoListItem
     * @param {type} displayType The type of display the HTML content uses. | eg. 'flex'
     * @returns {AutoListItem}
     */
    constructor(content, object, displayType)
    {
        this.content = content;
        this.object = object;
        this.lineBreak = document.createElement("hr");
        this.contentDisplay = displayType;
    }

    /**
     * Sets the AutoListItem's display to hide or show the AutoListItem.
     * Uses 'none' to hide the AutoListItem and the provided displayType to show it.
     * @param {boolean} isVisible Whether the AutoListItem will be visible or not.
     */
    setItemVisibility(isVisible)
    {
        if(isVisible)
        {
            this.content.style.display = this.contentDisplay;
            this.lineBreak.style.display = "block";
        }
        else
        {
            this.content.style.display = "none";
            this.lineBreak.style.display = "none";
        }
    }

    /**
     * Sets the line break associated with the AutoListItem's visibility.
     * @param {boolean} isVisible Whether the line break is visible or not.
     * @returns {undefined}
     */
    setLineBreakVisibility(isVisible)
    {
        if(isVisible)
        {
            this.lineBreak.style.display = this.contentDisplay;
        }
        else
        {
            this.lineBreak.style.display = "none";
        }
    }
}