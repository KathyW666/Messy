import edu.princeton.cs.algs4.Stack;

public class QueueTwoStacks<Item> {
    Stack<Item> in = new Stack<>();
    Stack<Item> out = new Stack<>();

    public boolean isEmpty() {
        return in.isEmpty() && out.isEmpty();
    }

    public void enqueue(Item item) throws NullPointerException {
        if (item == null)
            throw new NullPointerException();
        in.push(item);
    }

    /***
     * in:  1|1|0|1|null|null|...
     *      v-----^
     * out: 1|0|1|1| --> pop() --> 1|0|1|
     * @return the top of the queue
     * @throws NoSuchMethodException
     */
    public Item dequeue() throws NullPointerException {
        if (isEmpty())
            throw new NullPointerException();
        if (out.isEmpty())
            while (!in.isEmpty())
                out.push(in.pop());
        return out.pop();
    }

    public static void main(String[] args) {
        QueueTwoStacks<Integer> queue = new QueueTwoStacks<>();
        System.out.println(queue.isEmpty());
        for (int i = 0; i < 5; i++) {
            queue.enqueue(i);
        }
        System.out.println(queue.isEmpty());
        while (!queue.isEmpty())
            System.out.print(queue.dequeue()+" ");
    }
}

