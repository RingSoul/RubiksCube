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
        if (rubiksCube != null)
        {
            System.out.println();
            System.out.println("Time to solve a Rubik's Cube~~~");
            boolean isWon = rubiksCube.checkIfWon();
            player.incrementGameCount();
            System.out.println("This is your Game #" + player.getGameCount());
            data += "game#" + player.getGameCount() + "/size:" + rubiksCube.getSize() + "/";
            Record record = new Record(rubiksCube.getSize());
            while (!isWon) {
                System.out.println("This is your current front view:");
                rubiksCube.displayFrontView();
                System.out.println("Possible Commands:");
                System.out.println("1. Change Perspective --- DOES NOT contribute to action count");
                System.out.println("2. Rotate by Row --- contributes to action count");
                System.out.println("3. Rotate by Column --- contributes to action count");
                System.out.println("4. Quit the Game\n");
                System.out.print("Type your command (1/2/3/4): ");
                String input = inputReader.nextLine();
                if (input.equals("4")) {
                    break;
                }
                processChoiceAndUpdateRecord(input, record);
                isWon = rubiksCube.checkIfWon();
            }
            endGameAndSaveData(record);
        }
        else
        {
            System.out.println("Something wrong happened =( Game over");
        }
    }

    private void initiatePlayer()
    {
        System.out.print("Enter your name: ");
        String username = inputReader.nextLine();
        try {
            // creating, if applicable
            file = new File("src/" + username + ".data");
            file.createNewFile();
            // reading
            int gameCount = 0;
            boolean alreadyExist = false;
            Scanner fileReader = new Scanner(file);
            String copy = ""; // in case user played before, this variable saves the line of data before the line is manipulated by String methods
            while (fileReader.hasNextLine())
            {
                String line = fileReader.nextLine();
                if (line.indexOf(username) != -1)
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
                System.out.println("Welcome back, " + username + ".");
                data += copy;
                player = new Player(username, gameCount, data);
                presentOptionsForReturningPlayers();
            }
            else
            {
                System.out.println("Your account seems new to me, hi.");
                data += "username:" + username + "|";
                player = new Player(username, 0, data);
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
            while (!(isNumber(sizeAsStr) && isWithinRange(sizeAsInt, 2, 30))) {
                System.out.println("Your Rubik's Cube will be \"size * size * size\".");
                System.out.print("Enter the value for size (2~30): ");
                sizeAsStr = inputReader.nextLine();
                if (isNumber(sizeAsStr) == true) {
                    sizeAsInt = Integer.parseInt(sizeAsStr);
                    if (isWithinRange(sizeAsInt, 2, 30) == true)
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
            String rowNumAsStr = "";
            int rowNum = 0;
            while (!isWithinRange(rowNum, 1, rubiksCube.getSize())) {
                System.out.print("(Enter a number between 1 and " + rubiksCube.getSize() + ") You want to rotate Row #");
                rowNumAsStr = inputReader.nextLine(); // minus 1 later
                if (isNumber(rowNumAsStr))
                {
                    rowNum = Integer.parseInt(rowNumAsStr);
                    if (!isWithinRange(rowNum, 1, rubiksCube.getSize()))
                    {
                        System.out.println(TextFormat.RED + "Not within the indicated range." + TextFormat.RESET);
                    }
                }
                else
                {
                    System.out.println(TextFormat.RED + "Type a number please =(" + TextFormat.RESET);
                }
            }
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
                System.out.println(TextFormat.RED + "Nice try, but invalid, now we start over =(" + TextFormat.RESET);
            }
        }
        else if (choice.equals("3"))
        {
            String colNumAsStr = "";
            int colNum = 0;
            while (!isWithinRange(colNum, 1, rubiksCube.getSize())) {
                System.out.print("(Enter a number between 1 and " + rubiksCube.getSize() + ") You want to rotate Column #");
                colNumAsStr = inputReader.nextLine(); // minus 1 later
                if (isNumber(colNumAsStr))
                {
                    colNum = Integer.parseInt(colNumAsStr);
                    if (!isWithinRange(colNum, 1, rubiksCube.getSize()))
                    {
                        System.out.println(TextFormat.RED + "Not within the indicated range." + TextFormat.RESET);
                    }
                }
                else
                {
                    System.out.println(TextFormat.RED + "Type a number please =(" + TextFormat.RESET);
                }
            }
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
                System.out.println(TextFormat.RED + "Nice try, but invalid, now we start over =(" + TextFormat.RESET);
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
        data += "actionCount:" + record.getActionCount() + "/";
        data += "solved:" + rubiksCube.checkIfWon() + "|";
        if (rubiksCube.checkIfWon())
        {
            System.out.println(TextFormat.GREEN + "Congratulation, you solved a Rubik's Cube with a size of " + rubiksCube.getSize() + "!" + TextFormat.RESET);
            record.changeToSolved();
        }
        else
        {
            System.out.println(TextFormat.BLUE + "Unfortunately, you failed to solve this Rubik's Cube..." + TextFormat.RESET);
        }
        player.getRecords().add(record);
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
            System.out.println("1. Check out my best record in terms of least number of actions!");
            System.out.println("2. Check out the biggest Rubik's Cube I ever tried!");
            System.out.println("3. See all my record(s)!");
            System.out.print("Type your command (1/2/3): ");
            String command = inputReader.nextLine();
            System.out.println();
            if (command.equals("1"))
            {
                player.reportBestRecordByActionCount();
            }
            else if (command.equals("2"))
            {
                player.reportLargestSize();
            }
            else if (command.equals("3"))
            {
                player.presentAllRecords();
            }
            else
            {
                System.out.println(TextFormat.GREEN + "Seems like you don't want to check your record, let's get to the game, namely torment." + TextFormat.RESET);
            }
            System.out.println();
        }
    }


    // input checker methods
    private boolean isNumber(String str)
    {
        if (str.equals(""))
        {
            return false;
        }
        String[] nums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int i = 0; i < str.length(); i++) // for every "digit" in sizeAsStr
        {
            boolean isValidDigit = false;
            String current = str.substring(i, i + 1);
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

    private boolean isWithinRange(int input, int lowerBound, int upperBound)
    {
        if (input >= lowerBound && input <= upperBound)
        {
            return true;
        }
        return false;
    }
}
