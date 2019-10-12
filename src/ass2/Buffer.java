package ass2;

import java.util.ArrayList;

public class Buffer
{
    private ArrayList<Multiple> multiples;
    private int maxElements;

    public Buffer()
    {
        this(5);
    }

    public Buffer(int newMaxElement)
    {
        maxElements = newMaxElement;
        multiples = new ArrayList<>();
    }

    public void addMultiPleToBuffer(int value)
    {
        multiples.add(new Multiple(value));
    }

    public boolean isOverFlow()
    {
        return multiples.size() > maxElements;
    }

    public ArrayList<Multiple> getList()
    {
        return multiples;
    }
}
