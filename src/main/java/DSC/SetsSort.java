package DSC;


import Input.Algorithm;

import java.util.Set;

public class SetsSort implements Comparable<SetsSort>
{
    private Set<Algorithm> algorithms;
    private double mean;

    public double rank = 0;

    public SetsSort(Set<Algorithm> algorithms, double mean)
    {
        this.mean = mean;
        this.algorithms = algorithms;
    }

    public double getMean()
    {
        return this.mean;
    }

    public int compareTo(SetsSort o)
    {
        return Double.compare(o.getMean(), this.mean);
    }

    public Set<Algorithm> getAlgorithms()
    {
        return this.algorithms;
    }
}
