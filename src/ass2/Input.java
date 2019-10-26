package ass2;

import java.util.Scanner;

/**
 * Input Class which creates a scanner object and accept input from the user
 * in different formats
 *
 * @author Kai.Sun
 * @version 14/10/2019
 */
public class Input
{

    /**
     * The default constructor
     */
    public Input()
    {
    }

    /**
     * Method which displays a message to the user to enter an DOUBLE input
     * which is read from the keyboard using the Scanner object and then passed
     * to the calling method
     *
     * @param displayMessage A string message telling the user what input is expected
     * @return A single double value which is entered by the user
     */
    public String acceptUserInput(String displayMessage)
    {
        System.out.println(displayMessage);
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        return userInput.trim();
    }

    /**
     * Get a integer from the user input
     *
     * @param displayMessage The notification that the user knows
     * @return An integer
     * @throws NumberFormatException if the user inputs wrong characters which not belong to a number. Throw a NumberFormatException.
     */
    public int acceptNumericInput(String displayMessage) throws NumberFormatException
    {
        System.out.println(displayMessage);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

}
