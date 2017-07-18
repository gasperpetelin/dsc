package DSC2;


import Output.Response;

import java.util.ArrayList;
import java.util.List;

public class DataHolder
{
    private Response input;
    public DataHolder(Response input)
    {
        this.input = input;
    }

    public int getNumberOfAlgorithms()
    {
        return this.input.getNumberOfAlgorithms();
    }

    public Double[] getAlgorithmsData(int algorithm)
    {
        List<Double> ls = new ArrayList<>();
        for (int i = 0; i < this.input.getProblems().size(); i++)
        {
            ls.add(this.input.getProblems().get(i).getResult().get(algorithm).getRank());
        }
        Double[] dl = new Double[ls.size()];
        int index = 0;
        for(Double d:ls)
        {
            dl[index] = d;
            index++;
        }
        return dl;
    }
}
