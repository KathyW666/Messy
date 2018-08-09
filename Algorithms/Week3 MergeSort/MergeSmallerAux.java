import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class MergeSmallerAux {
    private MergeSmallerAux() {}

    private static void merge(Comparable[] a) {
        int n = a.length / 2;
        Comparable[] aux = new Comparable[n];
        for(int i = 0; i < n; i++) {
            aux[i] = a[i];
        }
        int i = 0;
        int j = n;
        for (int k = 0; k < 2 * n; k++) {
            if (i >= n) break;
            else if (j >= 2 * n) a[k] = aux[i++];
            else if (less(a[j], aux[i])) a[k] = a[j++];
            else a[k] = aux[i++];
        }
    }
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void main(String[] args) {
        MergeSmallerAux o = new MergeSmallerAux();
        int n = 10;
        int[] a1 = new int[n];
        int[] a2 = new int[n];
        for (int i = 0; i < n; i++) {
            a1[i] = StdRandom.uniform(10);
            a2[i] = StdRandom.uniform(10);
        }
        System.out.println(Arrays.toString(a1));
        System.out.println(Arrays.toString(a2));
        Arrays.sort(a1);
        Arrays.sort(a2);
        System.out.println(Arrays.toString(a1));
        System.out.println(Arrays.toString(a2));
        Integer[] a = new Integer[2*n];
        for (int i = 0; i < n; i++) {
            a[i] = a1[i];
            a[i+n] = a2[i];
        }
        System.out.println(Arrays.toString(a));
        merge(a);
        System.out.println(Arrays.toString(a));
    }

}