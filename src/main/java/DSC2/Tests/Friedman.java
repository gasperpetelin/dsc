package DSC2.Tests;

import DSC2.INonParametricTest;
import jsc.datastructures.MatchedData;
import jsc.relatedsamples.FriedmanTest;

public class Friedman implements INonParametricTest
{
    @Override
    public double getPValue(double[][] data)
    {
        return new FriedmanTest(new MatchedData(data)).getTestStatistic();
    }
}
