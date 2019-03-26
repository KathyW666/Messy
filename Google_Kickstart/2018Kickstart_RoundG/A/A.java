import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class A {
    public int triplet(int[] a) {
        int count = 0;
        for (int x = 0; x < a.length - 2; ++x) {
            for (int y = x + 1; y < a.length - 1; ++y) {
                for (int z = y + 1; z < a.length; ++z) {
                    if (a[y] > 447 && a[z] > 447 && a[x] > 447)
                        continue;
                    if (a[x] == a[y] * a[z] || a[y] == a[x] * a[z] || a[z] == a[x] * a[y])
                        count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        A o = new A();
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int n = in.nextInt();
            int[] a = new int[n];
            for (int j = 0; j < n; ++j) {
                a[j] = in.nextInt();
            }
            System.out.println("Case #" + i + ": " + o.triplet(a));
        }
    }
}
