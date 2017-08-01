package DSC2.Tests;


import DSC2.INonParametricTest;
import org.apache.commons.math3.stat.inference.WilcoxonSignedRankTest;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class WilcoxonSignedRank implements INonParametricTest
{

    @Override
    public double getPValue(double[][] data)
    {
        double[] d1 = new double[data.length];
        double[] d2 = new double[data.length];
        for (int i = 0; i < data.length; i++)
        {
            d1[i] = data[i][0];
            d2[i] = data[i][1];
        }
        return new WilcoxonSignedRankTest().wilcoxonSignedRank(d1,d2);
    }

    @Override
    public double getTSValue(double[][] data)
    {
        throw new NotImplementedException();
    }
}
