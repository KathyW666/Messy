djjimport edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;

/** ********************************************************************************
 * Week4: Interview Questions: Priority Queues
 *  Dynamic Median
 *  使用两个堆维护中位数，这里临界条件"="在MaxPQ上
 *  judge() 存在边界bug
 *  Reference：1 ForeseeMark https://blog.csdn.net/wr339988/article/details/55813202
 *             2 zerods-seu  https://blog.csdn.net/zerodshei/article/details/54346425
 ***********************************************************************************/

public class DynamicMedian {
    private MinPQ minPQ = new MinPQ();
    private MaxPQ maxPQ = new MaxPQ();
    public int num = 0;

    public DynamicMedian() {}

    public void insert(int n) {
        if (num == 0) {
            minPQ.insert(n);
            num++;
            return;
        }
        if (num == 1) {
            maxPQ.insert(n);
            num++;
            return;
        }

        if (n <= (int) maxPQ.max()) maxPQ.insert(n);
        else minPQ.insert(n);

        judge();
        num++;
    }

    public int nums() {
        return num;
    }

    public void delMid() {
        judge();
        maxPQ.delMax();
        num--;
    }

    /** ****************************************************************************
     * 2018-09-30
     * 判断: 若两个堆的元素个数>1, 则将大堆的顶部元素放入小堆中重排
     * 【2】中直接以大堆的顶部元素作为中位数，可能会出现边界情况：[2,3,1]
     *  delMid() 返回 3 ------- BUG
     *
     *      minPQ   maxPQ
     *        2       3
     *              1
     * 2018-10-09 update
     * +立flag 解决边界调节的bug
     *******************************************************************************/
    private void judge() {
        if (num == 0) throw new NullPointerException();
        boolean flag = false;
        int subVal = maxPQ.size() - minPQ.size();
        while (Math.abs(subVal) >= 1) {
            if (subVal == 1 && flag) break;
            if (subVal < -1) maxPQ.insert(minPQ.delMin());
            if (subVal == -1) {
                maxPQ.insert(minPQ.delMin());
                flag = true;
            }
            if (subVal > 1) minPQ.insert(maxPQ.delMax());
            subVal = maxPQ.size() - minPQ.size();
        }
    }

    public int queryMid() {
        return (int) maxPQ.max();
    }

    public static void main(String[] args) {
        DynamicMedian dm = new DynamicMedian();
        dm.insert(4);
        dm.insert(3);
        dm.insert(5);
        System.out.println(dm.queryMid());
        dm.delMid();
        dm.insert(10);
        dm.insert(2);
        System.out.println(dm.queryMid());
    }
}
