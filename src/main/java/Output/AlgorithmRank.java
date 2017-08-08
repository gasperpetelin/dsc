package Output;


public class AlgorithmRank implements Comparable<AlgorithmRank>
{
    private String algorithmName;
    private double rank;

    public AlgorithmRank(String name, double rank)
    {
        this.algorithmName = name;
        this.rank = rank;
    }

    public String getAlgorithmName()
    {
        return algorithmName;
    }

    public double getRank()
    {
        return rank;
    }

    @Override
    public int compareTo(AlgorithmRank o)
    {
        return this.algorithmName.compareTo(o.algorithmName);
    }
}
