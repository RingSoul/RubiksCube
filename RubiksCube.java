import java.util.ArrayList;
import java.util.Arrays;

public class RubiksCube {
    // instance variables
    private Cube[][][] rubiksCube;
    private int size; // the entire Rubik's Cube will be "size * size * size"
    private Player player;
    private Side[][] frontView;
    private Side[][] backView;
    private Side[][] leftView;
    private Side[][] rightView;
    private Side[][] topView;
    private Side[][] bottomView;

    // constructor(s)
    public RubiksCube(int size, Player player)
    {
        this.size = size;
        this.player = player;
        createRubiksCube(size);
        updateAllViews();
    }

    /* Methods */
    // accessor methods
    public Cube[][][] getRubiksCube() {
        return rubiksCube;
    }
    public int getSize() {
        return size;
    }
    public Player getPlayer()
    {
        return player;
    }
    public Side[][] getFrontView() {
        return frontView;
    }
    public Side[][] getBackView() {
        return backView;
    }
    public Side[][] getTopView() {
        return topView;
    }
    public Side[][] getBottomView() { return bottomView; }
    public Side[][] getLeftView() { return leftView; }
    public Side[][] getRightView() {
        return rightView;
    }

    // this method creates the Rubik's Cube, with all tokens assigned
    private void createRubiksCube(int size)
    {
        rubiksCube = new Cube[size][size][size];

        // initiate the cube into a solvable & solved one that will be randomized later
        resetRubiksCube();

        // randomly change its structure by rotating
        for (int i = 0; i < size * 15; i++)
        {
            int randomRowNum = (int) (Math.random() * size);
            int randomColNum = (int) (Math.random() * size);
            if (Math.random() < 0.5)
            {
                rowRotationLeft(randomRowNum);
            }
            else
            {
                rowRotationRight(randomRowNum);
            }
            if (Math.random() < 0.5)
            {
                colRotationDown(randomColNum);
            }
            else
            {
                colRotationUp(randomColNum);
            }
            if (Math.random() < 0.5)
            {
                faceRotationClockwise();
            }
            else
            {
                faceRotationCounterclockwise();
            }
        }
    }


    // this method resets the rubik's cube into a solved one
    private void resetRubiksCube()
    {
        if (rubiksCube != null)
        {
            String[] tokenArray = player.getTokens();
            ArrayList<String> tokenList = new ArrayList<String>(Arrays.asList(tokenArray));
            // set tokens
            int random = (int) (Math.random() * tokenList.size());
            String frontToken = tokenList.remove(random); // x = 0, front
            random = (int) (Math.random() * tokenList.size());
            String backToken = tokenList.remove(random); // x = size - 1, back
            random = (int) (Math.random() * tokenList.size());
            String leftToken = tokenList.remove(random); // c = 0, left
            random = (int) (Math.random() * tokenList.size());
            String rightToken = tokenList.remove(random); // c = size - 1, right
            random = (int) (Math.random() * tokenList.size());
            String topToken = tokenList.remove(random); // r = 0, top
            random = (int) (Math.random() * tokenList.size());
            String bottomToken = tokenList.remove(random); // r = size - 1, bottom

            // generate cubes
            for (int r = 0; r < size; r++)
            {
                for (int c = 0; c < size; c++)
                {
                    for (int z = 0; z < size; z++)
                    {
                        Cube cube = new Cube();
                        if (r == 0)
                        {
                            cube.getTop().setRepresentation(topToken);
                        }
                        if (r == size - 1)
                        {
                            cube.getBottom().setRepresentation(bottomToken);
                        }
                        if (c == 0)
                        {
                            cube.getLeft().setRepresentation(leftToken);
                        }
                        if (c == size - 1)
                        {
                            cube.getRight().setRepresentation(rightToken);
                        }
                        if (z == 0)
                        {
                            cube.getFront().setRepresentation(frontToken);
                        }
                        if (z == size - 1)
                        {
                            cube.getBack().setRepresentation(backToken);
                        }
                        rubiksCube[r][c][z] = cube;
                    }
                }
            }
            updateAllViews();
        }
    }


