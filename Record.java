public class Record {
    // instance variables
    private int rubiksCubeSize;
    private int actionCount;
    private boolean isSolved;

    // constructor(s)
    // for new game
    public Record(int rubiksCubeSize)
    {
        this.rubiksCubeSize = rubiksCubeSize;
        this.actionCount = 0;
        this.isSolved = false;
    }
    // for old game
    public Record(int rubiksCubeSize, int actionCount, boolean isSolved)
    {
        this.rubiksCubeSize = rubiksCubeSize;
        this.actionCount = actionCount;
        this.isSolved = isSolved;
    }

    /* methods */
    // accessor methods
    public int getRubiksCubeSize() { return rubiksCubeSize; }
    public int getActionCount() { return actionCount; }
    public boolean isSolved() { return isSolved; }

    public void incrementActionCount()
    {
        actionCount++;
    }

    public void changeToSolved()
    {
        isSolved = true;
    }

    public String toString()
    {
        String info = "魔方尺寸 = " + rubiksCubeSize;
        info += "\n行动数/旋转数量 = " + actionCount;
        if (isSolved)
            info += "\n该魔方被解开了";
        else
            info += "\n该魔方未能被解开";
        return info;
    }
}
