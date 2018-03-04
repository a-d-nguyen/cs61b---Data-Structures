import java.util.Map;

/**
 * Created by Anthony Nguyen on 5/6/2017.
 */
public class HuffmanDecoder {

    public static void main(String[] args) {
        ObjectReader read = new ObjectReader(args[0]);
        Map<Character, Integer> table = (Map<Character, Integer>) read.readObject();
        BinaryTrie trie = new BinaryTrie(table);

        System.out.println(trie.root);
        int size = (Integer) read.readObject();

        BitSequence massive = (BitSequence) read.readObject();
        char[] symbols = new char[size];

//        int p = 0;
        for (int i = 0; i < size; i++) {
            Match same = trie.longestPrefixMatch(massive);
            symbols[i] = same.getSymbol();
//            p += match.getSequence().length();
            massive = massive.allButFirstNBits(same.getSequence().length());
        }

        FileUtils.writeCharArray(args[1], symbols);
    }
}
