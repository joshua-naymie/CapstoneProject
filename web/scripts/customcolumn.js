class CustomColumn
{
    constructor(header, className, contentGenerator)
    {
        this.header = header;
        this.className = className;
        this.contentGenerator = contentGenerator;
    }

    generateContent(entity, isLast)
    {
        let cell = document.createElement("div");
        cell.classList.add(this.className);
        if(isLast)
        {
            cell.classList.add("last-column");
        }

        let content = this.contentGenerator(entity);
        content.classList.add(`${this.className}-content`);

        cell.appendChild(content);
        

        return cell;
    }

    setClassName(className)
    {
        this.className = className;
    }
}