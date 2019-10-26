package ass2;

import java.util.ArrayList;

/**
 * The buffer class stores the multiple objects for a game
 *
 * @author Kai.Sun
 * @version 23/10/2019
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

    /**
     * init the buffer, drop all elements in the buffer
     */
    public void initBufferValues()
    {
        multiples.clear();
    }

    /**
     * try to add an element to the buffer
     *
     * @param newMultiple new multiple need to be added
     * @return true is the element has been added correctly, false is adding error.
     */
    public boolean addMultipleToBuffer(Multiple newMultiple)
    {
        boolean result = false;
        if (newMultiple != null && getMultiples().size() < maxElements)
        {
            multiples.add(newMultiple);
            result = true;
        }
        return result;
    }

    /**
     * remove a current exist multiple object from the buffer
     *
     * @param existMultiple the object of the exist multiple
     * @return true is the removing action success, false is the removing error.
     */
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

    /**
     * Display the whole information in the ArrayList
     *
     * @return A string contains all information in the ArrayList
     */
    public String displayBuffer()
    {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < multiples.size(); i++)
        {
            stringBuffer.append(displayBuffer(i));
        }
        return stringBuffer.toString();
    }

    /**
     * Display the specific information of the buffer in the ArrayList
     *
     * @param index the index of the current ArrayList in the Buffer.
     * @return the information in the specific index of the Buffer if the index is correct, otherwise, return Unknown
     */
    public String displayBuffer(int index)
    {
        final String result;
        if (index >= multiples.size() || index < 0)
        {
            result = "Unknown";
        } else
        {
            result = multiples.get(index).displayMultiple() + " ";
        }
        return result;
    }

    /**
     * Get the current remain capacity of the Buffer
     *
     * @return the number of the remain available capacity.
     */
    public int getRemainCapacity()
    {
        int remainCapacity = getMaxElements() - getMultiples().size();
        if (remainCapacity < 0)
        {
            remainCapacity = 0;
        }
        return getMaxElements() - getMultiples().size();
    }

    /**
     * Get the current size of the buffer
     *
     * @return the number of the current size of the buffer
     */
    public int getCurrentSize()
    {
        return getMultiples().size();
    }
}
