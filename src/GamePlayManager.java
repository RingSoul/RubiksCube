import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class GamePlayManager {
    private Scanner inputReader;
    private File file;
    private Player player;
    private RubiksCube rubiksCube;
    private String data;


    public GamePlayManager()
    {
        inputReader = new Scanner(System.in);
        try {
            file = new File("src/GameRecord.data");
            file.createNewFile();

            createPlayer();
        } catch (IOException e) {
            System.out.println("Error in initiation stage.");
        }
    }

    public void start()
    {

    }

    private void createPlayer()
    {
        System.out.print("Enter your name: ");
        String name = inputReader.nextLine();
        player = new Player(name);
        try {
            FileWriter fileWriter = new FileWriter("src/GameRecord.data");
            fileWriter.write(name);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error in recording the player's name.");
        }
    }

    private void createRubiksCube()
    {
        if (player != null)
        {
            int size;
            System.out.println("Your Rubik's Cube will be \"size * size * size\".");
            System.out.print("Enter the value for size: ");
            size = inputReader.nextInt();
            rubiksCube = new RubiksCube(size, player);
        }
    }


}
