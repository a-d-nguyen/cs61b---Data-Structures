import edu.princeton.cs.algs4.Picture;

/**
 * Created by Anthony Nguyen on 4/26/2017.
 */
public class SeamCarver {
    private Picture disPic;
    private double[][] allEnergy;

    public SeamCarver(Picture picture) {
        if (picture.equals(null)) {
            throw new NullPointerException();
        }
        this.disPic = new Picture(picture);
        this.allEnergy = new double[picture.height()][picture.width()];
    }

    private void calcEnergy() {
        for (int x = 0; x < allEnergy.length; x++) {
            for (int y = 0; y < allEnergy[x].length; y++) {
                this.allEnergy[x][y] = this.energy(y, x);
            }
        }
    }

    public Picture picture() {
//        Picture temp = new Picture(this.disPic);
        return new Picture(disPic);
    }

    public int width() {
        return this.disPic.width();
    }

    public int height() {
        return this.disPic.height();
    }

    public double energy(int x, int y) {
        if (x < 0 || x >= this.width() || y < 0 || y >= this.height()) {
            throw new IndexOutOfBoundsException("what up");
        }

        double r1 = Math.abs(this.disPic.get(((x - 1) + width()) % width(), y).getRed()
                - this.disPic.get((x + 1) % width(), y).getRed());
        double g1 = Math.abs(this.disPic.get(((x - 1) + width()) % width(), y).getGreen()
                - this.disPic.get((x + 1) % width(), y).getGreen());
        double b1 = Math.abs(this.disPic.get(((x - 1) + width()) % width(), y).getBlue()
                - this.disPic.get((x + 1) % width(), y).getBlue());
        double r2 = Math.abs(this.disPic.get(x, ((y - 1) + height()) % height()).getRed()
                - this.disPic.get(x, (y + 1) % height()).getRed());
        double g2 = Math.abs(this.disPic.get(x, ((y - 1) + height()) % height()).getGreen()
                - this.disPic.get(x, (y + 1) % height()).getGreen());
        double b2 = Math.abs(this.disPic.get(x, ((y - 1) + height()) % height()).getBlue()
                - this.disPic.get(x, (y + 1) % height()).getBlue());

        return r1 * r1 + g1 * g1 + b1 * b1 + r2 * r2 + g2 * g2 + b2 * b2;
    }

    public int[] findHorizontalSeam() {
        this.calcEnergy();
        int[] toRemove = new int[this.width()];
        int[] path = new int[this.width() * this.height()];

        for (int y = 1; y < this.width(); y++) {
            for (int x = 0; x < this.height(); x++) {
                findPath(y, x, path, false);
            }
        }
        int pos = getMinH();

        toRemove[toRemove.length - 1] = pos;
        int vertex = this.width() * (pos) + (this.width() - 1);

        for (int i = toRemove.length - 2; i >= 0; i--) {
            vertex = path[vertex];
            toRemove[i] = vertex / this.width();
        }

        return toRemove;
    }

    public int[] findVerticalSeam() {
        this.calcEnergy();
        int[] toRemove = new int[this.height()];
        int[] path = new int[this.width() * this.height()];

        for (int x = 1; x < this.height(); x++) {
            for (int y = 0; y < this.width(); y++) {
                findPath(y, x, path, true);
            }

        }
        int pos = getMinV();

        toRemove[toRemove.length - 1] = pos;
        int vertex = this.width() * (this.height() - 1) + (pos);

        for (int i = toRemove.length - 2; i >= 0; i--) {
            vertex = path[vertex];
            toRemove[i] = vertex % this.width();
        }

        return toRemove;
    }

    private void findPath(int col, int row, int[] path, boolean vert) {
        int pos = 0;
        int[] above;
        double minVal = 1000000000;

        if (vert) {
            above = getAboveV(row, col);
        } else {
            above = getAboveH(row, col);
        }

        for (int x : above) {
            if (this.allEnergy[x / this.width()][x % this.width()] < minVal) {
                pos = x;
                minVal = this.allEnergy[x / this.width()][x % this.width()];
            }
        }

        path[this.width() * row + col] = pos;
        this.allEnergy[row][col] += minVal;

    }

