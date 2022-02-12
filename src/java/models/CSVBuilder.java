package models;

public class CSVBuilder
{
    /**
     * The delimiter to use in the CSV table
     */
    private
    char delimiter = ',';
    
    /**
     * The StringBuilder used to build the CSV table
     */
    private
    StringBuilder builder = new StringBuilder();
    
    /**
     * Constructor for CSVBuilder.
     */
    public CSVBuilder()
    {
        ;
    }
    
    /**
     * Constructor for CSVBuilder. Takes a delimiter character as a parameter.
     * @param delimeter The delimiter to use in the CSV table
     */
    public CSVBuilder(char delimeter)
    {
        this.delimiter = delimeter;
    }
    
    /**
     * Adds a new record to the CSV table
     * @param data The data for every field in a record. Uses toString().
     */
    public void addRecord(Object[] data)
    {
        int i;
        for(i=0; i<data.length-1; i++)
        {
            addField(data[i]);
            newField();
        }
        addField(data[i]);
        
        newRecord();
    }
    
    /**
     * Adds a single field to the current record.
     * @param obj The field's data. Uses toString().
     */
    public void addField(Object obj)
    {
        builder.append('"');
        builder.append(obj == null ? "" : obj.toString());
        builder.append('"');
    }
    
    /**
     * Adds a delimiter character to start a new field
     */
    public void newField()
    {
        builder.append(delimiter);
    }
    
    /**
     * Moves the cursor to the next line.
     */
    public void newRecord()
    {
        builder.append('\n');
    }
    
    /**
     * Clears the CSV table
     */
    public void clearBuffer()
    {
        builder.setLength(0);
    }
    
    /**
     * Sets the field delimiter character
     * @param delimeter The character to delimit the CSV table by
     */
    public void setDelimiter(char delimeter)
    {
        this.delimiter = delimeter;
    }
    
    /**
     * Prints the entire CSV table currently in the buffer
     * @return the entire CSV table currently in the buffer
     */
    public String printFile()
    {
        return builder.toString();
    }
}