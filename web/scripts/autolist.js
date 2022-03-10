class AutoList
{
    constructor(displayType)
    {
        this.container = document.createElement("div");
        this.items = [];
        this.contentDisplay = displayType
    }

    addItem(content, object)
    {
        this.items.push(new AutoListItem(content, object, this.contentDisplay));
    }

    setFilterMethod(filterMethod)
    {
        this.filterMethod = filterMethod;
    }

    setSortMethod(sortMethod)
    {
        this.sortMethod = sortMethod;
    }

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
    constructor(content, object, displayType)
    {
        this.content = content;
        this.object = object;
        this.lineBreak = document.createElement("hr");
        this.contentDisplay = displayType;
    }

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