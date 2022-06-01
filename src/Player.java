import java.util.ArrayList;

public class Player {
    // instance variables
    private String userName;
    private ArrayList<Record> records;
    private int gameCount;
    private String[] tokens = {YELLOW + "Y" + RESET,
                               RED + "R" + RESET,
                               GREEN + "G" + RESET,
                               BLUE + "B" + RESET,
                               PURPLE + "P" + RESET,
                               WHITE + "W" + RESET};

    // final variables
    // default set of colors as tokens for the sides of the cubes
    // can be accessed directly
    private static final String YELLOW = "\u001b[33m";
    private static final String RED = "\u001b[31m";
    private static final String GREEN = "\u001b[32m";
    private static final String BLUE = "\u001b[34m";
    private static final String PURPLE = "\u001b[35m";
    private static final String WHITE = "\u001b[37m";
    private static final String RESET = "\u001b[0m";

    // constructor
    public Player(String userName, int gameCount, String data)
    {
        this.userName = userName;
        records = new ArrayList<Record>();
        this.gameCount = gameCount; // 0 if new player
        if (gameCount > 0)
        {
            restoreRecord(data);
        }
    }

    /* Methods */
    // accessor methods (everything besides colors)
    public String getUserName() {
        return userName;
    }
    public ArrayList<Record> getRecords() {
        return records;
    }
    public String[] getTokens() {
        return tokens;
    }
    public int getGameCount() { return gameCount; }
    // mutator methods (everything besides records and colors and gameCount)
    public void setUserName(String newName)
    {
        userName = newName;
    }
    public void setTokens(String[] newTokens)
    {
        tokens = newTokens;
    }

    public void incrementGameCount()
    {
        gameCount++;
    }

    private void restoreRecord(String data)
    {
        String[] infos = data.split("\\|");
        for (String info : infos)
        {
            if (info.indexOf("game") != -1)
            {
                String[] piecesOfInfo = info.split("/"); // 1 = size, 2 = action count, 3 = solved or not solved
                int indexOfColon = piecesOfInfo[1].indexOf(":");
                String sizeAsStr = piecesOfInfo[1].substring(indexOfColon + 1);
                int sizeAsInt = Integer.parseInt(sizeAsStr);
                indexOfColon = piecesOfInfo[2].indexOf(":");
                String actionCountAsStr = piecesOfInfo[2].substring(indexOfColon + 1);
                int actionCountAsInt = Integer.parseInt(actionCountAsStr);
                indexOfColon = piecesOfInfo[3].indexOf(":");
                String solvedOrNot = piecesOfInfo[3].substring(indexOfColon + 1);
                boolean isSolved = Boolean.parseBoolean(solvedOrNot);
                Record oldRecord = new Record(sizeAsInt, actionCountAsInt, isSolved);
                records.add(oldRecord);
            }
        }
    }

}
