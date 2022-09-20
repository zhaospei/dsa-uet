import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private int numberTestCases;
    private double[] frac;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) 
            throw new IllegalArgumentException();
        numberTestCases = trials;
        frac = new double[numberTestCases];
        for (int i = 0; i < numberTestCases; i++) {
            Percolation curPer = new Percolation(n);
            int openSites = 0;
            while (!curPer.percolates()) {
                int x = StdRandom.uniformInt(1, n + 1);
                int y = StdRandom.uniformInt(1, n + 1);
                if (!curPer.isOpen(x, y)) {
                    curPer.open(x, y);
                    openSites++;
                }
            }
            frac[i] = (double) openSites / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(frac);
    }

    public double stddev() {
        return StdStats.stddev(frac);
    }

    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(numberTestCases); 
    }

    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(numberTestCases); 
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats mainPercolation = new PercolationStats(n, t);
        StdOut.println("mean                    = " + mainPercolation.mean());
        StdOut.println("stddev                  = " + mainPercolation.stddev());
        StdOut.println("95% confidence interval = [" + mainPercolation.confidenceLo() + ", " + mainPercolation.confidenceHi() + "]");
   }
}
