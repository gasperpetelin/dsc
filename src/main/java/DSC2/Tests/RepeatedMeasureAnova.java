package DSC2.Tests;


import DSC2.INonParametricTest;
import org.apache.commons.math3.stat.inference.OneWayAnova;

import java.util.ArrayList;
import java.util.List;

public class RepeatedMeasureAnova implements INonParametricTest
{
    //TODO implementd oneway anova
    @Override
    public double getPValue(double[][] data)
    {
        return new OneWayAnova().anovaPValue(this.dataTransformation(data));
    }

    private List<double[]> dataTransformation(double[][]data)
    {
        List<double[]> d = new ArrayList<>();
        for (int i = 0; i < data[0].length; i++)
        {
            double[] ls = new double[data.length];
            for (int j = 0; j < data.length; j++)
            {
                ls[j] = data[j][i];
            }
            d.add(ls);
        }
        return d;
    }

    @Override
    public double getTSValue(double[][] data)
    {
        return new OneWayAnova().anovaFValue(this.dataTransformation(data));
    }
}
