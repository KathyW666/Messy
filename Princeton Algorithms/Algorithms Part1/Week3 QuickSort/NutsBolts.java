import java.util.Arrays;
import java.util.Comparator;

public class NutsBolts {
    private int[] nuts;
    private int[] bolts;

    public NutsBolts(int[] nuts, int[] bolts) {
        this.nuts = nuts;
        this.bolts = bolts;
    }

    public void sortNutsAndBolts(int[] nuts, int[] bolts) {
        if(nuts == null || bolts == null) return;
        if(nuts.length != bolts.length) return;

        qsort(nuts, bolts, 0, nuts.length -1);
    }

    private void qsort(int[] nuts, int[] bolts, int lo, int hi) {
        if (lo >= hi) return;
        int key = partition(nuts, bolts[lo], lo, hi);
        partition(bolts, nuts[key], lo, hi);
        qsort(nuts, bolts, lo, key - 1);
        qsort(nuts, bolts, key + 1, hi);
    }

    public static int partition(int[] a, int key, int lo, int hi) {
        int i = lo - 1, j = hi + 1;
        while (true) {
            while (a[++i] < key) {
                if (i == hi) break;
            }
            while (a[--j] >= key) {
                if (j == lo) break;
            }
            if (i >= j) break;
            exch(a, i, j);
        }
        return j;


    }

    public static int[] exch(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        return a;
    }

    public static void main(String[] args) {
        NutsBolts o = new NutsBolts(new int[]{4,2,3,5,1}, new int[]{2,4,1,3,5});
        o.sortNutsAndBolts(o.nuts, o.bolts);
        System.out.println(Arrays.toString(o.bolts));

    }
}
