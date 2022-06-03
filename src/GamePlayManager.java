import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GamePlayManager {
    private Scanner inputReader;
    private File file;
    private Player player;
    private RubiksCube rubiksCube;
    private String data; // that would be saved into the file at the end of the program


    public GamePlayManager()
    {
        data = "";
        inputReader = new Scanner(System.in);
        initiatePlayer(); // player and file initiated
        constructRubiksCube(); // rubiksCube initiated
    }

    public void playGame()
    {
        System.out.println();
        System.out.println("Time to solve a Rubik's Cube~~~");
        boolean isWon = rubiksCube.checkIfWon();
        player.incrementGameCount();
        System.out.println("This is your Game #" + player.getGameCount());
        data += "game#" + player.getGameCount() + "/size:" + rubiksCube.getSize() + "/";
        Record record = new Record(rubiksCube.getSize());
        inputReader.nextLine(); // avoid weird output
        while(!isWon)
        {
            System.out.println("This is your current front view:");
            rubiksCube.displayFrontView();
            System.out.println("Possible Commands:");
            System.out.println("1. Change Perspective --- DOES NOT contribute to action count");
            System.out.println("2. Rotate by Row --- contributes to action count");
            System.out.println("3. Rotate by Column --- contributes to action count");
            System.out.println("4. Quit the Game\n");
            System.out.print("Type your command (1/2/3/4): ");
            String input = inputReader.nextLine();
            if (input.equals("4"))
            {
                break;
            }
            processChoiceAndUpdateRecord(input, record);
            isWon = rubiksCube.checkIfWon();
        }
        endGameAndSaveData(record);
    }

    private void initiatePlayer()
    {
        System.out.print("Enter your name: ");
        String userName = inputReader.nextLine();
        try {
            // creating, if applicable
            file = new File("src/" + userName + ".data");
            file.createNewFile();
            // reading
            int gameCount = 0;
            boolean alreadyExist = false;
            Scanner fileReader = new Scanner(file);
            String copy = ""; // in case user played before, this variable saves the line of data before the line is manipulated by String methods
            while (fileReader.hasNextLine())
            {
                String line = fileReader.nextLine();
                if (line.indexOf(userName) != -1)
                {
                    copy = line;
                    alreadyExist = true;
                    int index = line.indexOf("|");
                    line = line.substring(index + 1); // the first part is always the userName
                    index = line.indexOf("|");
                    while (index != -1)
                    {
                        gameCount++;
                        line = line.substring(index + 1);
                        index = line.indexOf("|");
                    }
                    break;
                }
            }
            fileReader.close();
            if (alreadyExist)
            {
                System.out.println("Welcome back, " + userName + ".");
                data += copy;
                player = new Player(userName, gameCount, data);
                presentOptionsForReturningPlayers();
            }
            else
            {
                System.out.println("Your account seems new to me, hi.");
                data += "userName:" + userName + "|";
                player = new Player(userName, 0, data);
            }
        } catch (IOException e) {
            System.out.println("Error in initiating player.");
        }
    }

    private void constructRubiksCube()
    {
        if (player != null)
        {
            String sizeAsStr = "";
            int sizeAsInt = 0;
            while (!(checkSizeAsStr(sizeAsStr) && checkSizeAsInt(sizeAsInt))) {
                System.out.println("Your Rubik's Cube will be \"size * size * size\".");
                System.out.print("Enter the value for size (2~30): ");
                sizeAsStr = inputReader.nextLine();
                if (checkSizeAsStr(sizeAsStr) == true) {
                    sizeAsInt = Integer.parseInt(sizeAsStr);
                    if (checkSizeAsInt(sizeAsInt) == true)
                    {
                        rubiksCube = new RubiksCube(sizeAsInt, player);
                    }
                    else
                    {
                        System.out.println(TextFormat.RED + "Not accurate, make sure the size is between 2 and 30." + TextFormat.RESET);
                        sizeAsInt = 0;
                    }
                } else {
                    System.out.println(TextFormat.RED + "Not accurate, make sure you type everything as valid digits." + TextFormat.RESET);
                }
            }
        }
    }


    // use
    private void processChoiceAndUpdateRecord(String choice, Record record)
    {
        if (choice.equals("1"))
        {
            System.out.println("Indicate the direction you want to move the Rubik's Cube for changing the current front view:");
            System.out.println("1. Leftward --> former RIGHT view will become your front view");
            System.out.println("2. Rightward --> former LEFT view will become your front view");
            System.out.println("3. Upward --> former BOTTOM view will become your front view");
            System.out.println("4. Downward --> former TOP view will become your front view");
            System.out.print("Type your command (1/2/3/4): ");
            String command = inputReader.nextLine();
            switch (command) {
                case "1" -> rubiksCube.rotatePerspectiveLeftward();
                case "2" -> rubiksCube.rotatePerspectiveRightward();
                case "3" -> rubiksCube.rotatePerspectiveUpward();
                case "4" -> rubiksCube.rotatePerspectiveDownward();
                default -> System.out.println(TextFormat.RED + "Come on, what are you thinking, now we have to do it again =(" + TextFormat.RESET);
            }
        }
        else if (choice.equals("2"))
        {
            System.out.print("(Enter a number between 1 and " + rubiksCube.getSize() + ") You want to rotate Row #");
            int rowNum = inputReader.nextInt(); // minus 1 later
            if (rowNum > rubiksCube.getSize() && rowNum < 1)
            {
                System.out.println(TextFormat.RED + "Invalid row number. See you later." + TextFormat.RESET);
            }
            else
            {
                inputReader.nextLine();
                System.out.print("(L)eftward or (R)ightward? ");
                String direction = inputReader.nextLine();
                if (direction.toUpperCase().equals("L"))
                {
                    rubiksCube.rowRotationLeft(rowNum - 1);
                    record.incrementActionCount();
                }
                else if (direction.toUpperCase().equals("R"))
                {
                    rubiksCube.rowRotationRight(rowNum - 1);
                    record.incrementActionCount();
                }
                else
                {
                    System.out.println(TextFormat.RED + "Nice try, but invalid." + TextFormat.RESET);
                }
            }
        }
        else if (choice.equals("3"))
        {
            System.out.print("(Enter a number between 1 and " + rubiksCube.getSize() + ") You want to rotate Column #");
            int colNum = inputReader.nextInt(); // minus 1 later
            if (colNum > rubiksCube.getSize() && colNum < 1)
            {
                System.out.println(TextFormat.RED + "Invalid column number. See you later." + TextFormat.RESET);
            }
            else
            {
                inputReader.nextLine();
                System.out.print("(U)pward or (D)ownward? ");
                String direction = inputReader.nextLine();
                if (direction.toUpperCase().equals("U"))
                {
                    rubiksCube.colRotationUp(colNum - 1);
                    record.incrementActionCount();
                }
                else if (direction.toUpperCase().equals("D"))
                {
                    rubiksCube.colRotationDown(colNum - 1);
                    record.incrementActionCount();
                }
                else
                {
                    System.out.println(TextFormat.RED + "Nice try, but invalid." + TextFormat.RESET);
                }
            }
        }
        else
        {
            System.out.println(TextFormat.RED + "Invalid command." + TextFormat.RESET);
        }
    }

    // end the game and save the data into the file
    private void endGameAndSaveData(Record record)
    {
        player.getRecords().add(record);
        data += "actionCount:" + record.getActionCount() + "/";
        data += "solved:" + rubiksCube.checkIfWon() + "|";
        if (rubiksCube.checkIfWon())
        {
            System.out.println(TextFormat.GREEN + "Congratulation, you solved a Rubik's Cube with a size of " + rubiksCube.getSize() + "!" + TextFormat.RESET);
        }
        else
        {
            System.out.println(TextFormat.BLUE + "Unfortunately, you failed to solve this Rubik's Cube..." + TextFormat.RESET);
        }
        // writing into file
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.close();
            System.out.println("Your game is recorded, see you next time~");
        } catch(IOException e) {
            System.out.println("Error in saving data.");
        }
    }

    // if the user is a returning player, allow them to check their record
    private void presentOptionsForReturningPlayers()
    {
        if (player != null) {
            System.out.println("Before playing, what do you want to do?");
            System.out.println("1. Check out my best record in terms of the least number of actions!");
            System.out.println("2. Check out the biggest Rubik's Cube I ever tried!");
            System.out.print("Type your command (1/2): ");
            String command = inputReader.nextLine();
            if (command.equals("1"))
            {
                player.reportBestRecordByActionCount();
            }
            else if (command.equals("2"))
            {
                player.reportLargestSize();
            }
            else
            {
                System.out.println(TextFormat.GREEN + "Seems like you don't want to check your record, let's get to the game, namely torment." + TextFormat.RESET);
            }
        }
    }


    // input checker methods
    private boolean checkSizeAsStr(String sizeAsStr)
    {
        if (sizeAsStr.equals(""))
        {
            return false;
        }
        String[] nums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int i = 0; i < sizeAsStr.length(); i++) // for every "digit" in sizeAsStr
        {
            boolean isValidDigit = false;
            String current = sizeAsStr.substring(i, i + 1);
            for (String num : nums) // for every num in nums
            {
                if (current.equals(num))
                {
                    isValidDigit = true;
                    break;
                }
            }
            if (isValidDigit == false)
            {
                return false;
            }
        }
        return true;
    }

    private boolean checkSizeAsInt(int sizeAsInt)
    {
        if (sizeAsInt > 1 && sizeAsInt <= 30)
        {
            return true;
        }
        return false;
    }
}
