public class Solution {
    public boolean Find(int target, int [][] array) {
        int row = array.length;
        int len = array[0].length;
        for (int i = 0; i < row; i++) {
            if (array[i][0] > target || array[i][len-1] <target) continue;
            if (BinarySearch(target, array[i], len)) break;
        }
        return false;
    }

    public boolean BinarySearch(int tg, int [] a, int hi) {
        int lo = 0;
        int mid = (lo + hi) / 2;
        while (hi > lo) {
            if (tg < a[mid]) hi = mid;
            if (tg > a[mid]) lo = mid;
            return true;
        }
        return false;

    }

    public static void main(String[] args) {
        int a[][] = new int[][] {{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
        Solution s = new Solution();
        System.out.println(s.Find(5,a));
    }
}

