import java.util.*;
import java.io.*;

public class Kickstart2019A_1 {
    public static void main(String[] args) {
        Kickstart2019A_1 o = new Kickstart2019A_1();
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int N = in.nextInt();
            int P = in.nextInt();
            int[] S = new int[N];
            for (int j = 0; j < N; j++) {
                S[j] = in.nextInt();
            }
            System.out.println("Case #" + i + ": " + o.training(S, N, P));
        }
    }

    public int training(int[] s, int n, int p) {
        Arrays.sort(s);
        int count = 0;
        for (int i = 0; i < p; i++) {
            count += s[i];
        }
        int min = s[p - 1] * p - count;
        for (int i = 1; i <= n - p; i++) {
            count = count - s[i - 1] + s[i + p - 1];
            if (s[i + p - 1] * p - count < min) {
                min = s[i + p - 1] * p - count;
            }
        }
        return min;
    }
}