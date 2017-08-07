package Tests.GroupDifferenceTest;

import Tests.GroupDifferenceTest.Implementations.FriedmanOriginal;
import jsc.datastructures.MatchedData;

public class Friedman implements IGroupDifferenceTest
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
