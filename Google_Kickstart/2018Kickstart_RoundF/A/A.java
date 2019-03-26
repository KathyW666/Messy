import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class A {

    private int anagNums(String A, String B, int n) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int len = j - i + 1;
                String subA = A.substring(i, j + 1);
                for (int k = 0; k < n - len + 1; k++) {
                    String subB = B.substring(k, k + len);
                    if (isAnag(subA, subB, len)) {
                        count++;
                        break;
                    }
                }

            }
        }
        return count;
    }

    private boolean isAnag(String subA, String subB, int len) {
        char[] charsA = subA.toCharArray();
        char[] charsB = subB.toCharArray();
        Arrays.sort(charsA);
        Arrays.sort(charsB);
        for (int i = 0; i < len; i++) {
            if (charsA[i] != charsB[i]) return false;
        }
        return true;

    }

    public static void main(String[] args) {
//        Solution o = new Solution();
//        String A = "ABB";
//        String B = "BBA";
//        System.out.println(o.anagNums(A, B, 3));
        A o = new A();
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int n = in.nextInt();
            String A = in.next();
            String B = in.next();
            System.out.println("Case #" + i + ": " + o.anagNums(A, B, n));
        }
    }
}
