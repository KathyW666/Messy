import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int n = 0;

    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = s[i];
        s = copy;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == s.length) resize(2 * s.length);
        s[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int ranId = StdRandom.uniform(n);
        Item item = s[ranId];
        s[ranId] = s[n-1];
        s[--n] = null;
        if (n > 0 && n == s.length / 4) resize(s.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int id = StdRandom.uniform(n);
        return s[id];
    }

    public Iterator<Item> iterator() {
        return new RandQueIterator();
    }

    private class RandQueIterator implements Iterator<Item> {
        private final int[] randomIndex = StdRandom.permutation(n);
        private int i = 0;

        public boolean hasNext() { return i < n; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return s[randomIndex[i++]];
        }
    }
}
