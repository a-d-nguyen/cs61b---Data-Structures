package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF backwash;
    private Ref[][] open;
    private int numOpen;

    private class Ref {
        boolean isOpen;
        int index;

        Ref() {
            isOpen = false;
            index = 0;
        }
    }

    private int translate(int row, int col) {
        isValid(row, col);
        return (row * n) + col;
    }

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid input : n must > 0 !");
        }
        this.n = n;
        open = new Ref[n][n];
        grid = new WeightedQuickUnionUF(this.n * this.n + 2);
        backwash = new WeightedQuickUnionUF(this.n * this.n + 2);

        for (int i = 0; i < n; i++) {
            for (int p = 0; p < n; p++) {
                open[i][p] = new Ref();
                open[i][p].index = translate(i, p);
            }
        }

        numOpen = 0;
    }


    public void open(int row, int col) {
        isValid(row, col);
        if (!open[row][col].isOpen) {
            open[row][col].isOpen = true;
            if (row == 0 && n == 1) {
                grid.union(translate(row, col), n * n);
                backwash.union(translate(row, col), n * n);
                grid.union(translate(row, col), n * n + 1);
            }
            if (row == 0) {
                grid.union(translate(row, col), n * n);
                backwash.union(translate(row, col), n * n);
            } else if (row == n - 1) {
                grid.union(translate(row, col), n * n + 1);
            }
            if ((row - 1 >= 0) && isOpen(row - 1, col)) {
                grid.union(translate(row, col), translate(row - 1, col));
                backwash.union(translate(row, col), translate(row - 1, col));
            }
            if ((row + 1 < n) && isOpen(row + 1, col)) {
                grid.union(translate(row, col), translate(row + 1, col));
                backwash.union(translate(row, col), translate(row + 1, col));
            }
            if ((col - 1 >= 0) && isOpen(row, col - 1)) {
                grid.union(translate(row, col), translate(row, col - 1));
                backwash.union(translate(row, col), translate(row, col - 1));
            }
            if ((col + 1 < n) && isOpen(row, col + 1)) {
                grid.union(translate(row, col), translate(row, col + 1));
                backwash.union(translate(row, col), translate(row, col + 1));
            }
            numOpen++;
        }
    }

    public boolean isOpen(int row, int col) {
        isValid(row, col);
        return open[row][col].isOpen;
    }

    public boolean isFull(int row, int col) {
        isValid(row, col);
        return backwash.connected(translate(row, col), n * n);
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean percolates() {
        return grid.connected(((n * n) + 1), (n * n));
    }

    public void isValid(int row, int col) {
        if (row < 0 || col < 0 || row >= n || col >= n) {
            throw new IndexOutOfBoundsException("Invalid input: out of bounds");
        }
    }


}                       
