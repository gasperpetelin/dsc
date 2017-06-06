package Input;

import java.util.HashMap;
import java.util.List;

public class Request
{
    private double alpha;
    private String method;
    private List<Algorithm> data;

    public double getAlpha()
    {
        return alpha;
    }

    public void setAlpha(double alpha)
    {
        this.alpha = alpha;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public List<Algorithm> getAlgorithms()
    {
        return data;
    }

    public void setAlgorithms(List<Algorithm> algorithms)
    {
        this.data = algorithms;
    }

    public int getNumberOfAlgorithms()
    {
        return data.size();
    }

    public Algorithm getAlgorithm(int index)
    {
        return this.data.get(index);
    }

    public HashMap<Algorithm, Double> getMeans(int problem)
    {
        HashMap<Algorithm, Double> map = new HashMap<Algorithm, Double>();
        for (Algorithm a:this.data)
        {
            map.put(a, a.getMean(problem));
        }
        return map;
    }



}
