import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] randomQueue;
    private int sz;
    // construct an empty randomized queue
    public RandomizedQueue() {
        randomQueue = (Item[]) new Object[1];
        sz = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (sz == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return sz;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (sz == randomQueue.length) {
            Item[] newRandomQueue = (Item[]) new Object[randomQueue.length * 2];
            for (int i = 0; i < randomQueue.length; i++) {
                newRandomQueue[i] = randomQueue[i];
            }
            randomQueue = newRandomQueue;
        }
        randomQueue[sz++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (sz == 0) throw new NoSuchElementException();
        int pos = StdRandom.uniformInt(0, sz);
        Item item = randomQueue[pos];
        randomQueue[pos] = randomQueue[sz - 1];
        randomQueue[sz - 1] = null;
        
        if (sz < randomQueue.length / 4) {
            Item[] newRandomQueue = (Item[]) new Object[randomQueue.length / 2];
            for (int i = 0; i < sz; i++) {
                newRandomQueue[i] = randomQueue[i];
            }
            randomQueue = newRandomQueue;
        }
        sz--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (sz == 0) throw new NoSuchElementException();
        int pos = StdRandom.uniformInt(0, sz);
        Item item = randomQueue[pos];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item>{
        private Item[] randomArr;
        private int cur = 0;

        QueueIterator() {
            randomArr = (Item[]) new Object[sz];

            for (int i = 0; i < randomArr.length; i++) {
                randomArr[i] = randomQueue[i];
            }
            
            for (int i = 1; i < randomArr.length; i++) {
                int swap = StdRandom.uniformInt(i + 1);
                Item tmp = randomArr[i];
                randomArr[i] = randomArr[swap];
                randomArr[swap] = tmp;
            }
        }
        @Override
        public boolean hasNext() {
            return cur < randomArr.length;
        }

        @Override 
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = randomArr[cur];
            cur++;
            return item;
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        Iterator<Integer> it0 = queue.iterator();
        Iterator<Integer> it1 = queue.iterator();
        while (it0.hasNext()) {
            System.out.println(it0.next());
        }
        System.out.println();
        while (it1.hasNext()) {
            System.out.println(it1.next());
        }
        System.out.println();
    }

}