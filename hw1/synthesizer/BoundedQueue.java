package synthesizer;

import java.util.Iterator;

/**
 * Created by Anthony Nguyen on 2/22/2017.
 */
public interface BoundedQueue<T> extends Iterable<T> {
    int capacity();

    int fillCount();

    void enqueue(T x);

    T dequeue();

    T peek();

    default boolean isEmpty() {
        if (fillCount() == 0) {
            return true;
        }
        return false;
    }

    default boolean isFull() {
        if (fillCount() == capacity()) {
            return true;
        }
        return false;
    }

    Iterator<T> iterator();
}
