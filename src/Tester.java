public class Tester {
    public static void main(String[] args) {
        String abc = "A/B/C";
        String[] arr = abc.split("/");
        for (String str : arr)
        {
            System.out.println(str);
        }
    }
}