    private int[] getAboveV(int row, int col) {
        int[] above;
        if (col == 0) {
            above = new int[2];
            above[0] = this.width() * (row - 1) + col;
            above[1] = this.width() * (row - 1) + (col + 1);
        } else if (col == this.width() - 1) {
            above = new int[2];
            above[0] = this.width() * (row - 1) + (col - 1);
            above[1] = this.width() * (row - 1) + (col);
        } else {
            above = new int[3];
            above[0] = this.width() * (row - 1) + (col - 1);
            above[1] = this.width() * (row - 1) + (col);
            above[2] = this.width() * (row - 1) + (col + 1);
        }
        return above;
    }

    private int[] getAboveH(int row, int col) {
        int[] above;
        if (row == 0) {
            above = new int[2];
            above[0] = this.width() * (row) + (col - 1);
            above[1] = this.width() * (row + 1) + (col - 1);
        } else if (row == this.height() - 1) {
            above = new int[2];
            above[0] = this.width() * (row - 1) + (col - 1);
            above[1] = this.width() * (row) + (col - 1);
        } else {
            above = new int[3];
            above[0] = this.width() * (row - 1) + (col - 1);
            above[1] = this.width() * (row) + (col - 1);
            above[2] = this.width() * (row + 1) + (col - 1);
        }
        return above;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != this.width() || this.width() <= 1 || this.height() <= 1) {
            throw new IllegalArgumentException("hurrrooo");
        }

        double[][] newEnergy = new double[this.height() - 1][this.width()];
        Picture result = new Picture(this.width(), this.height() - 1);

        for (int x = 0; x < this.height(); x++) {
            for (int y = 0; y < this.width(); y++) {
                if (seam[y] == x) {
                    continue;
                } else if (x > seam[y]) {
                    newEnergy[x - 1][y] = this.allEnergy[x][y];
                    result.set(y, x - 1, disPic.get(y, x));
                } else {
                    newEnergy[x][y] = this.allEnergy[x][y];
                    result.set(y, x, disPic.get(y, x));
                }
            }
        }
        allEnergy = newEnergy;
        this.disPic = new Picture(result);

    }

    public void removeVerticalSeam(int[] seam) {
        if (seam.length != this.height() || this.width() <= 1 || this.height() <= 1) {
            throw new IllegalArgumentException();
        }

        double[][] newEnergy = new double[this.height()][this.width() - 1];
        Picture result = new Picture(this.width() - 1, this.height());

        for (int i = 0; i < seam.length; i++) {
            System.arraycopy(allEnergy[i], 0, newEnergy[i], 0, seam[i]);
            for (int j = 0; j < seam[i]; j++) {
                result.set(j, i, disPic.get(j, i));
            }
            System.arraycopy(allEnergy[i], seam[i] + 1, newEnergy[i], seam[i],
                    allEnergy[i].length - seam[i] - 1);
            for (int j = seam[i] + 1; j < allEnergy[i].length; j++) {
                result.set(j - 1, i, disPic.get(j, i));
            }
        }
        allEnergy = newEnergy;
        this.disPic = new Picture(result);
    }

    private int getMinV() {
        double minVal = 1000000000;
        int pos = 0;
        for (int x = 0; x < this.width(); x++) {
            if (this.allEnergy[this.height() - 1][x] < minVal) {
                pos = x;
                minVal = this.allEnergy[this.height() - 1][x];
            }
        }

        return pos;
    }

    private int getMinH() {
        double minVal = 1000000000;
        int pos = 0;
        for (int y = 0; y < this.height(); y++) {
            if (this.allEnergy[y][this.width() - 1] < minVal) {
                pos = y;
                minVal = this.allEnergy[y][this.width() - 1];
            }
        }
        return pos;
    }
}
