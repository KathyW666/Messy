import java.util.*;
import java.io.*;

public class Kickstart2019A_2_2 {
    public static void main(String[] args) {
        Kickstart2019A_2_2 o = new Kickstart2019A_2_2();
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int R = in.nextInt();
            int C = in.nextInt();
            int[][] board = new int[R][C];
            for (int r = 0; r < R; r++) {
                String input = in.next();
                char[] inputCs = input.toCharArray();
                int c = 0;
                for (char ch : inputCs) {
                    board[r][c] = ch - '0';
                    c++;
                }
            }
            int[] max = maxtime(board, R, C);
            board[max[1]][max[2]] = 1;
            int[] res = maxtime(board, R, C);

            System.out.println("Case #" + i + ": " + res[0]);
        }
    }

    public static int[] maxtime(int[][] array, int R, int C) {
        int[][] dp = new int[R][C];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (array[i][j] == 1) {
                    fill(array, dp, i, j, 0);
                }
            }
        }
        int[] max = new int[3];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (max[0] < dp[i][j]) {
                    max[0] = dp[i][j];
                    max[1] = i;
                    max[2] = j;
                }
            }
        }
        return max;
    }

    public static void fill(int[][] array, int[][] dp, int i, int j, int k) {
        if (i < 0 || j < 0 || i >= array.length || j >= array[0].length || (array[i][j] == 1 && k != 0)) {
            return;
        }
        dp[i][j] = k;
        if (i + 1 < array.length && dp[i][j] + 1 < dp[i + 1][j]) {
            fill(array, dp, i + 1, j, k + 1);
        }
        if (i - 1 >= 0 && dp[i][j] + 1 < dp[i - 1][j]) {
            fill(array, dp, i - 1, j, k + 1);
        }
        if (j + 1 < array[0].length && dp[i][j] + 1 < dp[i][j + 1]) {
            fill(array, dp, i, j + 1, k + 1);
        }
        if (j - 1 >= 0 && dp[i][j] + 1 < dp[i][j - 1]) {
            fill(array, dp, i, j - 1, k + 1);
        }
    }
}