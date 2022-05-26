public class Side {
    // instance variables
    private boolean isVisible;
    private String representation;

    // constructor
    public Side(boolean isVisible, String rep)
    {
        this.isVisible = isVisible;
        if (isVisible)
            representation = rep;
    }

    /* methods */
    // accessor methods
    public boolean isVisible() {
        return isVisible;
    }
    public String getRepresentation() {
        return representation;
    }
    // mutator methods
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    public void setRepresentation(String representation) {
        this.representation = representation;
    }
}
