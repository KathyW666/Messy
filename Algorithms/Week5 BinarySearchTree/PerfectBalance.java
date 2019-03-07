import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;

public class PerfectBalance {
    /**
     * 编写一段程序，用一组键构造一课和二分查找等价的二叉查找树。
     * 也就是说，在这颗树中查找任意键所产生的比较序列和在这组键中使用二分查找所产生的比较序列完全相同。
     * Hint：把中值放在根上
     * 使用方法：
     *  Compilation:  javac PerfectBalance.java
     *  Execution:    java PerfectBalance < input.txt
     *  Dependencies: StdOut.java
     */

    // 先决条件：a[]中无重复元素
    private static void perfect(BST bst, String[] a) {
        Arrays.sort(a);
        perfect(bst, a, 0, a.length - 1);
        System.out.println();
    }

    // 先决条件：a[]已排序
    private static void perfect(BST bst, String[] a, int lo, int hi) {
        if (hi < lo) return;
        int mid = lo + (hi - lo) / 2;
        bst.put(a[mid], mid);
        System.out.println(a[mid] + " ");
        perfect(bst, a, lo, mid - 1);
        perfect(bst, a, mid + 1, hi);
    }

    public static void main(String[] args) {
        String[] words = StdIn.readAllStrings();
        BST<String, Integer> bst = new BST<>();
        perfect(bst, words);
    }

}
