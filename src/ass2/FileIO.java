package ass2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The FileIO class responses for reading or writing information from or to a file.
 *
 * @author Kai.Sun
 * @version 14/10/2019
 */

public class FileIO
{
    private final String filename;

    /**
     * Default constructor
     */
    public FileIO()
    {
        this("unknown");
    }

    /**
     * Non-default constructor
     * @param newInputFilename the name of the file that wants the FileIO object  to use
     */
    public FileIO(String newInputFilename)
    {
        filename = newInputFilename;
    }

    /**
     * The accessor of the file name
     * @return the filename
     */
    public String getFilename()
    {
        return filename;
    }

    /**
     * Read the content in the file as a String object.
     * @return the content in the file, if errors are happened, return an empty String
     */
    public String readMultipleFromFiles()
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

    /**
     * Write information to the file
     * @param writeInformation the content that wants the FileIO object to write
     */
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
