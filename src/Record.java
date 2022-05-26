public class Record {
    // instance variables
    private String userName;
    private int actionCount;

    // constructor(s)
    public Record(String userName, int actionCount)
    {
        this.userName = userName;
        this.actionCount = actionCount;
    }

    /* methods */
    // accessor methods
    public String getUserName() { return userName; }
    public int getActionCount() { return actionCount; }
}
