import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

    public double banNum(List<String> seq, int n) {
        seq.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                for (int i = 0, j = 0; i < o1.length() && j < o2.length(); i++, j++) {
                    if (o1.charAt(i) < o2.charAt(j)) return -1;
                    else if (o1.charAt(i) > o2.charAt(j)) return 1;
                }
                if (o1.length() < o2.length()) return -1;
                else if (o1.length() > o2.length()) return 1;
                else return 0;
            }
        });
        for (int j = 0; j < seq.size() - 1; j++) {
            String fir = seq.get(j), sec = seq.get(j+1);
            if (fir.length() <= sec.length() && fir.equals(sec.substring(0, fir.length()))) {
                seq.remove(j+1);
                j--;
            }
        }
        double binNum = 0.0;
        for (int k = 0; k < seq.size(); k++) {
            binNum += Math.pow(2, n - seq.get(k).length());
        }
        return binNum;
    }

    public static void main(String[] args) {
        Solution o = new Solution();
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int n = in.nextInt();
            int p = in.nextInt();
            double total = Math.pow(2, n);
            List<String> seq = new ArrayList<>();
            for (int j = 0; j < p; ++j) {
                seq.add(in.next());
            }
            total -= o.banNum(seq, n);
            System.out.println("Case #" + i + ": " + total);
        }
    }
}