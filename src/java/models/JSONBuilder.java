package models;

import java.util.ArrayList;
import java.util.Arrays;
import javax.management.openmbean.InvalidKeyException;

public class JSONBuilder
{
    /**
     * The keys for the JSON object
     */
    private
    ArrayList<JSONKey> keys;

    private
    StringBuilder builder = new StringBuilder();
    
    /**
     * Constructor with no keys added
     */
    public JSONBuilder()
    {
        this.keys = new ArrayList<JSONKey>();
    }

    /**
     * Constructor with keys provided
     * @param keys They JSONKeys to add to the JSONBuilder
     */
    public JSONBuilder(JSONKey[] keys)
    {
        this.keys = new ArrayList<JSONKey>(Arrays.asList(keys));
    }

    /**
     * Adds a JSONKey to the JSONBuilder
     * @param key The JSONKey to add
     */
    public void addKey(JSONKey key)
    {
        this.keys.add(key);
    }

    /**
     * Builds a JSON object using the keys already added and the values provided
     * @param values The values to pair with the JSON keys. Must be in the same order as JSONKeys
     * @return A JSON object in String format.
     */
    public String buildJSON(Object[] values)
    {
        // Amount of keys and values must match
        if(values.length != keys.size())
        {
            throw new IllegalArgumentException("number of values '" + values.length + "' "
                                             + "does not match keys size '" + keys.size() + "'");
        }
        // Keys size must not be 0
        else if(keys.size() == 0)
        {
            throw new InvalidKeyException("Cannot create JSON object with 0 keys");
        }

        // Build the JSON object
        
        builder.append('{');

        int i;
        for(i=0; i<keys.size()-1; i++)
        {
            builder.append(keys.get(i).getKeyValue(values[i]));
            builder.append(',');
        }
        builder.append(keys.get(i).getKeyValue(values[i]));
        builder.append('}');

        // Clear buffer
        String temp = builder.toString();
        builder.setLength(0);
        
        return temp;
    }
}