public class Elementary_Symbol_Tables {
    private class Node {
        private int key;
        private int val;
        private Node left;
        private Node right;
        private int N;

        public Node(int key, int val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public class Q1 {
        /**
         Question 1
         Java autoboxing and equals(). Consider two double values a and b and their corresponding Double values x and y.
         Find values such that (a == b) is true but x.equals(y) is false.
         Find values such that (a == b) is false but x.equals(y) is true.
         */

        /**
         1. a=0.0, b=-0.0
         2. a=b=Double.NaN
         */
    }

    public class Q2 {
        /**
         * 判断一个树是不是二叉搜索树
         */
        public boolean checkBST(Node p) {
            if (p == null) return true;
            if (p.left.key >= p.key || p.right.key <= p.key) return false;
            else return checkBST(p.left) && checkBST(p.right);
        }
    }

    public class Q3 {
        /**
         * O(1)空间中序遍历二叉搜索树
         */
        public void inorder(Node root) {
            if (root == null) return;
            boolean flag = true;
            if (root.left != null) inorder(root.left);
            else {
                System.out.println(root.key);
                if (root.right == null) return;
                else {
                    inorder(root.right);
                    flag = false;
                }
            }
            if (flag) {
                System.out.println(root.key);
                if (root.right != null) inorder(root.right);
            }
        }
    }


    public static void main(String[] args) {
        double a = 0.0;
        double b = -0.0;
        Double x = 0.0;
        Double y = -0.0;
        System.out.println(a == b);           //true
        System.out.println(x.equals(y));    //false
        a = Double.NaN;
        b = Double.NaN;
        x = a;
        y = b;
        System.out.println(a == b);         //false
        System.out.println(x.equals(y));    //true


    }
}
