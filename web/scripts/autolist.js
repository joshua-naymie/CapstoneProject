class AutoList
{
    constructor()
    {
        this.container = document.createElement("div");
        this.items = [];
        // this.contentDisplay = "block";
    }

    // setContentDisplay(display)
    // {
    //     this.contentDisplay = display;
    // }

    addItem(content, object)
    {
        this.items.push(new AutoListItem(content, object));
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
        if(this.sortMethod)
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
        removeAllChildren(this.items);

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
    constructor(content, object)
    {
        this.content = content;
        this.object = object;
        this.lineBreak = document.createElement("hr");
    }

    setItemVisibility(isVisible)
    {
        if(isVisible)
        {
            this.content.style.display = "flex";
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
            this.lineBreak.style.display = "block";
        }
        else
        {
            this.lineBreak.style.display = "none";
        }
    }
}