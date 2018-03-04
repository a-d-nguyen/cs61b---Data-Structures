/**
 * Created by Anthony Nguyen on 2/10/2017.
 */
public class OffByN implements CharacterComparator {
    private int distance;

    public OffByN(int N) {
        this.distance = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (Math.abs(x - y) == this.distance) {
            return true;
        }
        return false;
    }
}
