package Input;


public class AlgoMeanPair implements Comparable<AlgoMeanPair>
{
    public int algorithm;
    public double mean;

    public AlgoMeanPair(int algo, double mean)
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
}
