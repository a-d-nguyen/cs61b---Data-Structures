package hw4.hash;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
        /* TODO: Write a test that ensures the hashCode is perfect,
          meaning no two SimpleOomages should EVER have the same
          hashCode!
         */
        SimpleOomage temp = new SimpleOomage(5, 10, 20);
        SimpleOomage[] ooA = new SimpleOomage[6];
        int hash = temp.hashCode();
        ooA[0] = new SimpleOomage(5, 10, 20);
        ooA[1] = new SimpleOomage(5, 20, 10);
        ooA[2] = new SimpleOomage(10, 5, 20);
        ooA[3] = new SimpleOomage(10, 20, 5);
        ooA[4] = new SimpleOomage(20, 5, 10);
        ooA[5] = new SimpleOomage(20, 10, 5);
        HashSet hashS = new HashSet<>();
        hashS.add(temp);
        int count = 0;
        for (int i = 0; i < 6; i++) {
            if (hash == (ooA[i].hashCode())) {
                count++;
            }
        }
        assertEquals(1, count);
    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }

    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }

    /* TODO: Once you've finished haveNiceHashCodeSpread,
    in OomageTestUtility, uncomment this test. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /**
     * Calls tests for SimpleOomage.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }
}
