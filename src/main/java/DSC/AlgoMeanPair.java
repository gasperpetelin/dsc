package DSC;


import Input.Algorithm;

public class AlgoMeanPair implements Comparable<AlgoMeanPair>
{
    private Algorithm algorithm;
    private double mean;

    public AlgoMeanPair(Algorithm algo, double mean)
    {
        this.algorithm = algo;
        this.mean = mean;
    }

    public int compareTo(AlgoMeanPair o)
    {
        return Double.compare(this.mean, o.mean);
    }

    public Algorithm getAlgorithm()
    {
        return this.algorithm;
    }

    public double getMean()
    {
        return this.mean;
    }
}
