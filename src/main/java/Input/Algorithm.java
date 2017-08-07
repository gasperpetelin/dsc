package Input;


import java.util.List;

public class Algorithm
{
    private String name;
    private List<Problem> problems;

    public Algorithm(String name, List<Problem> problems)
    {
        this.name = name;
        this.problems = problems;
    }

    public String getName()
    {
        return name;
    }

    public int getNumberOfProblems()
    {
        return problems.size();
    }

    public Problem getProblem(int index)
    {
        return problems.get(index);
    }

    public double getMean(int problem)
    {
        return this.problems.get(problem).dataMean();
    }
}
