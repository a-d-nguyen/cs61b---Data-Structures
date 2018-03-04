package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLow;
    private double confidenceHigh;
    private double[] estimate;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        estimate = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percol = new Percolation(n);
            double count = 0;
            while (!percol.percolates()) {
                int x = StdRandom.uniform(0, n);
                int y = StdRandom.uniform(0, n);
                if (!percol.isOpen(x, y)) {
                    percol.open(x, y);
                    count++;
                }
            }
            estimate[i] = count / (n * n);
        }
        mean = StdStats.mean(estimate);
        stddev = StdStats.stddev(estimate);
        confidenceLow = mean - (1.96 * stddev) / Math.sqrt(trials);
        confidenceHigh = mean + (1.96 * stddev) / Math.sqrt(trials);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLow() {
        return confidenceLow;
    }

    public double confidenceHigh() {
        return confidenceHigh;
    }

}                       
