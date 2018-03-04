import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by Anthony Nguyen on 2/10/2017.
 */

public class TestArrayDeque1B {
    @Test
    public void testArray() {
        StudentArrayDeque test1 = new StudentArrayDeque<Integer>();
        ArrayDequeSolution test2 = new ArrayDequeSolution<Integer>();
        OperationSequence msg = new OperationSequence();
        for (int i = 0; i < 10; i += 1) {
            Integer randomNum = StdRandom.uniform(10);

            if (randomNum % 2 == 0) {
                test1.addLast(i);
                test2.addLast(i);
                DequeOperation dequeOp1 = new DequeOperation("addLast", i);
                msg.addOperation(dequeOp1);
                int temp = test1.size() - 1;
                assertEquals(msg.toString(), test2.get(test1.size() - 1), test1.get(temp));
            } else {
                test1.addFirst(i);
                test2.addFirst(i);
                DequeOperation dequeOp1 = new DequeOperation("addFirst", i);
                msg.addOperation(dequeOp1);
                assertEquals(msg.toString(), test2.get(0), test1.get(0));
            }
        }

        for (int i = 0; i < 9; i++) {
            Integer randomNum2 = StdRandom.uniform(10);

            if (randomNum2 % 2 == 0) {
                DequeOperation dequeOp2 = new DequeOperation("removeFirst");
                msg.addOperation(dequeOp2);
                assertEquals(msg.toString(), test2.removeFirst(), test1.removeFirst());
            } else {
                DequeOperation dequeOp2 = new DequeOperation("removeLast");
                msg.addOperation(dequeOp2);
                assertEquals(msg.toString(), test2.removeLast(), test1.removeLast());
            }
        }
    }
}