    // this method returns the front view of the Rubik's Cube
    private void updateFrontView()
    {
        if (rubiksCube != null)
        {
            if (frontView == null)
            {
                frontView = new Side[size][size];
            }
            for (int r = 0; r < size; r++) // row of rubik's cube
            {
                for (int c = 0; c < size; c++) // column of rubik's cube
                {
                    Cube current = rubiksCube[r][c][0];
                    frontView[r][c] = current.getFront();
                }
            }
        }
    }
    // this method displays the front view to the user through print statements
    public void displayFrontView()
    {
        if (frontView != null)
        {
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    System.out.print(frontView[r][c].getRepresentation() + "  ");
                }
                System.out.println();
            }
        }
    }


    // this method updates the back view of the Rubik's Cube (the back view is based on the front view)
    private void updateBackView()
    {
        if (rubiksCube != null)
        {
            if (backView == null)
            {
                backView = new Side[size][size];
            }
            for (int r = 0; r < size; r++) // row of rubik's cube
            {
                for (int c = 0; c < size; c++) // column of rubik's cube
                {
                    Cube current = rubiksCube[r][c][size - 1];
                    backView[r][c] = current.getBack();
                }
            }
        }
    }
    // this method returns the top view of the Rubik's Cube
    private void updateTopView()
    {
        if (rubiksCube != null)
        {
            if (topView == null)
            {
                topView = new Side[size][size];
            }
            for (int c = 0; c < size; c++) // column of rubik's cube
            {
                for (int z = 0; z < size; z++) // 3rd dimension of rubik's cube
                {
                    Cube current = rubiksCube[0][c][z];
                    topView[c][z] = current.getTop();
                }
            }
        }
    }
    // this method updates the bottom view of the Rubik's Cube
    private void updateBottomView()
    {
        if (rubiksCube != null)
        {
            if (bottomView == null)
            {
                bottomView = new Side[size][size];
            }
            for (int c = 0; c < size; c++) // column of rubik's cube
            {
                for (int z = 0; z < size; z++) // 3rd dimension of rubik's cube
                {
                    Cube current = rubiksCube[size-1][c][z];
                    bottomView[c][z] = current.getBottom();
                }
            }
        }
    }
    // this method updates left view of the rubik's Cube
    private void updateLeftView()
    {
        if (rubiksCube != null)
        {
            if (leftView == null)
            {
                leftView = new Side[size][size];
            }
            for (int r = 0; r < size; r++) // row of rubik's cube
            {
                for (int z = 0; z < size; z++) // 3rd dimension of rubik's cube
                {
                    Cube current = rubiksCube[r][0][z];
                    leftView[r][z] = current.getLeft();
                }
            }
        }
    }
    // this method updates the right view of the rubik's cube
    private void updateRightView()
    {
        if (rubiksCube != null)
        {
            if (rightView == null)
            {
                rightView = new Side[size][size];
            }
            for (int r = 0; r < size; r++) // row of rubik's cube
            {
                for (int z = 0; z < size; z++) // 3rd dimension of rubik's cube
                {
                    Cube current = rubiksCube[r][size-1][z];
                    rightView[r][z] = current.getRight();
                }
            }
        }
    }

    // this method updates all views
    private void updateAllViews()
    {
        updateFrontView();
        updateBackView();
        updateBottomView();
        updateTopView();
        updateLeftView();
        updateRightView();
    }



    // this method checks if the game is won
    // i.e. if for all the 6 sides, each has exactly n * n same tokens (when n = size of cube)
    public boolean checkIfWon()
    {
        Side[][][] allSixSides = {frontView, backView, topView, bottomView, leftView, rightView};
        for (int i = 0; i < allSixSides.length; i++)
        {
            boolean areAllTheSame = checkOneSideForVictory(allSixSides[i]);
            if (areAllTheSame == false)
            {
                return false;
            }
        }
        return true;
    }

    // precondition: oneSide is not null
    // precondition: oneSide is always one of the instance variables with matching data type
    public boolean checkOneSideForVictory(Side[][] oneSide)
    {
        String first = oneSide[0][0].getRepresentation();
        for (int r = 0; r < size; r++)
        {
            for (int c = 0; c < size; c++)
            {
                if (r != 0 && c != 0)
                {
                    String check = oneSide[r][c].getRepresentation();
                    if (!check.equals(first))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public void rowRotationLeft(int rowNum)
    {
        if (rubiksCube != null)
        {
            /* variables */
            // exclusive of corner cubes
            Cube[] frontCubes = new Cube[size-2];
            Cube[] backCubes = new Cube[size-2];
            Cube[] leftCubes = new Cube[size-2];
            Cube[] rightCubes = new Cube[size-2];
            for (int c = 0; c < frontCubes.length; c++)
            {
                frontCubes[c] = rubiksCube[rowNum][c+1][0]; // going to the left side, left to right --> back to front
                backCubes[c] = rubiksCube[rowNum][c+1][size-1]; // going to the right side, left to right --> back to front
            }
            for (int z = 0; z < leftCubes.length; z++)
            {
                leftCubes[z] = rubiksCube[rowNum][0][z+1]; // going to the back side, front to back --> left to right
                rightCubes[z] = rubiksCube[rowNum][size-1][z+1]; // going to the front side, front to back --> left to right
            }
            // corner cubes
            Cube frontLeft = rubiksCube[rowNum][0][0]; // saving the front left corner based on the front view
            Cube frontRight = rubiksCube[rowNum][size-1][0]; // saving the front right corner based on the front view
            Cube backLeft = rubiksCube[rowNum][0][size-1]; // saving the back left corner based on the front view
            Cube backRight = rubiksCube[rowNum][size-1][size-1]; // saving the back right corner based on the front view

            /* changing position && changing sides */
            // corners
            rubiksCube[rowNum][0][size-1] = frontLeft;
            rubiksCube[rowNum][0][0] = frontRight;
            rubiksCube[rowNum][size-1][0] = backRight;
            rubiksCube[rowNum][size-1][size-1] = backLeft;
            // other than corners
            // front change
            int index; // of the arrays (cubes other than corners)
            // frontCubes (left to right)
            index = 0;
            for (int destination = size - 2; destination >= 1; destination--) // back to front of left side
            {
                rubiksCube[rowNum][0][destination] = frontCubes[index];
                index++;
            }
            // backCubes (left to right)
            index = 0;
            for (int destination = size - 2; destination >= 1; destination--) // back to front of right side
            {
                rubiksCube[rowNum][size-1][destination] = backCubes[index];
                index++;
            }
            // leftCubes (front to back)
            index = 0;
            for (int destination = 1; destination < size - 1; destination++) // left to right of back side
            {
                rubiksCube[rowNum][destination][size-1] = leftCubes[index];
                index++;
            }
            // rightCubes (front to back)
            index = 0;
            for (int destination = 1; destination < size - 1; destination++) // left to right of front side
            {
                rubiksCube[rowNum][destination][0] = rightCubes[index];
                index++;
            }

            /* side adjustments (top & bottom side not impact) */
            Cube[] corners = {frontLeft, frontRight, backRight, backLeft};
            Cube[][] otherThanCorners = {frontCubes, backCubes, leftCubes, rightCubes};
            for (int i = 0; i < 4; i++)
            {
                /* for corners */
                Cube cube = corners[i];
                // store sides as temps
                Side front = cube.getFront();
                Side back = cube.getBack();
                Side left = cube.getLeft();
                Side right = cube.getRight();
                // change
                cube.setLeft(front);
                cube.setBack(left);
                cube.setRight(back);
                cube.setFront(right);

                /* for cubes other than corners */
                for (int c = 0; c < otherThanCorners[i].length; c++)
                {
                    cube = otherThanCorners[i][c];
                    // store sides as temps
                    front = cube.getFront();
                    back = cube.getBack();
                    left = cube.getLeft();
                    right = cube.getRight();
                    // change
                    cube.setLeft(front);
                    cube.setBack(left);
                    cube.setRight(back);
                    cube.setFront(right);
                }
            }
            updateAllViews();
        }
    }
    // this method rotates the perspective leftward
    public void rotatePerspectiveLeftward()
    {
        for (int r = 0; r < size; r++)
        {
            rowRotationLeft(r);
        }
        updateAllViews();
    }


    public void rowRotationRight(int rowNum)
    {
        if (rubiksCube != null)
        {
            /* variables */
            // exclusive of corner cubes
            Cube[] frontCubes = new Cube[size-2];
            Cube[] backCubes = new Cube[size-2];
            Cube[] leftCubes = new Cube[size-2];
            Cube[] rightCubes = new Cube[size-2];
            for (int c = 0; c < frontCubes.length; c++)
            {
                frontCubes[c] = rubiksCube[rowNum][c+1][0]; // going to the left side, left to right --> back to front
                backCubes[c] = rubiksCube[rowNum][c+1][size-1]; // going to the right side, left to right --> back to front
            }
            for (int z = 0; z < leftCubes.length; z++)
            {
                leftCubes[z] = rubiksCube[rowNum][0][z+1]; // going to the back side, front to back --> left to right
                rightCubes[z] = rubiksCube[rowNum][size-1][z+1]; // going to the front side, front to back --> left to right
            }
            // corner cubes
            Cube frontLeft = rubiksCube[rowNum][0][0]; // saving the front left corner based on the front view
            Cube frontRight = rubiksCube[rowNum][size-1][0]; // saving the front right corner based on the front view
            Cube backLeft = rubiksCube[rowNum][0][size-1]; // saving the back left corner based on the front view
            Cube backRight = rubiksCube[rowNum][size-1][size-1]; // saving the back right corner based on the front view

            /* changing position && changing sides */
            // corners
            rubiksCube[rowNum][0][size-1] = backRight;
            rubiksCube[rowNum][0][0] = backLeft;
            rubiksCube[rowNum][size-1][0] = frontLeft;
            rubiksCube[rowNum][size-1][size-1] = frontRight;
            // other than corners
            int index; // of the arrays (cubes other than corners)
            // frontCubes (left to right)
            index = 0;
            for (int destination = 1; destination < size - 1; destination++) // front to back of right side
            {
                rubiksCube[rowNum][size-1][destination] = frontCubes[index];
                index++;
            }
            // backCubes (left to right)
            index = 0;
            for (int destination = 1; destination < size - 1; destination++) // front to back of left side
            {
                rubiksCube[rowNum][0][destination] = backCubes[index];
                index++;
            }
            // leftCubes (front to back)
            index = 0;
            for (int destination = size - 2; destination >= 1; destination--) // right to left of front side
            {
                rubiksCube[rowNum][destination][0] = leftCubes[index];
                index++;
            }
            // rightCubes (front to back)
            index = 0;
            for (int destination = size - 2; destination >= 1; destination--) // right to left of back side
            {
                rubiksCube[rowNum][destination][size-1] = rightCubes[index];
                index++;
            }

            /* side adjustments (top & bottom side not impact) */
            Cube[] corners = {frontLeft, frontRight, backRight, backLeft};
            Cube[][] otherThanCorners = {frontCubes, backCubes, leftCubes, rightCubes};
            for (int i = 0; i < 4; i++)
            {
                /* for corners */
                Cube cube = corners[i];
                // store sides as temps
                Side front = cube.getFront();
                Side back = cube.getBack();
                Side left = cube.getLeft();
                Side right = cube.getRight();
                // change
                cube.setLeft(back);
                cube.setBack(right);
                cube.setRight(front);
                cube.setFront(left);

                /* for cubes other than corners */
                for (int c = 0; c < otherThanCorners[i].length; c++)
                {
                    cube = otherThanCorners[i][c];
                    // store sides as temps
                    front = cube.getFront();
                    back = cube.getBack();
                    left = cube.getLeft();
                    right = cube.getRight();
                    // change
                    cube.setLeft(back);
                    cube.setBack(right);
                    cube.setRight(front);
                    cube.setFront(left);
                }
            }
            updateAllViews();
        }
    }
    // this method rotates the perspective rightward
    public void rotatePerspectiveRightward()
    {
        for (int r = 0; r < size; r++)
        {
            rowRotationRight(r);
        }
        updateAllViews();
    }

    public void colRotationUp(int colNum)
    {
        if (rubiksCube != null)
        {
            /* variables */
            // exclusive of corner cubes
            Cube[] frontCubes = new Cube[size-2];
            Cube[] backCubes = new Cube[size-2];
            Cube[] topCubes = new Cube[size-2];
            Cube[] bottomCubes = new Cube[size-2];
            for (int r = 0; r < frontCubes.length; r++)
            {
                frontCubes[r] = rubiksCube[r+1][colNum][0]; // going to the left side, left to right --> back to front
                backCubes[r] = rubiksCube[r+1][colNum][size-1]; // going to the right side, left to right --> back to front
            }
            for (int z = 0; z < topCubes.length; z++)
            {
                topCubes[z] = rubiksCube[0][colNum][z+1]; // going to the back side, front to back --> left to right
                bottomCubes[z] = rubiksCube[size-1][colNum][z+1]; // going to the front side, front to back --> left to right
            }
            // corner cubes
            Cube frontTop = rubiksCube[0][colNum][0]; // saving the front top corner based on the front view
            Cube frontBottom = rubiksCube[size-1][colNum][0]; // saving the front bottom corner based on the front view
            Cube backTop = rubiksCube[0][colNum][size-1]; // saving the back top corner based on the front view
            Cube backBottom = rubiksCube[size-1][colNum][size-1]; // saving the back bottom corner based on the front view

            /* changing position && changing sides */
            // corners
            rubiksCube[0][colNum][0] = frontBottom;
            rubiksCube[size-1][colNum][0] = backBottom;
            rubiksCube[0][colNum][size-1] = frontTop;
            rubiksCube[size-1][colNum][size-1] = backTop;
            // other than corners
            int index; // of the arrays (cubes other than corners)
            // frontCubes (top to bottom)
            index = 0;
            for (int destination = size - 2; destination >= 1; destination--) // back to front of top side
            {
                rubiksCube[0][colNum][destination] = frontCubes[index];
                index++;
            }
            // backCubes (top to bottom)
            index = 0;
            for (int destination = size - 2; destination >= 1; destination--) // back to front of bottom side
            {
                rubiksCube[size-1][colNum][destination] = backCubes[index];
                index++;
            }
            // topCubes (front to back)
            index = 0;
            for (int destination = 1; destination < size - 1; destination++) // top to bottom of back side
            {
                rubiksCube[destination][colNum][size-1] = topCubes[index];
                index++;
            }
            // bottomCubes (front to back)
            index = 0;
            for (int destination = 1; destination < size - 1; destination++) // top to bottom of front side
            {
                rubiksCube[destination][colNum][0] = bottomCubes[index];
                index++;
            }

            /* side adjustments (left and right side not impact) */
            Cube[] corners = {frontTop, frontBottom, backTop, backBottom};
            Cube[][] otherThanCorners = {frontCubes, backCubes, topCubes, bottomCubes};
            for (int i = 0; i < 4; i++)
            {
                /* for corners */
                Cube cube = corners[i];
                // store sides as temps
                Side front = cube.getFront();
                Side back = cube.getBack();
                Side top = cube.getTop();
                Side bottom = cube.getBottom();
                // change
                cube.setFront(bottom);
                cube.setBottom(back);
                cube.setBack(top);
                cube.setTop(front);

                /* for cubes other than corners */
                for (int c = 0; c < otherThanCorners[i].length; c++)
                {
                    cube = otherThanCorners[i][c];
                    // store sides as temps
                    front = cube.getFront();
                    back = cube.getBack();
                    top = cube.getTop();
                    bottom = cube.getBottom();
                    // change
                    cube.setFront(bottom);
                    cube.setBottom(back);
                    cube.setBack(top);
                    cube.setTop(front);
                }
            }
            updateAllViews();
        }
    }
    // this method rotates the perspective upward
    public void rotatePerspectiveUpward()
    {
        for (int c = 0; c < size; c++)
        {
            colRotationUp(c);
        }
        updateAllViews();
    }


    public void colRotationDown(int colNum)
    {
        if (rubiksCube != null)
        {
            /* variables */
            // exclusive of corner cubes
            Cube[] frontCubes = new Cube[size-2];
            Cube[] backCubes = new Cube[size-2];
            Cube[] topCubes = new Cube[size-2];
            Cube[] bottomCubes = new Cube[size-2];
            for (int r = 0; r < frontCubes.length; r++)
            {
                frontCubes[r] = rubiksCube[r+1][colNum][0]; // going to the left side, left to right --> back to front
                backCubes[r] = rubiksCube[r+1][colNum][size-1]; // going to the right side, left to right --> back to front
            }
            for (int z = 0; z < topCubes.length; z++)
            {
                topCubes[z] = rubiksCube[0][colNum][z+1]; // going to the back side, front to back --> left to right
                bottomCubes[z] = rubiksCube[size-1][colNum][z+1]; // going to the front side, front to back --> left to right
            }
            // corner cubes
            Cube frontTop = rubiksCube[0][colNum][0]; // saving the front top corner based on the front view
            Cube frontBottom = rubiksCube[size-1][colNum][0]; // saving the front bottom corner based on the front view
            Cube backTop = rubiksCube[0][colNum][size-1]; // saving the back top corner based on the front view
            Cube backBottom = rubiksCube[size-1][colNum][size-1]; // saving the back bottom corner based on the front view

            /* changing position && changing sides */
            // corners
            rubiksCube[0][colNum][0] = backTop;
            rubiksCube[size-1][colNum][0] = frontTop;
            rubiksCube[0][colNum][size-1] = backBottom;
            rubiksCube[size-1][colNum][size-1] = frontBottom;
            // other than corners
            int index; // of the arrays (cubes other than corners)
            // frontCubes (top to bottom)
            index = 0;
            for (int destination = 1; destination < size - 1; destination++) // front to back of bottom side
            {
                rubiksCube[size-1][colNum][destination] = frontCubes[index];
                index++;
            }
            // backCubes (top to bottom)
            index = 0;
            for (int destination = 1; destination < size - 1; destination++) // front to back of top side
            {
                rubiksCube[0][colNum][destination] = backCubes[index];
                index++;
            }
            // topCubes (front to back)
            index = 0;
            for (int destination = size - 2; destination >= 1; destination--) // bottom to top of front side
            {
                rubiksCube[destination][colNum][0] = topCubes[index];
                index++;
            }
            // bottomCubes (front to back)
            index = 0;
            for (int destination = size - 2; destination >= 1; destination--) // bottom to top of back side
            {
                rubiksCube[destination][colNum][size-1] = bottomCubes[index];
                index++;
            }

            /* side adjustments (left and right side not impact) */
            Cube[] corners = {frontTop, frontBottom, backTop, backBottom};
            Cube[][] otherThanCorners = {frontCubes, backCubes, topCubes, bottomCubes};
            for (int i = 0; i < 4; i++)
            {
                /* for corners */
                Cube cube = corners[i];
                // store sides as temps
                Side front = cube.getFront();
                Side back = cube.getBack();
                Side top = cube.getTop();
                Side bottom = cube.getBottom();
                // change
                cube.setFront(top);
                cube.setBottom(front);
                cube.setBack(bottom);
                cube.setTop(back);

                /* for cubes other than corners */
                for (int c = 0; c < otherThanCorners[i].length; c++)
                {
                    cube = otherThanCorners[i][c];
                    // store sides as temps
                    front = cube.getFront();
                    back = cube.getBack();
                    top = cube.getTop();
                    bottom = cube.getBottom();
                    // change
                    cube.setFront(top);
                    cube.setBottom(front);
                    cube.setBack(bottom);
                    cube.setTop(back);
                }
            }
            updateAllViews();
        }
    }
    // this method rotates the perspective downward
    public void rotatePerspectiveDownward()
    {
        for (int c = 0; c < size; c++)
        {
            colRotationDown(c);
        }
        updateAllViews();
    }


    public void faceRotationClockwise()
    {

    }
    public void faceRotationCounterclockwise()
    {

    }
}
