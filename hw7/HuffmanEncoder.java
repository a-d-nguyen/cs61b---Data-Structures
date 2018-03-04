import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anthony Nguyen on 5/6/2017.
 */
public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> freqTable = new HashMap<>();

        for (char x : inputSymbols) {
            if (freqTable.containsKey(x)) {
                freqTable.put(x, freqTable.get(x) + 1);
            } else {
                freqTable.put(x, 1);
            }
        }

        return freqTable;
    }


    public static void main(String[] args) {
        char[] read = FileUtils.readFile(args[0]);
        Map<Character, Integer> newFreqTable = HuffmanEncoder.buildFrequencyTable(read);

        BinaryTrie tree = new BinaryTrie(newFreqTable);

        ObjectWriter huffFile = new ObjectWriter(args[0] + ".huf");
        huffFile.writeObject((Serializable) newFreqTable);
        huffFile.writeObject(new Integer(read.length));

        Map<Character, BitSequence> table = tree.buildLookupTable();
        List<BitSequence> bitList = new ArrayList<>();
        for (char x : read) {
            bitList.add(table.get(x));
        }
        BitSequence bs = BitSequence.assemble(bitList);
        huffFile.writeObject(bs);
    }
}
