import edu.princeton.cs.algs4.StdOut;

import java.util.*;
public class Taxicab {

    public boolean find(int a) {
        int count = 0;
        int cbrt = (int) Math.cbrt(a);   // 小于等于该数字的立方根
        for (int i = 1; i <= cbrt; i++) {

            int diff = a - (i * i * i);
            int a1 = (int) Math.cbrt(diff);
            if (a1 == Math.cbrt(diff)) count++;
            if (count > 2) return true;
        }
        return false;
    }

    public int[] Numb(int a) {
        int count = 0;
        int[] nums = new int[4];
        int cbrt = (int) Math.cbrt(a);   // 小于等于该数字的立方根
        for (int i = 1; i <= cbrt; i++) {
            int diff = a - (i * i * i);
            int a1 = (int) Math.cbrt(diff);
            if (a1 == Math.cbrt(diff)) {
                count++;
                if (count == 1) {
                    nums[0] = i;
                    nums[1] = a1;
                }
                if (count == 2) {
                    nums[2] = i;
                    nums[3] = a1;
                }
            }
            if (count > 2) return nums;
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Taxicab tc = new Taxicab();
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int i = 0, k = 1;
        while (i < n) {
            if (tc.find(k)) {
                i++;
                System.out.println(i + " th ramanujan number is " + k);
                System.out.println(tc.Numb(k)[0] + " " + tc.Numb(k)[1]
                        + " & " + tc.Numb(k)[2] + " " + tc.Numb(k)[3]);
            }
            k++;
        }
        scan.close();
    }
}


