package ass2;


import java.util.ArrayList;

/**
 * The game class responses for starting a new game and stores the username
 *
 * @author Kai.Sun
 * @version 14/10/2019
 */
public class Game
{
    private String playerName;
    private int gameTotal;
    private ArrayList<Buffer> multipleList;

    private final String INPUT_FILE = "multiples.txt";
    private final String OUTPUT_FILE = "output.txt";

    /**
     * Default Constructor
     */
    public Game()
    {
        playerName = "";
        gameTotal = 0;
        multipleList = new ArrayList<>();
        multipleList.add(new Buffer(5));
        multipleList.add(new Buffer(3));
    }

    /**
     * The initialisation for all fields used in a new game.
     */
    private void initValues()
    {
        gameTotal = 0;
        for (Buffer buffer : multipleList)
        {
            buffer.getMultiples().clear();
        }
    }

    /**
     * Starting a new game
     */
    public void startGame()
    {
        //Once a new game is starting, init fields for it
        initValues();
        while (!isHasAValidUserName(getPlayerName()))
        {
            trySetUserName(new Input().acceptUserInput("please input a user name"));
        }
        System.out.println("Welcome " + playerName + "!!!!");

        //Let the user input a value of game total
        tryToSetGameMaxTotal();

        //Read the information of multiples from the file.
        ArrayList<Multiple> randomMultiples = getRandomMultiples();
        if (randomMultiples == null || randomMultiples.isEmpty())
        {
            System.out.println("read multiple file error");
            return;
        }
        RNG rng = new RNG(0, randomMultiples.size());

        System.out.println("------------------------------------\n------------------------------------");
        System.out.println("Now, game is start!!!");
        int tempGameTotal = 0;
        while (isContinuePlay(tempGameTotal))
        {
            tempGameTotal += randomMultiples.get(rng.getAValidIndex()).getValue();
            System.out.println("this system has Randomly chosen a multiple");
            System.out.println("Now the game total is " + tempGameTotal);

            boolean isGenerateNewValue = false; //this is a flag for whether the user is split a multiple
            while (!isGenerateNewValue && isContinuePlay(tempGameTotal))
            {
                showBufferList(tempGameTotal);
                System.out.println("1.split right →\n2.Merge Right ←←\n3.Split Left ←\n4.Merge Left →→");
                int userInput;
                try
                {
                    userInput = new Input().acceptNumericInput("please select a option");
                } catch (Exception e) //if user inputs a string which not belongs to numeric, show a notification
                {
                    System.out.println("you must chosen a number between 1 and 4!");
                    System.out.println("You have input a wrong option, please try again");
                    continue;
                }

                switch (userInput)
                {
                    case 1:
                        if (split(multipleList.get(1), tempGameTotal))
                        {
                            isGenerateNewValue = true;
                            tempGameTotal = 0;
                        } else
                        {
                            isGenerateNewValue = false;
                        }
                        break;
                    case 2:
                        isGenerateNewValue = false;
                        tempGameTotal = merge(multipleList.get(1), tempGameTotal);
                        break;
                    case 3:
                        if (split(multipleList.get(0), tempGameTotal))
                        {
                            isGenerateNewValue = true;
                            tempGameTotal = 0;
                        } else
                        {
                            isGenerateNewValue = false;
                        }
                        break;
                    case 4:
                        isGenerateNewValue = false;
                        tempGameTotal = merge(multipleList.get(0), tempGameTotal);
                    default:
                        System.out.println("you must chosen a number between 1 and 4!");
                        break;
                }
            }
        }

        //If user wins a game, then write the final result to a file.
        if (tempGameTotal >= getGameTotal())
        {
            new FileIO(OUTPUT_FILE).writeContentToFile(playerName + " has got " + tempGameTotal + " score");
            System.out.println("The result of this game has been record in output.txt!");
        }

    }

    /**
     * Safely set the max game total
     */
    private void tryToSetGameMaxTotal()
    {
        int newMaxTotal;
        do
        {
            try
            {
                newMaxTotal = new Input().acceptNumericInput("please insert a game total");
            } catch (Exception e)
            {
                newMaxTotal = -1;
                System.out.println("You have input an incorrect number, please try again");
                continue;
            }

            if (isCorrectGameTotal(newMaxTotal))
            {
                System.out.println("The game total must be greater than 32 and a multiple of 8!");
            }
        } while (isCorrectGameTotal(newMaxTotal));
        setGameTotal(newMaxTotal); //If there is a correct total game value, then set it to the field.
    }

