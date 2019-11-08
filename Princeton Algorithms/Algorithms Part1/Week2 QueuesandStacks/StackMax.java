
public class StackMax {
    private int N;
    private Node first;
    private Node max;

    private class Node {
        private double item;
        private Node next;
    }

    public StackMax() {
        N = 0;
        first = null;
        max = null;
    }

    public double getMax() {
        return max.item;
    }

    public void push(double item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        N++;
        if (item >= getMax()) {
            Node oldmax = max;
            max = new Node();
            max.next = oldmax;
        }
    }

    public double pop() {
        double tmp = first.item;
        first = first.next;
        N--;
        if (tmp == getMax()) {
            max = max.next;
        }
        return tmp;
    }

}
