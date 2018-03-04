import java.io.Serializable;
import java.util.*;

/**
 * Created by Anthony Nguyen on 5/5/2017.
 */
public class BinaryTrie implements Serializable {
    TrieNode root;
    Map<Character, BitSequence> lookupTbl = new HashMap<>();

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        if (frequencyTable.isEmpty()) {
            throw new IllegalArgumentException();
        }
        root = new TrieNode(frequencyTable, "", lookupTbl);

//        System.out.println("check");
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        BitSequence longest = null;
        Character symbol = null;
        int length = 0;

        for (BitSequence p : lookupTbl.values()) {
            if (p.length() > querySequence.length()) {
                continue;
            }
            if (p.equals(querySequence.firstNBits(p.length()))) {
                if (p.length() > length) {
                    longest = p;
                    length = longest.length();
                }
            }
        }

        Object[] temp = lookupTbl.entrySet().toArray();
        for (Object k : temp) {
            if (longest.equals(((Map.Entry<String, BitSequence>) k).getValue())) {
                symbol = ((Map.Entry<Character, BitSequence>) k).getKey();
            }
        }

        return new Match(longest, symbol);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        return lookupTbl;
    }

    public class TrieNode implements Serializable {
        Map<Character, Integer> container;
        Object[] orderedMap;
        TrieNode left, right;
        String id;
        Map<Character, BitSequence> lookupTbl;

        public TrieNode(Map<Character, Integer> x, String y, Map<Character, BitSequence> lookupTbl) {
            this.container = x;
            this.orderedMap = x.entrySet().toArray();
            this.left = null;
            this.right = null;
            this.id = y;
            this.lookupTbl = lookupTbl;

            if (container.size() <= 1) {
                Character symbol = (Character) container.keySet().toArray()[0];
                lookupTbl.put(symbol, new BitSequence(this.id));
                return;
            } else {
                divide(this.container);
            }
        }

        public void divide(Map<Character, Integer> x) {
            Arrays.sort(orderedMap, new Comparator() {
                public int compare(Object o1, Object o2) {
                    return ((Map.Entry<String, Integer>) o1).getValue()
                            .compareTo(((Map.Entry<String, Integer>) o2).getValue());
                }
            });

            int midIndex;
            if (container.size() % 2 == 0) {
                midIndex = container.size() / 2;
            } else {
                midIndex = (container.size() / 2) + 1;
            }

            HashMap<Character, Integer> leftMap = new HashMap<>();
            HashMap<Character, Integer> rightMap = new HashMap<>();

            int count = 0;
            for (Object q : orderedMap) {
                if (count < midIndex) {
                    leftMap.put(((Map.Entry<Character, Integer>) q).getKey(),
                            ((Map.Entry<String, Integer>) q).getValue());
                    count++;
                } else {
                    rightMap.put(((Map.Entry<Character, Integer>) q).getKey(),
                            ((Map.Entry<String, Integer>) q).getValue());
                }

            }

            left = new TrieNode(leftMap, id + "0", lookupTbl);
            right = new TrieNode(rightMap, id + "1", lookupTbl);
        }
    }

//    public static void main(String[] args) {
//        Map<Character, Integer> frequencyTable = new HashMap<Character, Integer>();
//        frequencyTable.put('a', 1);
//        frequencyTable.put('b', 2);
//        frequencyTable.put('c', 4);
//        frequencyTable.put('d', 5);
//        frequencyTable.put('e', 6);
//        BinaryTrie trie = new BinaryTrie(frequencyTable);

//        BitSequence temp = new BitSequence("00101010101");
//        Match m = trie.longestPrefixMatch(temp);
//        System.out.print(m.getSequence());
//        temp = temp.allButFirstNBits(m.getSequence().length());
//        Match p = trie.longestPrefixMatch(temp);
//        System.out.print(p.getSequence());
//        System.out.print("Success");
//    }
}
