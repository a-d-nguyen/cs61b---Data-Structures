/**
 * Created by Anthony Nguyen on 2/10/2017.
 */
public interface Deque<Item> {
    void addFirst(Item x);
    void addLast(Item y);
    Item removeFirst();
    Item removeLast();
    Item get(int index);
    boolean isEmpty();
    int size();
    void printDeque();
}