    /**
     * Judge whether this is a correct value of the game total.
     *
     * @param newGameTotal a new game total input by the user.
     * @return it is a correct value of game total.
     */
    private boolean isCorrectGameTotal(int newGameTotal)
    {
        return (newGameTotal < 32 || newGameTotal % 8 != 0);
    }

    /**
     * Try to do the merge action
     *
     * @param buffer        the selected buffer chosen by the user.
     * @param tempGameTotal the temporary value of the game total
     * @return new value of the temporary value of the game total
     */
    private int merge(Buffer buffer, int tempGameTotal)
    {
        Multiple mergeMultiple = tryToGetAMergeMultiple(buffer, tempGameTotal); //Try to find a multiple which value is equal to the temporary value of game total.
        if (mergeMultiple == null)
        {
            System.out.println("Total number is " + tempGameTotal + ", you cannot merge");
        } else //If find one, merge its value to the temporary game total.
        {
            tempGameTotal += mergeMultiple.getValue();
            System.out.println("Multiple value: " + mergeMultiple.getValue() + " has been merged");
            buffer.getMultiples().remove(mergeMultiple); //After the merge action, delete the multiple object from the buffer list.
            System.out.println("merge operation has been completed, now the buffer list is");
            showBufferList(tempGameTotal);
            System.out.println("the game total is " + tempGameTotal);
        }
        return tempGameTotal;

    }

    /**
     * Try to find a multiple which value is equal to the temporary value of the game total
     *
     * @param buffer        The selected buffer chosen by the user
     * @param tempGameTotal The value of temporary game total
     * @return Return a Multiple object if there is a correspondent multiple or return a null if there are no multiples which value is equal to the game total
     */
    private Multiple tryToGetAMergeMultiple(Buffer buffer, int tempGameTotal)
    {
        Multiple result = null;
        for (Multiple multiple : buffer.getMultiples())
        {
            if (multiple.getValue() == tempGameTotal || tempGameTotal == 0)
            {
                result = multiple;
                break;
            }
        }
        return result;
    }

    /**
     * Try to split the value of temporary game total to the selected buffer.
     *
     * @param buffer    The selected buffer chosen by the user
     * @param gameTotal The temporary game total
     * @return return whether the current value of the game total is successfully split to the buffer.
     */
    private boolean split(Buffer buffer, int gameTotal)
    {
        boolean splitSuccess = false;
        if (buffer.isFull())
        {
            System.out.println("this buffer is full, you cannot split to this");
        } else
        {
            buffer.getMultiples().add(new Multiple(gameTotal));
            System.out.println("split complete!");
            splitSuccess = true;
        }
        return splitSuccess;
    }

    /**
     * Read multiple from the file
     *
     * @return If there is no error happened, return a list of multiples, or return null if there is an error.
     */
    private ArrayList<Multiple> getRandomMultiples()
    {
        ArrayList<Multiple> result = null;
        try
        {
            String fileContent = new FileIO(INPUT_FILE).readMultipleFromFiles(); //read the content of the file by a FileIO object
            if (fileContent == null || fileContent.trim().isEmpty())
            {
                System.out.println("The content of the tile is empty!");
                result = null;
            } else
            {
                ArrayList<ArrayList<Multiple>> candidateArrays = new ArrayList<>();
                String[] eachMultiples = fileContent.split("\n");
                for (String multipleLine : eachMultiples)
                {
                    String[] multiples = multipleLine.split(",");
                    ArrayList<Multiple> multipleArray = new ArrayList<>();
                    for (String multiple : multiples)
                    {
                        multipleArray.add(new Multiple(Integer.parseInt(multiple)));
                    }
                    candidateArrays.add(multipleArray); //build the whole batch of multiples' list
                }
                System.out.println("Please choose a multiple:");
                for (int i = 0; i < candidateArrays.size(); i++)
                {
                    StringBuffer outInformation = new StringBuffer().append(i + 1).append(": ");
                    for (Multiple number : candidateArrays.get(i))
                    {
                        outInformation.append(number.getValue()).append(",");
                    }
                    outInformation.delete(outInformation.length() - 1, outInformation.length()); //delete the comma after the last of the element in the list.
                    System.out.println(outInformation.toString());
                }
                int inputNumber;
                do
                {
                    try
                    {
                        inputNumber = new Input().acceptNumericInput("select: ");
                    } catch (Exception e)
                    {
                        System.out.println("You have input a incorrect number! The number should be between 1 and " + candidateArrays.size() + "please try again ");
                        inputNumber = -1;
                    }
                } while (inputNumber < 1 || inputNumber > candidateArrays.size());
                result = candidateArrays.get(inputNumber - 1);
            }

        }catch (NumberFormatException e)
        {
            System.out.println("Content is incorrect");
        }
        return result;
    }

