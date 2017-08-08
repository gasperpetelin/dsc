package Tests.GroupDifferenceTest;

import jsc.util.Rank;
import org.apache.commons.math3.distribution.FDistribution;

public class ImanDavenport implements IGroupDifferenceTest
{
    @Override
    public double getPValue(double[][] data)
    {
        int N = data.length;
        int k = data[0].length;
        FDistribution fDistribution = new FDistribution(k-1, (k-1)*(N-1));
        return  1-fDistribution.cumulativeProbability(this.getTSValue(data));

    }

    @Override
    public double getTSValue(double[][] data)
    {
        int N = data.length;
        int k = data[0].length;
        Friedman friedman = new Friedman();
        double X = friedman.X(data, N, k);
        return ((N-1)*X)/(N*(k-1)-X);
    }
}
