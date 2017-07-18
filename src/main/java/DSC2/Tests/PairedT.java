package DSC2.Tests;

import DSC2.INonParametricTest;
import org.apache.commons.math3.stat.inference.TTest;
public class PairedT implements INonParametricTest
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
        return new TTest().pairedTTest(d1,d2);
    }
}
