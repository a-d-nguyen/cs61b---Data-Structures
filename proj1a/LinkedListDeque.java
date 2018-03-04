/**
 * Created by Anthony Nguyen on 1/30/2017.
 */
public class LinkedListDeque<Item> {
    private DLList sentinel;
    private int size;

    private class DLList {
        private Item curr;
        private DLList next;
        private DLList prev;

        DLList(DLList m, Item i, DLList n) {
            prev = m;
            curr = i;
            next = n;
        }

    }

    public LinkedListDeque() {
        size = 0;
        sentinel = new DLList(null, null, null);
//        sentinel = new DLList(sentinel, null, sentinel);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public void addFirst(Item x) {
        DLList temp1 = new DLList(sentinel, x, sentinel.next);
        sentinel.next.prev = temp1;
        sentinel.next = temp1;
        size++;
    }

    public void addLast(Item y) {
        DLList temp2 = new DLList(sentinel.prev, y, sentinel);
        sentinel.prev.next = temp2;
        sentinel.prev = temp2;
//        sentinel.prev.next = new DLList(sentinel.prev, y, sentinel);
//        sentinel.prev = sentinel.prev.next;
        size++;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        DLList list = sentinel;
        while (list.next.curr != null) {
            System.out.print(list.next.curr + " ");
            list = list.next;
        }
    }

    public Item removeFirst() {
        size--;
//        if (list.next.next == null) {
//            sentinel.next = null;
//        }
//        while (list.next != null) {
//            list.next = list.next.next;
//            list = list.next;
//        }
//        return sentinel.next.curr;
        Item del = sentinel.next.curr;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return del;
    }

    public Item removeLast() {
        size--;
//        DLList list = sentinel.next;
//        if (list.next == null) {
//            list.curr = null;
//            return null;
//        } else {
//            while (list.next.next != null) {
//                list = list.next;
//            }
//            list.next = null;
//            return list.curr;
//        }
        Item del = sentinel.prev.curr;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return del;
    }

    public Item get(int index) {
//        DLList list = sentinel.next;
//        while (index > 0) {
//            if (list.curr == null){
//                return null;
//            }
//            list = list.next;
//            index -= 1;
//        }
//        return list.curr;
        int current = 0;
        DLList list = sentinel.next;
        if (index >= size) {
            return null;
        }
        while (current != index) {
            list = list.next;
            current++;
        }
        return list.curr;
    }

    public Item getRecursive(int index) {
        if (index == 0) {
            return sentinel.next.curr;
        } else if (index >= size) {
            return null;
        }
        DLList list = sentinel.next;
        return recur(index, list);
    }

    private Item recur(int index, DLList list) {
        if (index == 0) {
            return list.curr;
        } else {
            list = list.next;
            return recur(index - 1, list);
        }
    }

}
