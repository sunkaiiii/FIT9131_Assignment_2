package ass2;

import java.io.IOException;
import java.util.ArrayList;

public class Game
{
    private String playerName;
    private int gameTotal;
    private ArrayList<Buffer> multipleList;
    private Buffer buffer;

    private final String INPUT_FILE = "multiples.txt";
    private final String OUTPUT_FILE = "output.txt";

    public Game()
    {
        playerName = "";
        gameTotal = 0;
        buffer = new Buffer();
        multipleList = new ArrayList<>();
        multipleList.add(new Buffer(5));
        multipleList.add(new Buffer(3));
    }

    public void startGame()
    {
        while (!isHasAValidUserName(this.playerName))
        {
            trySetUserName(new Input().acceptUserInput("please input a user name"));
        }
        System.out.println("Welcome " + playerName + "!!!!");
        tryToSetGameMaxTotal();
        int tempGameTotal = 0;
        ArrayList<Integer> randomMultiples = getRandomMultiples();
        if (randomMultiples == null || randomMultiples.isEmpty())
        {
            System.out.println("read multiple file error");
            return;
        }
        RNG rng = new RNG(0, randomMultiples.size());
        while (isContinuePlay(tempGameTotal))
        {
            showBufferList(tempGameTotal);
            tempGameTotal += randomMultiples.get(rng.getAValidIndex());
            System.out.println("this system has Randomly chosen a multiple");
            System.out.println("Now the game total is " + tempGameTotal);
            int userInput;
            boolean isGenerateNewValue = false;
            do
            {
                System.out.println("1.split\n2.merge");
                userInput = new Input().acceptAValidNumericInput("please select a option");
                switch (userInput)
                {
                    case 1:
                        isGenerateNewValue = true;
                        tempGameTotal = split(tempGameTotal);
                        break;
                    case 2:
                        isGenerateNewValue = false;
                        tempGameTotal = merge(tempGameTotal);
                        break;
                    default:
                        System.out.println("you must chosen a number between 1 and 2!");
                        break;
                }
            } while (!isGenerateNewValue && isContinuePlay(tempGameTotal));
        }
        new FileIO(OUTPUT_FILE).writeContentToFile(playerName + " has got " + tempGameTotal + " score");
    }

    private void tryToSetGameMaxTotal()
    {
        int newMaxTotal = 0;
        do
        {
            newMaxTotal = new Input().acceptAValidNumericInput("please insert a game total");
            if (!isAVarifiedGameTotal(newMaxTotal))
            {
                System.out.println("The game total must be greater than 32 and a multiple of 8!");
            }
        } while (!isAVarifiedGameTotal(newMaxTotal));
        setGameTotal(newMaxTotal);
    }

    private boolean isAVarifiedGameTotal(int newGameTotal)
    {
        return (newGameTotal >= 32 && newGameTotal % 8 == 0);
    }

    private int merge(int tempGameTotal)
    {
        boolean merged = false;
        for (Multiple multiple : buffer.getList())
        {
            if (multiple.getValue() == tempGameTotal)
            {
                System.out.println("Multiple value: " + multiple.getValue() + " has been merged");
                buffer.getList().remove(multiple);
                System.out.println("merge operation has been completed, now the buffer list is");
                showBufferList(tempGameTotal);
                System.out.println("the game total is " + tempGameTotal);
                tempGameTotal += multiple.getValue();
                merged = true;
            }
        }
        if (!merged)
        {
            System.out.println("Total number is " + tempGameTotal + ", you cannot merge");
        }
        return tempGameTotal;

    }

    private int split(int gameTotal)
    {
        buffer.addMultiPleToBuffer(gameTotal);
        System.out.println("split complete!");
        return 0;
    }

    private ArrayList<Integer> getRandomMultiples()
    {
        try
        {
            String fileContent = new FileIO(INPUT_FILE).readMultipleFromFiles();
            ArrayList<ArrayList<Integer>> candidateArrays = new ArrayList<>();
            String[] eachMultiples = fileContent.split("\n");
            for (String multipleLine : eachMultiples)
            {
                String[] multiples = multipleLine.split(",");
                ArrayList<Integer> multipleArray = new ArrayList<>();
                for (String multiple : multiples)
                {
                    multipleArray.add(Integer.parseInt(multiple));
                }
                candidateArrays.add(multipleArray);
            }
            System.out.println("Please choose a multiple:");
            for (int i = 0; i < candidateArrays.size(); i++)
            {
                StringBuffer outInformation = new StringBuffer().append(i + 1).append(": ");
                for (int number : candidateArrays.get(i))
                {
                    outInformation.append(number).append(",");
                }
                outInformation.delete(outInformation.length() - 1, outInformation.length());
                System.out.println(outInformation.toString());
            }
            int inputNumber;
            do
            {
                inputNumber = new Input().acceptAValidNumericInput("select: ");
            } while (inputNumber < 1 || inputNumber > candidateArrays.size());
            return candidateArrays.get(inputNumber - 1);
        } catch (IOException e)
        {
            System.out.println("read multiple file error");
        } catch (NumberFormatException e)
        {
            System.out.println("Content is incorrect");
        }
        return null;
    }

    private void showBufferList(int tempGameTotal)
    {
        boolean listIsAllEmpty = true;
        int maxSize = 0;
        for (Buffer buffer : multipleList)
        {
            if (buffer.getList().size() != 0)
            {
                listIsAllEmpty = false;
                maxSize = Math.max(maxSize, buffer.getList().size());
            }
        }
        if (listIsAllEmpty)
        {
            return;
        }
        String format = "%4s%5s%10s%4s%5s\n";
        System.out.println(String.format(format, "Index", "Value", "Game Total", "Index", "Value"));
        System.out.println(String.format(format, "----", "----", "---", "----", "----"));
        Buffer left = multipleList.get(0);
        Buffer right = multipleList.get(1);
        for (int i = 0; i < maxSize; i++)
        {
            String indexString = (i + 1) + "";
            String leftValue = "" + (i < left.getList().size() ? left.getList().get(i) : "");
            String rightValue = "" + (i < right.getList().size() ? right.getList().get(i) : "");
            System.out.println(String.format(format, leftValue.equals("") ? "" : indexString, leftValue, tempGameTotal, rightValue.equals("") ? "" : indexString, rightValue));
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

    private boolean isContinuePlay(int tempGameTotal)
    {
        if (buffer.isOverFlow())
        {
            System.out.println("Buffer is overflow, this game is over!");
        }
        if (tempGameTotal >= getGameTotal())
        {
            System.out.println("Game total is greater than the limitation, this game is over!");
        }
        return !(buffer.isOverFlow() || tempGameTotal >= getGameTotal());
    }

    public int getGameTotal()
    {
        return gameTotal;
    }

    public void setGameTotal(int gameTotal)
    {
        this.gameTotal = gameTotal;
    }
}
