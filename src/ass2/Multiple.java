package ass2;

/**
 * Multiple stores the value for a game.
 *
 * @author Kai.Sun
 * @version 26/10/2019
 */
public class Multiple
{

    private int value;

    /**
     * Non-default constructor
     *
     * @param newValue the initial value of the multiple object
     */
    public Multiple(int newValue) throws Exception
    {
        if (newValue <= 0)
            throw new Exception("The value of a multiple object cannot be less 0");
        value = newValue;
    }

    /**
     * The mutator of the value
     *
     * @param newValue The new value
     */
    public void setValue(int newValue) throws Exception
    {
        if (newValue <= 0)
            throw new Exception("The value of a multiple object cannot be less 0");
        this.value = newValue;
    }


    /**
     * The accessor of the value field
     *
     * @return the value of the value field
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Dispaly the information of this multiple.
     *
     * @return A string contained the information of the object.
     */
    public String displayMultiple()
    {
        return "Value: " + value;
    }

}
