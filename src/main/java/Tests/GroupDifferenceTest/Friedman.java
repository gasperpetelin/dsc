package Tests.GroupDifferenceTest;

import Tests.GroupDifferenceTest.Utility.MatrixOperations;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

import java.util.Arrays;

public class Friedman implements IGroupDifferenceTest
{
    public double X(double[][] data, int N, int k)
    {
        MatrixOperations matrix = new MatrixOperations(data);
        double factor1 = ((double)(12*N))/((double)(k*(k+1)));
        double factor2 = ((double)(k*(k+1)*(k+1)))/4;
        double[]R = matrix.columnMean();
        return factor1 * (Arrays.stream(R).map(i -> i*i).sum() - factor2);
    }

    @Override
    public double getPValue(double[][] data)
    {
        int k = data[0].length;
        return 1-new ChiSquaredDistribution(k-1).cumulativeProbability(this.getTSValue(data));
    }

    @Override
    public double getTSValue(double[][] data)
    {
        int N = data.length;
        int k = data[0].length;
        return X(data, N, k);
    }
}
