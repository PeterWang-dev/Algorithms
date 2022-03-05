/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] threshold;
    private int T;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Parameters are not valid");

        T = trials;
        threshold = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation grid = new Percolation(n);
            while (!grid.percolates()) {
                int randomx = StdRandom.uniform(n) % n + 1;
                int randomy = StdRandom.uniform(n) % n + 1;
                grid.open(randomx, randomy);
            }
            threshold[i] = grid.numberOfOpenSites() / (n * n * 1.0);
        }
    }

    public double mean() {
        return StdStats.mean(threshold, 0, T - 1);
    }

    public double stddev() {
        return StdStats.stddev(threshold, 0, T - 1);
    }

    public double confidenceLo() {
        return (mean() - (1.96 * stddev() / Math.sqrt(T)));
    }

    public double confidenceHi() {
        return (mean() + (1.96 * stddev() / Math.sqrt(T)));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        PercolationStats experiment = new PercolationStats(n, trails);

        StdOut.println("mean = " + experiment.mean());
        StdOut.println("stddev = " + experiment.stddev());
        StdOut.println(
                "95% confidence interval = " + "[" + experiment.confidenceLo() + ", " + experiment
                        .confidenceHi() + "]");
    }
}
