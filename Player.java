import java.util.ArrayList;

public class Player {
    // instance variables
    private String username;
    private ArrayList<Record> records;
    private int gameCount;
    private String[] tokens = {TextFormat.YELLOW + "Y" + TextFormat.RESET,
                               TextFormat.RED + "R" + TextFormat.RESET,
                               TextFormat.GREEN + "G" + TextFormat.RESET,
                               TextFormat.BLUE + "B" + TextFormat.RESET,
                               TextFormat.PURPLE + "P" + TextFormat.RESET,
                               TextFormat.WHITE + "W" + TextFormat.RESET};


    // constructor
    public Player(String username, int gameCount, String data)
    {
        this.username = username;
        records = new ArrayList<Record>();
        this.gameCount = gameCount; // 0 if new player
        if (gameCount > 0)
        {
            restoreRecord(data);
        }
    }

    /* Methods */
    // accessor methods (everything besides colors)
    public String getUsername() {
        return username;
    }
    public ArrayList<Record> getRecords() {
        return records;
    }
    public String[] getTokens() {
        return tokens;
    }
    public int getGameCount() { return gameCount; }
    // mutator methods (everything besides records and colors and gameCount)
    public void setUsername(String newName)
    {
        username = newName;
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
        Record bestGame = null;
        int lowestActionCount = Integer.MAX_VALUE;
        for (Record record : records)
        {
            if (record.isSolved() && record.getActionCount() < lowestActionCount)
            {
                bestGame = record;
                lowestActionCount = record.getActionCount();
            }
        }
        if (bestGame == null)
        {
            System.out.println("啊哦，你还未解开过任何一个魔方呢……");
        }
        else
        {
            System.out.print("你的最佳记录是当你解开了一个尺寸为" + bestGame.getRubiksCubeSize() + "^3的魔方，");
            System.out.println("总计旋转了" + bestGame.getActionCount() + "次。");
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
            System.out.println("你试过的最大的魔方尺寸为" + maxSize + "^3。");
        }
    }

    public void presentAllRecords()
    {
        for (int i = 0; i < records.size(); i++)
        {
            Record record = records.get(i);
            System.out.println("游戏 #" + (i+1));
            System.out.println(record);
            System.out.println("------------------------");
        }
    }

}
