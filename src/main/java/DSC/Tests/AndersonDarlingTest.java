package DSC.Tests;

import DSC.ISimilarityTest;
import de.lmu.ifi.dbs.elki.math.statistics.tests.StandardizedTwoSampleAndersonDarlingTest;

public class AndersonDarlingTest implements ISimilarityTest
{
    private StandardizedTwoSampleAndersonDarlingTest test = new StandardizedTwoSampleAndersonDarlingTest();
    @Override
    public double getPValue(double[] array1, double[] array2)
    {
        return test.unstandardized(array1, array2);
    }
}
