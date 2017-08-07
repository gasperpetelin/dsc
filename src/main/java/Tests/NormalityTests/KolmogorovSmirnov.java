package Tests.NormalityTests;


import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;

public class KolmogorovSmirnov implements INormalityTest
{
    NormalDistribution nd = new NormalDistribution(0, 1);
    @Override
    public double getPValue(double[] data)
    {
        return new KolmogorovSmirnovTest().kolmogorovSmirnovTest(nd, data);
    }
}
