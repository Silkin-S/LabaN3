import java.io.PrintStream;
import java.util.Scanner;
public class Main1 {
    public static Scanner in = new Scanner(System.in);
    public static PrintStream out = System.out;
    static double minList(int n) {
        if (n == 0) {
            return 0;
        } else {
            double x = in.nextDouble();
            return x + averageList1(n - 1);
        }
    }




    public static void main(String[] args) {
        out.print(minList(6));
    }
}