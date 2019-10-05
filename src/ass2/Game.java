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
            showBufferList();
            gameTotal += randomMultiples.get(rng.getAValidIndex());
            System.out.println("this system has Randomly chosen a multiple");
            System.out.println("Now the game total is " + gameTotal);
            int userInput;
            boolean isCorrectAction = false;
            do
            {
                System.out.println("1.split\n2.merge");
                userInput = Input.acceptAValidNumericInput("please select a option");
                switch (userInput)
                {
                    case 1:
                        isCorrectAction = split(gameTotal);
                        break;
                    case 2:
                        isCorrectAction = merge(gameTotal);
                        break;
                    default:
                        System.out.println("you must chosen a number between 1 and 2!");
                        break;
                }
            } while (!isCorrectAction);
        }
        System.exit(EXIT_SUCCESS);
    }

    private boolean merge(int gameTotal)
    {
        //check if this condition can be merged
        boolean canMerge = false;
        for (Multiple multiple : buffer.getList())
        {
            if (multiple.getValue() == gameTotal)
            {
                canMerge = true;
                break;
            }
        }
        if (!canMerge)
        {
            System.out.println("Total number is " + gameTotal + ", you cannot merge");
            return false;
        }
        int option;
        do
        {
            showBufferList();
            option = Input.acceptAValidNumericInput("please select a buffer index");
            if (buffer.getList().get(option - 1).getValue() != gameTotal)
            {
                System.out.println("you cannot select this multiple value");
            }
            if (option <= 0 || option > buffer.getList().size())
            {
                System.out.println("select index is out of bound, please select again");
            }
        } while (option <= 0 || option > buffer.getList().size() || !(buffer.getList().get(option - 1).getValue() == gameTotal));
        this.gameTotal += buffer.getList().get(option - 1).getValue();
        buffer.getList().remove(option - 1);
        System.out.println("user has selected to merge the multiple value, now the buffer list is");
        showBufferList();
        System.out.println("the game total is " + this.gameTotal);
        return true;
    }

    private boolean split(int gameTotal)
    {
        buffer.addMultiPleToBuffer(gameTotal);
        this.gameTotal -= gameTotal;
        System.out.println("split complete!");
        return true;
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

    private void showBufferList()
    {
        for (int i = 0; i < buffer.getList().size(); i++)
        {
            System.out.println("Multiple " + (i + 1) + ": " + buffer.getList().get(i).getValue());
        }
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
        if (buffer.isOverFlow())
        {
            System.out.println("Buffer is overflow, this game is over!");
        }
        if (gameTotal >= MAX_TOTAL_NUMBER)
        {
            System.out.println("Game total is greater than the limitation, this game is over!");
        }
        return !(buffer.isOverFlow() || gameTotal >= MAX_TOTAL_NUMBER);
    }
}
