package thread.capter04;

public class StrTest {
    public static void main(String[] args) {
        String s = "hello";
        String t = "he"+new String("llo");
        System.err.println(s==t);
    }
}
