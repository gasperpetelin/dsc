package Tests.PAdjust;

import SecondOutput.AlgorithmMeans;

import java.util.List;
import java.util.Map;

public interface IAdjust
{
    Map<String, Double> adjust(List<AlgorithmMeans> means, AlgorithmMeans algorithmName, double factor);
    String getName();
}
