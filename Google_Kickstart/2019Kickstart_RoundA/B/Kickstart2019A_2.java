import java.util.*;
import java.io.*;

public class Kickstart2019A_2 {
    public static void main(String[] args) {
        Kickstart2019A_2 o = new Kickstart2019A_2();
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            Set<Integer> set = new HashSet<>();
            int R = in.nextInt();
            int C = in.nextInt();
            int[][] board = new int[R][C];
            for (int r = 0; r < R; r++) {
                String input = in.next();
                char[] inputCs = input.toCharArray();
                int c = 0;
                for (char ch : inputCs) {
                    board[r][c] = ch - '0';
                    if (board[r][c] == 1) {
                        set.add(r * C + c);
                    }
                    c++;
                }
            }
            o.parcel(board, set, R, C);
            System.out.println("Case #" + i + ": " + o.parcel(board, set, R, C));
        }
    }

    public int parcel(int[][] board, Set<Integer> set, int R, int C) {
        int max = 0;
        int maxI = 0, maxJ = 0, curr;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (board[i][j] == 1) curr = 0;
                else {
                    curr = minManDist(set, i, j, C);
                }
                if (curr > max) {
                    max = curr;
                    maxI = i;
                    maxJ = j;
                }

            }
        }
        if (maxI < R && maxJ < C) {
            board[maxI][maxJ] = 1;
            set.add(maxI * C + maxJ);
        }
        return max;
    }

    public int minManDist(Set<Integer> set, int i, int j, int C) {
        int min = Integer.MAX_VALUE;
        for (Integer num : set) {
            int r = num / C;
            int c = num % C;
            int dist = Math.abs(r - i) + Math.abs(c - j);
            min = dist < min ? dist : min;
        }
        return min;
    }
}