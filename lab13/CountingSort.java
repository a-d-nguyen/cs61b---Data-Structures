import com.sun.deploy.util.ArrayUtil;

import java.util.ArrayList;

/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 * 
 * @author 	Akhil Batra
 * @version	1.1 - April 16, 2016
 * 
**/
public class CountingSort {
    
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     *  does not touch original array (non-destructive method)
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
    **/
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            if (i > max) {
                max = i;
            }
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i] += 1;
        }

        // put the value count times into a new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // return the sorted array
        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     *  does not touch original array (non-destructive method)
     * 
     * @param toSort int array that will be sorted
    **/
    public static int[] betterCountingSort(int[] toSort) {
        //TODO make it work with arrays containing negative numbers.
        ArrayList<Integer> positive = new ArrayList<>();
        ArrayList<Integer> negative = new ArrayList<>();

        for (int i = 0; i < toSort.length; i++) {
            if (toSort[i] >= 0) {
                positive.add(toSort[i]);
            } else {
                negative.add(toSort[i] * -1);
            }
        }
        if (negative.isEmpty()) {
            int[] result = naiveCountingSort(toSort);
            return result;
        }
        int[] pos = new int[positive.size()];
        int[] neg = new int[negative.size()];
        int index = 0;

        for (int s: positive) {
            pos[index] = s;
            index ++;
        }

        index = 0;
        for (int s: negative) {
            neg[index] = s;
            index ++;
        }

        pos = naiveCountingSort(pos);
        neg = naiveCountingSort(neg);

        for(int i = 0; i < neg.length / 2; i++) {
            int temp = neg[i];
            neg[i] = neg[neg.length - i - 1] * -1;
            neg[neg.length - i - 1] = temp * -1;
        }

        if (neg[neg.length / 2] >= 0) {
            neg[neg.length / 2] = neg[neg.length / 2] * -1;
        }
        int[] result = new int[pos.length + neg.length];
        System.arraycopy(neg, 0, result, 0, neg.length);
        System.arraycopy(pos, 0, result, neg.length, pos.length);

        return result;
    }
}
