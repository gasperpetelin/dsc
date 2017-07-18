package DSC.Tests;

import de.lmu.ifi.dbs.elki.math.statistics.tests.StandardizedTwoSampleAndersonDarlingTest;

public class AndersonDarlingTest implements ISimilarityTest
{
    private StandardizedTwoSampleAndersonDarlingTest test = new StandardizedTwoSampleAndersonDarlingTest();
    @Override
    public double getPValue(double[] array1, double[] array2)
    {
        //TODO uses unstandardized getPValue
        return test.unstandardized(array1, array2);
    }
}
