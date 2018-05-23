package Util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class TopKHeap<E> implements Iterable<E> {
    private PriorityQueue<E> queue;
    private int maxSize; //堆的最大容量
    private Comparator<E> comparator;

    public TopKHeap(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalStateException();
        }
        this.maxSize = maxSize;
        this.queue = new PriorityQueue<>(maxSize);
    }

    public TopKHeap(int maxSize, Comparator<E> comparator) {
        if (maxSize <= 0) {
            throw new IllegalStateException();
        }
        this.comparator = comparator;
        this.maxSize = maxSize;
        this.queue = new PriorityQueue<>(maxSize, comparator);
    }

    public static void main(String[] args) {
        int[] array = {4, 5, 1, 6, 2, 7, 3, 8};
        TopKHeap pq = new TopKHeap<Integer>(4);
        for (int n : array) {
            pq.add(n);
        }
        //System.out.println(pq.sortedList());
    }

//    public List<E> sortedList() {
//        List<E> list = new ArrayList<>(queue);
//        Collections.sort(list);
//        return list;
//    }

    public void add(E e) {
        if (queue.size() < maxSize) {
            queue.add(e);
        } else {
            E peek = (E) queue.peek();
//            if (e.compareTo(peek) > 0) {
//                queue.poll();
//                queue.add(e);
//            }
            if (comparator.compare(e, peek) > 0) {
                queue.poll();
                queue.add(e);
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }
}