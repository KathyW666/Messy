public class SelectionTwoArrays {
    /**
     * Selection in two sorted arrays
     */

    //Test
    public static void main(String[] args) {
        Integer[] a = new Integer[]{1,8,9,12,45,553};
        Integer[] b = new Integer[]{2,3};
        SelectionTwoArrays o = new SelectionTwoArrays();
        System.out.println(o.select(a,b,7).toString());

    }

    /**
     * MergeSort-Like method, the sort items number is subjected to k
     *
     * @param a array1
     * @param b array2
     * @param k the kth number
     */

    public Comparable select(Comparable[] a, Comparable[] b, int k) {
        int n1 = a.length, n2 = b.length;
        if (n1 == 0 || n2 == 0) throw new IllegalArgumentException("null array");
        if (k < 0 || k > n1 + n2) throw new IllegalArgumentException("k out of range");
        Comparable[] temp = new Comparable[k];
        int i = 0, j = 0;
        for(int count = 0; count < k; count++) {
            if (i >= n1) temp[count] = b[j++];
            else if (j >= n2) temp[count] = a[i++];
            else if (b[j].compareTo(a[i]) < 0) temp[count] = b[j++];
            else temp[count] = a[i++];
        }
        return temp[k-1];
    }
}
