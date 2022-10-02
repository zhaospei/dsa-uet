import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    // construct an empty deque
    private Node first;
    private Node last;
    private int sz;

    private class Node {
        private Item item;
        private Node next;
        private Node pre; 
    }

    public Deque() {
        sz = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        if (sz == 0) return true;
        else return false;
    }

    // return the number of items on the deque
    public int size() {
        return sz;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst == null) last = first;
        else oldFirst.pre = first;
        sz++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.pre = oldLast;
        if (oldLast == null) first = last;
        else oldLast.next = last;
        sz++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        if (first.next != null) {
            first = first.next;
            first.pre = null;
        } else{
            first = null;
            last = null;
        }
        sz--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        if (last.pre != null) {
            last = last.pre;
            last.next = null;   
        } else{
            first = null;
            last = null;
        }
        sz--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>{
        private Node cur = first;
        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override 
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = cur.item;
            cur = cur.next;
            return item;
        }
    }
    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        System.out.println(deque.isEmpty());
        deque.addFirst(1);
        deque.removeLast();
        deque.addLast(2);
        deque.addFirst(3);
        System.out.println(deque.size());
        Iterator<Integer> iter = deque.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

}