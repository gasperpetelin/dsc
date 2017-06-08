package DSC.Tests;

import DSC.ISimilarityTest;
import jsc.independentsamples.SmirnovTest;

public class KolmogorovSmirnovTest implements ISimilarityTest
{
    public double getPValue(double[] array1, double[] array2)
    {
        return new SmirnovTest(array1, array2).getSP();
    }
}
