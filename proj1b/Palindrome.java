/**
 * Created by Anthony Nguyen on 2/10/2017.
 */
public class Palindrome {
    public static Deque<Character> wordToDeque(String word) {
        Deque<Character> testWord = new ArrayDequeSolution<Character>();
        for (int i = 0; i < word.length(); i++) {
            char x = word.charAt(i);
            testWord.addLast(x);
        }
        return testWord;
    }

    public static boolean isPalindrome(String word) {
        Deque<Character> testWord = wordToDeque(word);
        return isPalindrome(testWord);
    }

    private static boolean isPalindrome(Deque<Character> word) {
        if (word.size() == 1 || word.size() == 0) {
            return true;
        }

        char first = word.removeFirst();
        char last = word.removeLast();

        boolean palin = first == last;

        if (palin) {
            return isPalindrome(word) && palin;
        } else {
            return false;
        }
    }

    public static boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> testWord = wordToDeque(word);
        return isPalindrome(testWord, cc);
    }

    private static boolean isPalindrome(Deque<Character> word, CharacterComparator cc) {
        if (word.size() == 0 || word.size() == 1) {
            return true;
        }

        char first = word.removeFirst();
        char last = word.removeLast();

        boolean palin = cc.equalChars(first, last);

        if (palin) {
            return isPalindrome(word, cc) && palin;
        } else {
            return false;
        }
    }
}
