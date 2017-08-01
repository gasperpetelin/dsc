package DSC2.Tests;

import DSC2.INonParametricTest;
import jsc.datastructures.MatchedData;
import jsc.distributions.FriedmanM;
import jsc.relatedsamples.FriedmanTest;

public class Friedman implements INonParametricTest
{
    @Override
    public double getPValue(double[][] data)
    {
        MatchedData d = new MatchedData(data);
        return new FriedmanOriginal(d, 0, false).getSP();
    }

    @Override
    public double getTSValue(double[][] data)
    {
        MatchedData d = new MatchedData(data);
        return new FriedmanOriginal(d, 0, false).getTestStatistic();
    }
}
