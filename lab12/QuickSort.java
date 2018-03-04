import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     * <p>
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item : q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /**
     * Returns a random item from the given queue.
     */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted A Queue of unsorted items
     * @param pivot    The item to pivot on
     * @param less     An empty Queue. When the function completes, this queue will contain
     *                 all of the items in unsorted that are less than the given pivot.
     * @param equal    An empty Queue. When the function completes, this queue will contain
     *                 all of the items in unsorted that are equal to the given pivot.
     * @param greater  An empty Queue. When the function completes, this queue will contain
     *                 all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        // Your code here!
        Iterator<Item> iter = unsorted.iterator();
        while (iter.hasNext()) {
            Item input = iter.next();
            Comparable it = input;
            if (it.compareTo(pivot) < 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                less.enqueue(input);
            } else if (it.compareTo(pivot) == 0) {
                equal.enqueue(input);
            } else {
                greater.enqueue(input);
            }
        }
/*        while (!unsorted.isEmpty()) {
            Comparable it = unsorted.peek();
            if (it.compareTo(pivot) < 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                less.enqueue(unsorted.dequeue());
            } else if (it.compareTo(pivot) == 0) {
                equal.enqueue(unsorted.dequeue());
            } else {
                greater.enqueue(unsorted.dequeue());
            }
        }*/
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        // Your code here!
        Queue<Item> sorted = new Queue<Item>();
        if (items.size() <= 1) {
            return items;
        }
        Queue<Item> xless = new Queue<Item>();
        Queue<Item> xequal = new Queue<Item>();
        Queue<Item> xgreater = new Queue<Item>();

        Item piv = getRandomItem(items);

        partition(items, piv, xless, xequal, xgreater);

        sorted = catenate(quickSort(xless), xequal);
        sorted = catenate(sorted, quickSort(xgreater));
        return sorted;
    }

    public static void main(String[] args) {
        Queue helloooo = new Queue();
        helloooo.enqueue("rockabye");
        helloooo.enqueue("baby");
        helloooo.enqueue("don't");
        helloooo.enqueue("you");
        helloooo.enqueue("cry");
        System.out.println(helloooo);
        Queue hello1 = MergeSort.mergeSort(helloooo);
        System.out.println(hello1);


        Queue hola = new Queue();
        hola.enqueue("rockabye");
        hola.enqueue("baby");
        hola.enqueue("don't");
        hola.enqueue("you");
        hola.enqueue("cry");
        Queue hello2 = QuickSort.quickSort(hola);
        System.out.println(hello2);
    }
}
