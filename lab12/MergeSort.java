import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     * <p>
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
        makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        Iterator<Item> iterate = items.iterator();

        Queue qued = new Queue();
        if (items.equals(null)) {
            return null;
        } else if (items.size() == 1) {
            qued.enqueue(iterate.next());
            return qued;
        } else {
            int op = items.size();
            for (int i = 0; i < op; i++) {
                Queue<Item> addi = new Queue<Item>();
                addi.enqueue(iterate.next());
                qued.enqueue(addi);
            }
        }
/*        if (items.equals(null)) {
            return null;
        } else if (items.size() == 1) {
            qued.enqueue(items.dequeue());
            return qued;
        } else {
            int op = items.size();
            for (int i = 0; i < op; i++) {
                Queue<Item> addi = new Queue<Item>();
                addi.enqueue(items.dequeue());
                qued.enqueue(addi);
            }
        }*/
        return qued;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     * <p>
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return A Queue containing all of the q1 and q2 in sorted order, from least to
     * greatest.
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue<Item> ordered = new Queue<Item>();
        int num = q1.size() + q2.size();
        while (num > 0) {
            ordered.enqueue(getMin(q1, q2));
            num--;
        }
        return ordered;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        if (items.isEmpty() | items.size() == 1) {
            return items;
        }
        Queue<Item> sorted;
        Queue<Queue<Item>> sep = makeSingleItemQueues(items);

        Iterator<Queue<Item>> iter = sep.iterator();
        Queue<Item> use1 = iter.next();
        Queue<Item> use2 = iter.next();
        sorted = mergeSortedQueues(use1, use2);
        while (iter.hasNext()) {
            sorted = mergeSortedQueues(sorted, iter.next());
        }

/*        Queue<Item> use1 = sep.dequeue();
        Queue<Item> use2 = sep.dequeue();
        sorted = mergeSortedQueues(use1, use2);
        while (!sep.isEmpty()) {
            sorted = mergeSortedQueues(sorted, sep.dequeue());
        }*/
        return sorted;
    }

    public static void main(String[] args) {
        Queue temp = new Queue();
        temp.enqueue("fight");
        temp.enqueue("me");
        temp.enqueue("Berkeley");
        System.out.println(temp);

/*        Queue temp3 = new Queue();
        temp3.enqueue(1);
        temp3.enqueue(5);
        temp3.enqueue(8);

        Queue temp4 = new Queue();
        temp4.enqueue(2);
        temp4.enqueue(6);
        temp4.enqueue(7);

        Queue temp7 = MergeSort.mergeSortedQueues(temp3, temp4);
        System.out.println(temp7);*/

/*        Queue pop = makeSingleItemQueues(temp);
        System.out.println(pop);
        System.out.println(pop.dequeue());
        System.out.print(pop.dequeue());*/

        Queue temp2 = MergeSort.mergeSort(temp);
        System.out.println(temp2);
    }
}
