package ass2;

/**
 * Write a description of class RNG here.
 *
 * @author Kai.Sun
 * @version 13/09/2019
 */
public class RNG
{
    private int maximumValue;
    private int minimumValue;

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
