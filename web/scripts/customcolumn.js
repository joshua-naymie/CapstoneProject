class CustomColumn
{
    constructor(header, contentGenerator)
    {
        this.header = header;
        this.contentGenerator = contentGenerator;
    }

    generateContent(element)
    {
        let cell = document.createElement("div");
        cell.classList.add(`${this.className}-cell`);

        let content = this.contentGenerator(element);
        content.classList.add(`${this.className}-content`);

        cell.appendChild(content);
        

        return cell;
    }

    setClassName(className)
    {
        this.className = className;
    }
}