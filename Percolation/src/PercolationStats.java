import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import  edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private double[] arr;
    private double mean;
    private double stdv;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n <= 0 || trials <= 0){
            throw new IllegalArgumentException("No negative nums");
        }
        this.trials = trials;
        arr = new double[trials];
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while(!perc.percolates()){
                perc.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1,n+1));
            }
            arr[i] = (Double.valueOf(perc.numberOfOpenSites())/(n*n));
        }
//        System.out.println("mean = " + mean());
//        System.out.println("stddv = " + stddev());
//        System.out.println("95% CI = " + "[" + confidenceLo() + "," + " " + confidenceHi()+ "]");
//        System.out.println("Time: " + sw.elapsedTime());
        mean = StdStats.mean(arr);
        stdv = StdStats.stddev(arr);
    }

    // sample mean of percolation threshold
    public double mean(){
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return stdv;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean - (1.96*stdv) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean + (1.96*stdv) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args){
        PercolationStats pc = new PercolationStats(300, 10);
    }

}