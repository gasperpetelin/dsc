package DSC2;

import SecondOutput.AlgorithmMeans;

import java.util.ArrayList;
import java.util.List;

public class PValueCalculationReturn
{
    private double p;
    private double t;
    private List<AlgorithmMeans> means = new ArrayList<>();
    public PValueCalculationReturn(double p, double t, List<AlgorithmMeans> means)
    {
        this.p=p;
        this.t=t;
        this.means=means;
    }

    public double getP() {
        return p;
    }

    public double getT() {
        return t;
    }

    public List<AlgorithmMeans> getMeans() {
        return means;
    }
}
