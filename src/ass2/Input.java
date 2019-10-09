package ass2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Input Class which creates a scanner object and accept input from the user
 * in different formats
 *
 * @author Kai.Sun
 * @version 9/09/2019
 */
public class Input
{

    private static final String MULTIPLE_TEXT_FILE_NAME = "C:\\Users\\sunkai\\Documents\\GitHub\\Ass2\\src\\ass2\\multiples.txt";
    private static final String SAVE_FILE_NAME="outcome.txt";
    public static final int ERROR_INPUT = Integer.MIN_VALUE;

    /**
     * This method response for clear the console display by printing many empty lines
     */
    public static void clearDisplay()
    {
        for (int i = 0; i < 20; i++)
        {
            System.out.println();
        }
    }

    public static ArrayList<Integer> readMultipleFromFiles() throws IOException, NumberFormatException
    {
        try (FileReader fileReader = new FileReader(MULTIPLE_TEXT_FILE_NAME))
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

    public static void writeFinalResultToFile(String writeInformation)throws IOException
    {
        try(PrintWriter printWriter=new PrintWriter(SAVE_FILE_NAME))
        {
            printWriter.println(writeInformation);
        }
    }

    /**
     * Method which displays a message to the user to enter an DOUBLE input
     * which is read from the keyboard using the Scanner object and then passed
     * to the calling method
     *
     * @param displayMessage A string message telling the user what input is expected
     * @return A single double value which is entered by the user
     */
    public static String acceptUserInput(String displayMessage)
    {
        System.out.println(displayMessage);
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        scanner.close();
        return userInput.trim();
    }

    private static boolean checkUserInputIsNumeric(String userInput)
    {
        if (userInput == null || userInput.length() == 0)
        {
            return false;
        }
        //check the number inputted by the user is correct
        //if it contains any character which not belongs to number, then return false
        for (int i = 0; i < userInput.length(); i++)
        {
            if (!Character.isDigit(userInput.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Get a integer from the user input, this method would convert the string to a int.
     * if the has inputted any incorrect string, it will return Integer.MIN_VALUE
     *
     * @param displayMessage
     * @return A valid integer which is converted by the string that is inputted by the user, or an Integer.MIN_VALUE value if the user has inputted a wrong string.
     */
    public static int acceptAValidNumericInput(String displayMessage)
    {
        String userInput = acceptUserInput(displayMessage);
        int result = ERROR_INPUT;
        if (checkUserInputIsNumeric(userInput))
        {
            result = Integer.parseInt(userInput);
        }
        return result;
    }

    /**
     * This method response for output many empty lines in the console
     *
     * @param number a number of how many lines would the console will output
     */
    public static void displayManyLines(int number)
    {
        int actuallyNumber;
        if (number < 0)
            actuallyNumber = 0;
        if (number > 20) //meaningless line number
            actuallyNumber = 20;
        actuallyNumber = number;
        for (int i = 0; i < actuallyNumber; i++)
        {
            System.out.println();
        }
    }

}
