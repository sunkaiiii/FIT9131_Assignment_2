package ass2;

import java.util.ArrayList;

/**
 * The buffer class stores the multiple objects for a game
 *
 * @author Kai.Sun
 * @version 14/10/2019
 */
public class Buffer
{
    private ArrayList<Multiple> multiples;
    private int maxElements;

    /**
     * default constructor
     */
    public Buffer()
    {
        this(5);
    }

    /**
     * Non-default constructor
     *
     * @param newMaxElement the max number of elements of the buffer
     */
    public Buffer(int newMaxElement)
    {
        maxElements = newMaxElement;
        multiples = new ArrayList<>();
    }

    /**
     * Judge whether the buffer is full or not.
     *
     * @return true means the buffer is full, false is not full.
     */
    public boolean isFull()
    {
        return multiples.size() >= getMaxElements();
    }

    public boolean isEmpty()
    {
        return multiples.isEmpty();
    }

    /**
     * The accessor of the multiple list
     *
     * @return the list of multiples
     */
    public ArrayList<Multiple> getMultiples()
    {
        return multiples;
    }

    public void initBufferValues()
    {
        multiples.clear();
    }

    public boolean addMultipleToBuffer(Multiple newMultiple)
    {
        boolean result = false;
        if (multiples.size() < maxElements)
        {
            multiples.add(newMultiple);
            result = true;
        }
        return result;
    }

    public boolean removeMultipleFromBuffer(Multiple existMultiple)
    {
        return multiples.remove(existMultiple);
    }

    /**
     * The mutator of the multiple list
     *
     * @param newMultiples the new value of the multiple list.
     */
    public void setMultiples(ArrayList<Multiple> newMultiples)
    {
        this.multiples = newMultiples;
    }

    /**
     * The accessor of the value of the max element
     *
     * @return the value of the max elements that the buffer can contain.
     */
    public int getMaxElements()
    {
        return maxElements;
    }

    /**
     * The mutator of the value of the max element.
     *
     * @param newMaxElements new value of the max element.
     */
    public void setMaxElements(int newMaxElements)
    {
        this.maxElements = newMaxElements;
    }

    public String displayBuffer()
    {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < multiples.size(); i++)
        {
            stringBuffer.append(displayBuffer(i));
        }
        return stringBuffer.toString();
    }

    public String displayBuffer(int index)
    {
        return "Index: " + (index + 1) + multiples.get(index).displayMultiple()+" ";
    }
}
