package ass2;

import java.util.ArrayList;

public class Buffer
{
    private ArrayList<Multiple> multiples;
    private int maxElements;
    private static final int MAX_NUMBER = 5;

    public Buffer()
    {
        maxElements = MAX_NUMBER;
        multiples = new ArrayList<>();
    }

    public void addMultiPleToBuffer(int value)
    {
        multiples.add(new Multiple(value));
    }

    public boolean isOverFlow()
    {
        return multiples.size() > MAX_NUMBER;
    }

    public ArrayList<Multiple> getList()
    {
        return multiples;
    }
}
