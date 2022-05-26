public class Tester {
    public static void main(String[] args) {
        Player player = new Player("RingSoul");
        RubiksCube rc = new RubiksCube(4, player); // rotated already

        System.out.println("------------------------------------------------");
        for (int i = 0; i < 6; i++)
        {
            rc.changePerspectiveLeftward();
            rc.displayFrontView();
            System.out.println("------------------------------------------------");
        }
        for (int i = 0; i < 9; i++)
        {
            rc.changePerspectiveRightward();
            rc.displayFrontView();
            System.out.println("------------------------------------------------");
        }
    }
}
