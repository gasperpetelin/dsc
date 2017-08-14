package SecondOutput;

import com.google.gson.annotations.SerializedName;

public class AlgorithmMeans
{
    @SerializedName("name")
    private String algorithmName;
    private double mean;
    public AlgorithmMeans(String algorithmName, double mean)
    {
        this.algorithmName = algorithmName;
        this.mean = mean;
    }

    public double getMean() {
        return mean;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }
}
