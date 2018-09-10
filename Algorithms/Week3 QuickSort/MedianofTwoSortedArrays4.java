public class MedianofTwoSortedArrays4 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return ArrayMid(nums2);
        if (nums2 == null || nums2.length == 0) return ArrayMid(nums1);
        int m = nums1.length, n = nums2.length;
        int aux_len = (m + n) / 2 + 1;
        int[] aux = new int[aux_len];
        int i = 0, j = 0;
        for (int k = 0; k < aux_len; k++) {
            if (i >= m) aux[k] = nums2[j++];
            else if (j >= n) aux[k] = nums1[i++];
            else if (nums2[j] < nums1[i]) aux[k] = nums2[j++];
            else aux[k] = nums1[i++];
        }
        if ((m + n) % 2 == 1)  return aux[aux_len - 1] / 1.0;
        else return (aux[aux_len - 1] + aux[aux_len - 2]) / 2.0;
    }

    public double ArrayMid(int[] nums) {
        int len = nums.length;
        if (len % 2 == 1)  return nums[len / 2];
        else  return (nums[len / 2 - 1] + nums[len / 2]) / 2.0;
    }
}
