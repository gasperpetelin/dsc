package Output;


import java.util.ArrayList;
import java.util.List;

public class ProblemSolutions
{
    private String problemName;
    private List<AlgorithmRank> result;

    public ProblemSolutions(String problemName)
    {
        this.problemName = problemName;
        this.result = new ArrayList<>();
    }

    public List<AlgorithmRank> getResult()
    {
        return result;
    }

    public void addAlgorithm(AlgorithmRank pair)
    {
        this.result.add(pair);
    }
}
