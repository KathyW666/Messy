import edu.princeton.cs.algs4.BST;

public class TestBST {
    public static void main(String[] args) {
        String test = "S E A R C H E X A M P L E";
        String[] keys = test.split(" ");
        int n = keys.length;
        BST<String, Integer> st = new BST<>();
        for (int i = 0; i < n; i++)
            st.put(keys[i], i);
        System.out.println("Size = " + st.size());
        System.out.println("Max = " + st.max());
        System.out.println("Min = " + st.min());
        System.out.println();

        //中序遍历所有key
        System.out.println("Testing Select");
        System.out.println("-------------------------");
        for (String s : st.keys())
            System.out.println(s + " " + st.get(s));
        System.out.println();

        //测试rank，floor，ceiling
        System.out.println("key rank floor flor2 ceil");
        System.out.println("-------------------------");
        for (char i = 'A'; i <= 'Z'; i++) {
            String s = i + "";
            System.out.printf("%2s %4d %4s %4s %4s\n", s, st.rank(s), st.floor(s), st.floor2(s), st.ceiling(s));
        }
        System.out.println();

        // 测试 range search and range count
        String[] from = { "A", "Z", "X", "0", "B", "C" };
        String[] to   = { "Z", "A", "X", "Z", "G", "L" };
        System.out.println("range search");
        StdOut.println("-------------------");
        for (int i = 0; i < from.length; i++) {
            System.out.printf("%s-%s (%2d) : ", from[i], to[i], st.size(from[i], to[i]));
            for (String s : st.keys(from[i], to[i]))
                System.out.print(s + " ");
            System.out.println();
        }
        System.out.println();

        // 删除最小的key
        for (int i = 0; i < st.size() / 2; i++) {
            st.deleteMin();
        }
        System.out.println("After deleting the smallest " + st.size() / 2 + " keys");
        System.out.println("--------------------------------");
        for (String s : st.keys())
            System.out.println(s + " " + st.get(s));
        System.out.println();

        // 删除所有剩余元素
        while (!st.isEmpty()) {
            st.delete(st.select(st.size() / 2));
        }
        System.out.println("After deleting the remaining keys");
        System.out.println("--------------------------------");
        for (String s : st.keys())
            System.out.println(s + " " + st.get(s));
        System.out.println();

        System.out.println("After adding back the keys");
        System.out.println("--------------------------------");
        for (int i = 0; i < n; i++)
            st.put(keys[i], i);
        for (String s : st.keys())
            System.out.println(s + " " + st.get(s));
        System.out.println();

    }

}
