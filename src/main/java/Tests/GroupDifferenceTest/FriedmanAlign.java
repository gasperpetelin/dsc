package Tests.GroupDifferenceTest;

import Tests.GroupDifferenceTest.Utility.MatrixOperations;
import jsc.util.Rank;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

import java.util.Arrays;

public class FriedmanAlign implements IGroupDifferenceTest
{

    private double[][] rankCalculation(double[][] data)
    {
        MatrixOperations matrixOp = new MatrixOperations(data);

        int N = data.length;
        int k = data[0].length;
        double[] flat = new double[N*k];


        double[] means = matrixOp.rowMean();
        double[][] dataMean = new double[N][k];

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < k; j++)
            {
                dataMean[i][j] = data[i][j]-means[i];
            }
        }


        int counter = 0;
        for (int i = 0; i < k; i++)
        {
            for (int j = 0; j < N; j++)
            {
                flat[counter] = -dataMean[j][i] + 0.0;
                counter++;
            }
        }

        Rank rank = new Rank(flat, 0);

        double[][] newRankMatrix = new double[N][k];

        int columnCounter = 0;
        int rowCounter = 0;
        for(Double d:rank.getRanks())
        {
            newRankMatrix[columnCounter][rowCounter] = d;
            columnCounter++;
            if(columnCounter==N)
            {
                rowCounter++;
                columnCounter=0;
            }
        }
        return newRankMatrix;

    }


    @Override
    public double getPValue(double[][] data)
    {
        double k = (double)data[0].length;
        ChiSquaredDistribution CSdistribution = new ChiSquaredDistribution(k-1);
        return 1-CSdistribution.cumulativeProbability(this.getTSValue(data));

    }

    @Override
    public double getTSValue(double[][] data)
    {
        data = this.rankCalculation(data);
        MatrixOperations matrix = new MatrixOperations(data);
        double N = (double)data.length;
        double k = (double)data[0].length;

        double rjSum = Arrays.stream(matrix.columnSum()).map(i -> i*i).sum();
        double riSum = Arrays.stream(matrix.rowSum()).map(i -> i*i).sum();


        double kn = k*N;
        double num = (k - 1) * (rjSum - (kn * N/4)*Math.pow(kn + 1, 2));
        double denom = ((kn * (kn + 1)) * ((2 * kn) + 1))/6 - (1/k) * riSum;
        return num/denom;
    }
}
