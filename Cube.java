public class Cube {
    /* instance variables */
    // all side will be constantly changing when actions are taken
    private Side front; // the front side is always the one that is presented to the user (with the 2D perspective)
    // all other sides are named based on the front view
    private Side left;
    private Side right;
    private Side top;
    private Side bottom;
    private Side back;

    // constructor
    public Cube()
    {
        front = new Side("");
        left = new Side("");
        right = new Side("");
        top = new Side("");
        bottom = new Side("");
        back = new Side("");
    }

    /* methods */
    // accessor methods
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
    // mutator methods (besides "sides" array)
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
}
