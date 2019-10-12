package ass2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileIO
{
    private final String filename;

    public FileIO()
    {
        this("unknown");
    }

    public FileIO(String newInputFilename)
    {
        filename = newInputFilename;
    }

    public String readMultipleFromFiles() throws IOException, NumberFormatException
    {
        String result = "";
        try
        {

            FileReader fileReader = new FileReader(filename);
            try
            {
                StringBuffer stringBuffer = new StringBuffer();
                Scanner scanner = new Scanner(fileReader);
                while (scanner.hasNextLine())
                {
                    stringBuffer.append(scanner.nextLine()).append("\n");
                }
                stringBuffer.delete(stringBuffer.length()-1,stringBuffer.length());
                result = stringBuffer.toString();
            } finally
            {
                fileReader.close();
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("There is no such file called " + filename);
        } catch (IOException e)
        {
            System.out.println("read " + filename + " error!!!");
        } catch (NumberFormatException e)
        {
            System.out.println("content in file " + filename + " is error");
        }
        return result;
    }

    public void writeContentToFile(String writeInformation)
    {
        try
        {
            PrintWriter printWriter = new PrintWriter(filename);
            try
            {
                printWriter.println(writeInformation);
            } finally
            {
                printWriter.close();
            }
        } catch (Exception e)
        {
            System.out.println("cannot write to file called " + filename);
        }
    }
}
