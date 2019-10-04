package ass2;

import java.io.IOException;
import java.util.ArrayList;

public class Game
{
    private String playerName;
    private int gameTotal;
    private Buffer buffer;
    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_ERROR = 1;
    private static final int MAX_TOTAL_NUMBER = 255;

    public Game()
    {
        playerName = "";
        gameTotal = 0;
        buffer = new Buffer();
    }

    public void startGame()
    {
        ArrayList<Integer> randomMultiples = getRandomMultiples();
        if (randomMultiples == null || randomMultiples.isEmpty())
        {
            System.out.println("read multiple file error");
            return;
        }
        RNG rng = new RNG(0, randomMultiples.size());
        while (!isHasAValidUserName(this.playerName))
        {
            trySetUserName(Input.acceptUserInput("please input a user name"));
        }
        System.out.println("Welcome " + playerName + "!!!!");
        while (isContinuePlay())
        {
            gameTotal += randomMultiples.get(rng.getAValidIndex());
            int userInput;
            do
            {
                userInput = Input.acceptAValidNumericInput("please select a option");
            } while (userInput == Input.ERROR_INPUT);
            switch (userInput)
            {
                case 1:
                    split(gameTotal);
                    break;
                case 2:
                    merge();
                    break;
                default:
                    break;
            }
        }
        System.exit(EXIT_SUCCESS);
    }

    private void merge()
    {
        for (Multiple multiple : buffer.getList())
        {
            System.out.println("value: " + multiple.getValue());
        }
        int option;
        do
        {
            option = Input.acceptAValidNumericInput("please select a buffer index");
        } while (option <= 0 || option > buffer.getList().size());

    }

    private void split(int gameTotal)
    {
        buffer.addMultiPleToBuffer(gameTotal);
    }

    private ArrayList<Integer> getRandomMultiples()
    {
        try
        {
            return Input.readMultipleFromFiles();
        } catch (IOException e)
        {
            System.out.println("read multiple file error");
            e.printStackTrace();
            System.exit(EXIT_ERROR);
        }
        return null;
    }

    private void trySetUserName(String userInputName)
    {
        if (isHasAValidUserName(userInputName))
        {
            playerName = userInputName;
        } else
        {
            System.out.println("the input username:" + userInputName + " is illegal, please try again!");
        }
    }

    private boolean isHasAValidUserName(String playerName)
    {
        return !(playerName == null || playerName.trim().isEmpty() || playerName.length() < 3 || playerName.length() > 10);
    }

    private boolean isContinuePlay()
    {
        return !(buffer.isOverFlow() || gameTotal >= MAX_TOTAL_NUMBER);
    }
}
