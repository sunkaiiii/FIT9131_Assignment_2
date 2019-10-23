package ass2;

/**
 * RNG class responses for generating random numbers in a specific range.
 *
 * @author Kai.Sun
 * @version 23/10/2019
 */
public class RNG
{
    private int maximumValue;
    private int minimumValue;

    /**
     * default constructor.
     */
    public RNG()
    {
        this(0,1);
    }
    /**
     * non-default constructor
     *
     * @param minValue the lowest value
     * @param maxValue the highest value
     */
    public RNG(int minValue, int maxValue)
    {
        this.minimumValue = minValue;
        this.maximumValue = maxValue;
    }

    /**
     * The accessor of the max value
     * @return the max value
     */
    public int getMaximumValue()
    {
        return maximumValue;
    }

    /**
     * The mutator of the max value
     * @param newMaximumValue The new max value
     */
    public void setMaximumValue(int newMaximumValue)
    {
        this.maximumValue = newMaximumValue;
    }

    /**
     * The accessor of the min value
     * @return The min value
     */
    public int getMinimumValue()
    {
        return minimumValue;
    }

    /**
     * The mutator of the min value
     * @param newMinimumValue The new min value
     */
    public void setMinimumValue(int newMinimumValue)
    {
        this.minimumValue = newMinimumValue;
    }

    /**
     * Get a random index depended on the fields.
     * @return return a int between the min number and max number.
     */
    public int getAValidIndex()
    {
        int result;
        do
        {
            result = (int) (Math.random() * maximumValue);
        } while (!(result >= minimumValue && result <= maximumValue));
        return result;
    }
}
