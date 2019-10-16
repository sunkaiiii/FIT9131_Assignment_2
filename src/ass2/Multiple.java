package ass2;

/**
 * Multiple stores the value for a game.
 */
public class Multiple
{

    private int value;

    /**
     * Non-default constructor
     * @param newValue the initial value of the multiple object
     */
    public Multiple(int newValue)
    {
        this.value = newValue;
    }

    /**
     * The mutator of the value
     * @param newValue The new value
     */
    public void setValue(int newValue)
    {
        this.value = newValue;
    }


    /**
     * The accessor of the value field
     * @return the value of the value field
     */
    public int getValue()
    {
        return value;
    }

    public String displayMultiple()
    {
        return "Value: "+value;
    }

}
