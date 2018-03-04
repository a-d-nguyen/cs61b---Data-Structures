/**
 * Created by Anthony Nguyen on 2/1/2017.
 */

/* to resize array int[] a = new int[size +1]
   System.arraycopy(a, 0, b, 0, size)
   a[size] = ....
   items = a
   size ++
*/

public class ArrayDeque<Item> {
    private int size;
    private Item[] gen;

    public ArrayDeque() {
        size = 0;
        gen = (Item[]) new Object[8];
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        System.arraycopy(gen, 0, temp, 0, capacity - 1);
        gen = temp;
    }

    public void addFirst(Item x) {
        if (size == gen.length) {
            resize(size + 1);
        }
        Item[] temp = (Item[]) new Object[gen.length];
        temp[0] = x;
        System.arraycopy(gen, 0, temp, 1, size);
        gen = temp;
        size++;
    }

    public void addLast(Item x) {
        if (size == gen.length) {
            resize(size + 1);
        }
        size++;
        gen[size - 1] = x;
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
        for (int i = 0; i != size; i++) {
            System.out.print(gen[i] + " ");
        }
        System.out.println();
    }

    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Item[] forDel = (Item[]) new Object[gen.length];
        System.arraycopy(gen, 0, forDel, 0, size);
        Item[] temp;
        if (size == gen.length) {
            temp = (Item[]) new Object[gen.length - 1];
        } else {
            temp = (Item[]) new Object[gen.length];
        }
        size--;
        System.arraycopy(gen, 1, temp, 0, size);
        gen = temp;
        return forDel[0];
    }

    public Item removeLast() {
        if (isEmpty()) {
            return null;
        }
        Item[] forDel = (Item[]) new Object[gen.length];
        System.arraycopy(gen, 0, forDel, 0, size);
        Item[] temp;
        if (size == gen.length) {
            temp = (Item[]) new Object[size - 1];
        } else {
            temp = (Item[]) new Object[gen.length];
        }
        size--;
        System.arraycopy(gen, 0, temp, 0, size);
        gen = temp;
        return forDel[size];
    }

    public Item get(int index) {
        if (index > size || isEmpty()) {
            return null;
        }
        return gen[index];
    }

}
