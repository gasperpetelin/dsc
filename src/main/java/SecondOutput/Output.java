package SecondOutput;

import com.google.gson.annotations.SerializedName;
import javafx.util.Pair;

import java.util.List;

public class Output
{
    private String message;
    @SerializedName("p_value")
    private double pValue;
    @SerializedName("t")
    private double tValue;
    private Method method;
    @SerializedName("algorithm_means")
    private List<AlgorithmMeans>algorithmMeans;

    private Output(String message, double p, double t, String method, double alpha, List<AlgorithmMeans> means)
    {
        this.message = message;
        this.pValue = p;
        this.tValue = t;
        this.method = new Method(method, alpha);
        this.algorithmMeans = means;
    }

    public static Output rejected(double p, double t, String method, double alpha, List<AlgorithmMeans>means)
    {
        return new Output("The null hypothesis is rejected.", p ,t, method, alpha, means);
    }

    public static Output confirmed(double p, double t, String method, double alpha, List<AlgorithmMeans> means)
    {
        return new Output("The null hypothesis is not rejected.", p ,t, method, alpha, means);
    }
}
