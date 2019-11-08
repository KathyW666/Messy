import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;

public class DecimalDominants {
    /**
     * Decimal Dominants. Application of QuickSort.
     * 9 containers method is suggested, however here using ArrayList.
     * Some bugs exits:
     * 1. When the length of input is small.
     * 2. Wrong elements appear.
     *
     */

    //Test
    public static void main(String[] args) {
        DecimalDominants o = new DecimalDominants();
        Integer[] a = new Integer[]{9,11,9,7,8,9,9,9,9,9,1,11,1,1,8,8,
                8,8,1,1,2,2,2,2,2,2,2,2,2};
        System.out.println(o.qsort3(a));
    }

    public ArrayList<Integer> qsort3(Integer[] a) {
        StdRandom.shuffle(a);
        ArrayList<Integer> tg = new ArrayList<>();
        return qsort3(tg, a, 0, a.length-1);
    }

    public ArrayList<Integer> qsort3(ArrayList<Integer> tg, Integer[] a, int lo, int hi) {
        if (hi <= lo) return tg;
        int lt = lo, i = lo + 1, gt = hi;
        int cont = 1;
        while (i <= gt) {
            if (a[i].compareTo(a[lo]) < 0) exch(a, lt++, i++);
            else if (a[i].compareTo(a[lo]) > 0) exch(a, i, gt--);
            else {
                cont++;
                i++;
            }
        }
        if (cont > Math.floor(a.length / 10)) tg.add(a[lo]);
        qsort3(tg, a, lo,lt-1);
        qsort3(tg, a,gt+1, hi);
        return tg;
    }

    public static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}
