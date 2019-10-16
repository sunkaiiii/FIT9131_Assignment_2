package ass2;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO
{
    private String fileName;

    public FileIO()
    {
        this("known");
    }

    public FileIO(String newFileName)
    {
        fileName = newFileName;
    }

    public ArrayList<Integer> readMultipleFromFiles() throws IOException, NumberFormatException
    {
        try (FileReader fileReader = new FileReader(fileName))
        {
            Scanner scanner = new Scanner(fileReader);
            String[] numberArray = scanner.nextLine().split(",");
            ArrayList<Integer> result = new ArrayList<>();
            for (String singleNum : numberArray)
            {
                result.add(Integer.parseInt(singleNum));
            }
            return result;
        }
    }

    public void writeFinalResultToFile(String writeInformation)throws IOException
    {
        try(PrintWriter printWriter=new PrintWriter(fileName))
        {
            printWriter.println(writeInformation);
        }
    }
}
