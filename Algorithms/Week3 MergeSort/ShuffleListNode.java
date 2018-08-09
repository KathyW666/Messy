import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class ShuffleListNode<T extends Comparable<T>> implements Iterable<T> {
    private Node first = null;
    private Node last = null;
    private int n;

    public class Node {
        T item;
        Node next;
    }

    private boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T t = current.item;
            current = current.next;
            return t;
        }
    }

    @Override
    public String toString() {
        Iterator<T> iter = iterator();
        String ret = iter.next().toString();
        while (iter.hasNext())
            ret += ", " + iter.next().toString();
        return ret;
    }

    public void add(T t) {
        Node node = new Node();
        node.item = t;
        node.next = null;
        if (first == null && last == null) {
            first = node;
            last = node;
        } else if (first != null && first == last) {
            first.next = node;
            last = node;
        } else {
            last.next = node;
            last = node;
        }
        n++;
    }

    public void mergeSort() {
        first = sort(first);
    }

    private Node sort(Node head) {
        if (head == null || head.next == null) return head;
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node left = head;
        Node right = slow.next;
        slow.next = null; //将左右链表分开
        left = sort(left);
        right = sort(right);
        return merge(left, right);
    }

    private Node merge(Node left, Node right) {
        Node aux = new Node();
        Node l = left;
        Node r = right;
        Node current = aux;
        while (l != null && r != null) {
            int rand = StdRandom.uniform(2);
            if (rand == 0) { //如果随机数选为0，则从左侧选取元素
                current.next = l;
                current = current.next;
                l = l.next;
            } else {
                current.next = r;
                current = current.next;
                r = r.next;
            }
        }
        if (l != null) current.next = l;
        else if (r != null) current.next = r;
        return aux.next;
    }

    /**
    private Node merge(Node left, Node right) {
        //System.out.println("left="+left.element+",right="+right.element);
        Node aux = new Node(); //需要耗费logn的额外空间
        Node l = left;
        Node r = right;
        Node current = aux;
        while (l != null && r != null) {
            if (less(r.element, l.element)) {
                current.next = r;
                current = current.next;
                r = r.next;
            } else {
                current.next = l;
                current = current.next;
                l = l.next;
            }
        }
        if (l != null) current.next = l; // 如果左侧没遍历完，将其连接至current后
        else if (r != null) current.next = r; //如果右侧没遍历完，将其连接至current后
        return aux.next; //返回归并好的链表
    }
    **/

    public static void main(String[] args) {
        ShuffleListNode<Integer> sll = new ShuffleListNode<>();
        sll.add(1);
        sll.add(2);
        sll.add(11);
        sll.add(9);
        sll.add(10);
        sll.add(4);
        sll.add(7);
        System.out.println(sll);
        sll.mergeSort();
        System.out.println(sll);
    }
}
