package Input;


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
        int r = 0;
        if(this.mean<o.mean)
            r = 1;
        if(this.mean>o.mean)
            r = -1;
        return -1*r;
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
