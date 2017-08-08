package Tests.GroupDifferenceTest.Utility;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MatrixOperations
{
    private double data[][];
    private int N;
    private int k;

    public MatrixOperations(double[][] data)
    {
        this.data = data;
        this.N = data.length;
        this.k = data[0].length;
    }

    public double[] rowSum()
    {
        double[] d = new double[this.N];
        for (int i = 0; i < k; i++)
        {
            for (int j = 0; j < N; j++)
            {
                d[j] +=this.data[j][i];
            }
        }
        return d;
    }

    public double[] rowMean()
    {
        double[] d = new double[this.N];
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < k; j++)
            {
                d[i] +=this.data[i][j];
            }
            d[i]/=k;
        }
        return d;
    }

    public double[] columnSum()
    {
        double[] d = new double[this.k];
        for (int i = 0; i < k; i++)
        {
            for (int j = 0; j < N; j++)
            {
                d[i] +=this.data[j][i];
            }
        }
        return d;
    }

    public double[] columnMean()
    {
        double[] d = new double[this.k];
        for (int i = 0; i < k; i++)
        {
            for (int j = 0; j < N; j++) {
                d[i] +=this.data[j][i];
            }
            d[i]/=N;
        }
        return d;
    }
}