    /**
     * show the temporary information of the buffer list
     * @param tempGameTotal The temporary value of the game total.
     */
    public void showBufferList(int tempGameTotal)
    {
        boolean listIsAllEmpty = true;
        int maxSize = 0;
        for (Buffer buffer : multipleList)
        {
            if (buffer.getMultiples().size() != 0)
            {
                listIsAllEmpty = false;
                maxSize = Math.max(maxSize, buffer.getMultiples().size()); //ensure the max number of size in buffers.
            }
        }
        if (listIsAllEmpty)
        {
            return; //if all buffers are empty, then show nothing.
        }
        String format = "%8s %s %8s %8s %s\n"; //format the output string, makes it be more readable.
        System.out.println(String.format(format, "Index", "Value", "Game Total", "Index", "Value"));
        System.out.println(String.format(format, "--------", "-----", "----------------", "--------", "-----"));
        Buffer left = multipleList.get(0);
        Buffer right = multipleList.get(1);
        for (int i = 0; i < maxSize; i++) //The times for looping is decided by the buffer that has a higher amount of Multiple objects.
        {
            String indexString = (i + 1) + ": ";
            String leftValue = "" + (i < left.getMultiples().size() ? left.getMultiples().get(i).getValue() : ""); //If the current index is out of bound, then this line will show an empty String.
            String rightValue = "" + (i < right.getMultiples().size() ? right.getMultiples().get(i).getValue() : ""); //If the current index is out of bound, then this line will show an empty String.
            String totalValue = "";
            if (i == maxSize / 2) //If there is the centre of the loop, then show the number of the game total.
            {
                totalValue = "| " + tempGameTotal + " |";
            }
            System.out.println(String.format(format, leftValue.equals("") ? "" : indexString, leftValue, totalValue, rightValue.equals("") ? "" : indexString, rightValue));
        }
    }

    /**
     * Try to set an user name if the use inputs a correct user name
     * @param userInputName the name input by the user.
     */
    private void trySetUserName(String userInputName)
    {
        if (isHasAValidUserName(userInputName))
        {
            setPlayerName(userInputName);
        } else
        {
            System.out.println("the input username:" + userInputName + " is illegal, please try again!");
        }
    }

    /**
     * Judge whether it is a correct user name
     * @param playerName An user name input by the user.
     * @return Whether it is a correct user name or not.
     */
    private boolean isHasAValidUserName(String playerName)
    {
        return !(playerName == null || playerName.trim().isEmpty() || playerName.length() < 3 || playerName.length() > 10);
    }


    /**
     * Judge whether this game is over or not
     * @param tempGameTotal The temporary value of the game total
     * @return if the game cannot continue to play, return false, if can, return true.
     */
    private boolean isContinuePlay(int tempGameTotal)
    {
        boolean isOver = true;
        for (int i = 0; i < multipleList.size(); i++)
        {
            Buffer buffer = multipleList.get(i);
            if (!buffer.isFull() || tryToGetAMergeMultiple(buffer, tempGameTotal) != null)
            {
                isOver = false;
                break;
            }
        }
        if (isOver)
        {
            System.out.println("all buffer is full, and there is no multiple can merge to total number. This game is over");
        } else if (tempGameTotal >= getGameTotal())
        {
            isOver = true;
            System.out.println("Game total is greater than the limitation, this game is over!");
        }
        return !isOver;
    }


    /**
     * The accessor of the playerName
     * @return the value of the player name
     */
    public String getPlayerName()
    {
        return playerName;
    }

    /**
     * Mutator of the player name
     * @param newPlayerName the new value of the player name
     */
    public void setPlayerName(String newPlayerName)
    {
        this.playerName = newPlayerName;
    }

    /**
     * The accessor of the multiple list
     * @return the value of the multiple list
     */
    public ArrayList<Buffer> getMultipleList()
    {
        return multipleList;
    }

    /**
     * Mutator of the multiple list
     * @param newMultipleList the new value of the multiple list
     */
    public void setMultipleList(ArrayList<Buffer> newMultipleList)
    {
        this.multipleList = newMultipleList;
    }

    /**
     * The accessor of the game total
     * @return the value of the game total
     */
    public int getGameTotal()
    {
        return gameTotal;
    }

    /**
     * The mutator of the game total
     * @param newGameTotal the new value of the game total
     */
    public void setGameTotal(int newGameTotal)
    {
        this.gameTotal = newGameTotal;
    }
}
