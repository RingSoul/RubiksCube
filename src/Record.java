public class Record {
    // instance variables
    private int rubiksCubeSize;
    private int actionCount;

    // constructor(s)
    public Record(int rubiksCubeSize)
    {
        this.rubiksCubeSize = rubiksCubeSize;
        actionCount = 0;
    }

    /* methods */
    // accessor methods
    public int getRubiksCubeSize() { return rubiksCubeSize; }
    public int getActionCount() { return actionCount; }

    public void incrementActionCount()
    {
        actionCount++;
    }
}
