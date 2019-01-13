/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] values;
    private double mean, std;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("index n or trials can not be negative");
        }
        mean = 0;
        std = 0;
        Percolation jamma;
        values = new double[trials];
        for (int i = 0; i < trials; i++) {
            jamma = new Percolation(n);
            while (!jamma.percolates()) {
                int rrow = StdRandom.uniform(1, n + 1);
                int rcol = StdRandom.uniform(1, n + 1);
                jamma.open(rrow, rcol);
            }
            double d = (double) jamma.numberOfOpenSites();
            double m = (double) n;
            values[i] = d / (m * m);
        }
    }    // perform trials independent experiments on an n-by-n grid

    public double mean() {
        mean = StdStats.mean(values);
        return mean;
    }                        // sample mean of percolation threshold

    public double stddev() {
        std = StdStats.stddev(values);
        return std;
    }                        // sample standard deviation of percolation threshold

    public double confidenceLo() {
        double conLo = mean - 1.96 * std / Math.sqrt(values.length);
        return conLo;
    }               // low  endpoint of 95% confidence interval


    public double confidenceHi() {
        double conHi = mean + 1.96 * std / Math.sqrt(values.length);
        return conHi;
    }                  // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();

        PercolationStats experiment;
        experiment = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + experiment.mean());
        StdOut.println("Stddev                  = " + experiment.stddev());
        StdOut.println(
                "95% confidence interval = [" + experiment.confidenceLo() + " , " + experiment
                        .confidenceHi() + " ]");
    }
}
