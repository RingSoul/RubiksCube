import java.util.ArrayList;

public class Player {
    // instance variables
    private String userName;
    private ArrayList<Record> records;
    private int gameCount;
    private String[] tokens = {TextFormat.YELLOW + "Y" + TextFormat.RESET,
                               TextFormat.RED + "R" + TextFormat.RESET,
                               TextFormat.GREEN + "G" + TextFormat.RESET,
                               TextFormat.BLUE + "B" + TextFormat.RESET,
                               TextFormat.PURPLE + "P" + TextFormat.RESET,
                               TextFormat.WHITE + "W" + TextFormat.RESET};


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

    public void reportBestRecordByActionCount()
    {
        if (records.size() > 0) {
            Record record = null;
            int lowestActionCount = Integer.MAX_VALUE;
            for (int i = 0; i < records.size(); i++) {
                if (records.get(i).getActionCount() < lowestActionCount && records.get(i).isSolved()) {
                    record = records.get(i);
                    lowestActionCount = records.get(i).getActionCount();
                }
            }
            if (record == null)
            {
                System.out.println("Ooooops, you have not solved any Rubik's Cube yet...");
            }
            else
            {
                System.out.print("Your best record is when you solved a " + record.getRubiksCubeSize() + "^3 Rubik's Cube ");
                System.out.println("in " + record.getActionCount() + " rotations!");
            }
        }
    }

    public void reportLargestSize()
    {
        if (records.size() > 0)
        {
            int maxSize = records.get(0).getRubiksCubeSize();
            for (Record r : records) {
                if (r.getRubiksCubeSize() > maxSize)
                {
                    maxSize = r.getRubiksCubeSize();
                }
            }
            System.out.println("The biggest Rubik's Cube you ever tried is " + maxSize + "^3.");
        }
    }

    public void presentAllRecords()
    {
        for (int i = 0; i < records.size(); i++)
        {
            Record record = records.get(i);
            System.out.println("Game #" + (i+1));
            System.out.println(record);
            System.out.println("------------------------");
        }
    }

}
