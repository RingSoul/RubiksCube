import java.util.ArrayList;

public class Cube {
    /* instance variables */
    private int visibleSides;
    // all side will be constantly be changing when actions are taken
    private Side front; // the front side is always the one that is presented to the user (with the 2D perspective)
    // all other sides or orientations are based on the front view
    private Side left;
    private Side right;
    private Side top;
    private Side bottom;
    private Side back;
    private Side[] sides; // for the convenience of accessing all sides

    // constructor
    public Cube()
    {
        // initialize all sides into not visible (will be changed by the setCorrespondingVisibility method)
        // and without representations or tokens (will be randomly assigned the assignTokens method of RubiksCube class)
        visibleSides = 0;
        front = new Side(false, "");
        left = new Side(false, "");
        right = new Side(false, "");
        top = new Side(false, "");
        bottom = new Side(false, "");
        back = new Side(false, "");
        sides = new Side[]{front, left, right, top, bottom, back};
    }

    /* methods */
    // accessor methods
    public int getVisibleSides() {
        return visibleSides;
    }
    public Side getTop() {
        return top;
    }
    public Side getBack() {
        return back;
    }
    public Side getBottom() {
        return bottom;
    }
    public Side getFront() {
        return front;
    }
    public Side getLeft() {
        return left;
    }
    public Side getRight() {
        return right;
    }
    public Side[] getSides() {
        return sides;
    }
    // mutator methods (besides "sides" and "visibleSides")
    public void setBack(Side back) {
        this.back = back;
    }
    public void setBottom(Side bottom) {
        this.bottom = bottom;
    }
    public void setFront(Side front) {
        this.front = front;
    }
    public void setLeft(Side left) {
        this.left = left;
    }
    public void setRight(Side right) {
        this.right = right;
    }
    public void setTop(Side top) {
        this.top = top;
    }

    // set the visibility of the block to true based on its position in the Rubik's Cube
    // first row and last row --> top and bottom
    // first column and last column --> left and right
    // z = 0 and z = size - 1 --> front and back
    // size will be always be the instance variable "size" in the RubiksCube class
    public void setCorrespondingVisibility(int r, int c, int z, int size)
    {
        if (r == 0)
        {
            top.setVisible(true);
            visibleSides++;
        }
        if (r == size - 1)
        {
            bottom.setVisible(true);
            visibleSides++;
        }
        if (c == 0)
        {
            left.setVisible(true);
            visibleSides++;
        }
        if (c == size - 1)
        {
            right.setVisible(true);
            visibleSides++;
        }
        if (z == 0)
        {
            front.setVisible(true);
            visibleSides++;
        }
        if (z == size - 1)
        {
            back.setVisible(true);
            visibleSides++;
        }
    }
}
