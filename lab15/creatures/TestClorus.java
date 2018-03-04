package creatures;

import huglife.*;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Created by Anthony Nguyen on 5/5/2017.
 */
public class TestClorus {

    @Test
    public void testReplicate() {
        Clorus babyMaker = new Clorus(2);
        Clorus baby = babyMaker.replicate();
        assertNotSame(babyMaker, baby);
        assertEquals(babyMaker.energy(), baby.energy(), 0.01);
    }

    @Test
    public void testChoose() {
        Clorus test1 = new Clorus(1.5);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Plip());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = test1.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        surrounded.put(Direction.TOP, new Plip());
        surrounded.put(Direction.BOTTOM, new Empty());
        surrounded.put(Direction.LEFT, new Empty());
        surrounded.put(Direction.RIGHT, new Empty());

        Action actual1 = test1.chooseAction(surrounded);
        Action expected1 = new Action(Action.ActionType.ATTACK);

        assertEquals(expected1, actual1);

        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Empty());
        surrounded.put(Direction.LEFT, new Empty());
        surrounded.put(Direction.RIGHT, new Empty());

        Action actual3 = test1.chooseAction(surrounded);
        Action expected3 = new Action(Action.ActionType.REPLICATE);

        assertEquals(actual3, expected3);

        Clorus test2 = new Clorus(0.8);
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Empty());
        surrounded.put(Direction.LEFT, new Empty());
        surrounded.put(Direction.RIGHT, new Empty());

        Action actual2 = test2.chooseAction(surrounded);
        Action expected2 = new Action(Action.ActionType.MOVE);

        assertEquals(expected2, actual2);
    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestPlip.class));
    }
}
