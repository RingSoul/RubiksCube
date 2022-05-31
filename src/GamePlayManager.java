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
            processChoice(input, record);
            isWon = rubiksCube.checkIfWon();
        }
        endGame(record);
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
                player = new Player(userName, gameCount);
                data += copy;
            }
            else
            {
                System.out.println("Your account seems new to me, hi.");
                player = new Player(userName, 0);
                data += "userName:" + userName + "|";
            }
        } catch (IOException e) {
            System.out.println("Error in initiating player.");
        }
    }

    private void constructRubiksCube()
    {
        if (player != null)
        {
            int size;
            System.out.println("Your Rubik's Cube will be \"size * size * size\".");
            System.out.print("Enter the value for size: ");
            size = inputReader.nextInt();
            while (size < 2)
            {
                System.out.println("\u001b[31m" + "Has to be greater than 1." + "\u001b[0m");
                System.out.print("Enter the value for size: ");
                size = inputReader.nextInt();
            }
            rubiksCube = new RubiksCube(size, player);
        }
    }

    private void processChoice(String choice, Record record)
    {
        if (choice.equals("1"))
        {
            System.out.println("Indicate the direction you want to move the Rubik's Cube for changing the current front view:");
            System.out.println("1. Leftward");
            System.out.println("2. Rightward");
            System.out.println("3. Upward");
            System.out.println("4. Downward");
            System.out.print("Type your command (1/2/3/4): ");
            String command = inputReader.nextLine();
            switch (command) {
                case "1" -> rubiksCube.rotatePerspectiveLeftward();
                case "2" -> rubiksCube.rotatePerspectiveRightward();
                case "3" -> rubiksCube.rotatePerspectiveUpward();
                case "4" -> rubiksCube.rotatePerspectiveDownward();
                default -> System.out.println("\u001b[31m" + "Come on, what are you thinking, now we have to do it again =(" + "\u001b[0m");
            }
        }
        else if (choice.equals("2"))
        {
            System.out.print("(Enter a number between 1 and " + rubiksCube.getSize() + ") You want to rotate Row #");
            int rowNum = inputReader.nextInt(); // minus 1 later
            if (rowNum > rubiksCube.getSize())
            {
                System.out.println("\u001b[31m" + "Invalid row number. See you later." + "\u001b[0m");
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
                    System.out.println("\u001b[31m" + "Nice try, but invalid." + "\u001b[0m");
                }
            }
        }
        else if (choice.equals("3"))
        {
            System.out.print("(Enter a number between 1 and " + rubiksCube.getSize() + ") You want to rotate Column #");
            int colNum = inputReader.nextInt(); // minus 1 later
            if (colNum > rubiksCube.getSize())
            {
                System.out.println("\u001b[31m" + "Invalid column number. See you later." + "\u001b[0m");
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
                    System.out.println("\u001b[31m" + "Nice try, but invalid." + "\u001b[0m");
                }
            }
        }
        else
        {
            System.out.println("\u001b[31m" + "Invalid command." + "\u001b[0m");
        }
    }

    private void endGame(Record record)
    {
        player.getRecords().add(record);
        data += "actionCount:" + record.getActionCount() + "/";
        data += "solved:" + rubiksCube.checkIfWon() + "|";
        if (rubiksCube.checkIfWon())
        {
            System.out.println("Congratulation, you solved a Rubik's Cube with a size of " + rubiksCube.getSize() + "!");
        }
        else
        {
            System.out.println("Unfortunately, you failed to solve this Rubik's Cube...");
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
}
