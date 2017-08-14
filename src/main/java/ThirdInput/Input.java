package ThirdInput;

import SecondOutput.AlgorithmMeans;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Input
{
    @SerializedName("algorithm_means")
    private List<AlgorithmMeans> algorithmMeans;

    @SerializedName("base_algorithm")
    private String baseAlgorithm;
    private int k;
    private int n;
    private String method;

    public List<AlgorithmMeans> getAlgorithmMeans() {
        return algorithmMeans;
    }

    public int getK() {
        return k;
    }

    public int getN() {
        return n;
    }

    public String getBaseAlgorithm() {
        return baseAlgorithm;
    }

    public String getMethod() {
        return method;
    }
}
