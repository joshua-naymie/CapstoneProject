package models;

public final class JSONKey
{
    /**
     * The key name to pair with a value
     */
    private final
    String keyName;

    /**
     * Whether the value needs quotes. ex: number or string
     */
    private
    boolean hasQuotes; 

    /**
     * The string builder to construct the key value pair
     */
    private
    StringBuilder builder = new StringBuilder();
    
    /**
     * Constructor for JSONKey. Sets the key name and whether value needs quotes
     * @param keyName The key name to pair with a value
     * @param hasQuotes Whether the value needs quotes. ex: number or string
     */
    public JSONKey(String keyName, boolean hasQuotes)
    {
        this.keyName = keyName;
        this.hasQuotes = hasQuotes;
    }

    /**
     * Gets the key value pair in JSON format
     * @param value The Object to use as the value
     * @return The key value pair in JSON format | Format: "keyName":"value"
     */
    public String getKeyValue(Object value)
    {
        // Write key name
        builder.append('"');
        builder.append(keyName);
        builder.append("\":");

        // Handle null values
        if(value == null)
        {
            builder.append("null");
            return builder.toString();
        }

        // Write value
        if(hasQuotes)
        {
            builder.append('"');
            builder.append(value);
            builder.append('"');
        }
        else
        {
            builder.append(value);
        }
        
        // Clear buffer
        String temp = builder.toString();
        builder.setLength(0);
        
        return temp;
    }
}