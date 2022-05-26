import java.util.ArrayList;

public class Player {
    // instance variables
    private String userName;
    private ArrayList<Record> records;
    private String[] tokens;

    // final variables
    // default set of colors as tokens for the sides of the cubes
    // can be accessed directly
    public static final String YELLOW = "\u001b[33m";
    public static final String RED = "\u001b[31m";
    public static final String GREEN = "\u001b[32m";
    public static final String BLUE = "\u001b[34m";
    public static final String PURPLE = "\u001b[35m";
    public static final String WHITE = "\u001b[37m";
    public static final String RESET = "\u001b[0m";

    // constructor
    public Player(String userName)
    {
        this.userName = userName;
        records = new ArrayList<Record>();
        // can be outside of constructor???
        tokens = new String[]{YELLOW + "Y",
                              RED + "R",
                              GREEN + "G",
                              BLUE + "B",
                              PURPLE + "P",
                              WHITE + "W",};
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
    // mutator methods (everything besides records and colors)
    public void setUserName(String newName)
    {
        userName = newName;
    }
    public void setTokens(String[] newTokens)
    {
        tokens = newTokens;
    }
}
