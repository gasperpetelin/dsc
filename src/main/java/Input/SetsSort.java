package Input;


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
        if(this.getMean()<o.getMean())
            return -1;
        if(this.getMean()>o.getMean())
            return 1;
        return 0;
    }

    public Set<Algorithm> getAlgorithms()
    {
        return this.algorithms;
    }
}
