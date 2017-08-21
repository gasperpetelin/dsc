package Tests.PAdjust;

import SecondOutput.AlgorithmMeans;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Z implements IAdjust
{
    @Override
    public Map<String, Double> adjust(List<AlgorithmMeans> means, AlgorithmMeans algorithmName, double factor)
    {
        Map<String, Double> map = new HashMap<>();
        for(AlgorithmMeans algorithm:means)
        {
            double f = (algorithmName.getMean()-algorithm.getMean()) / factor;
            NormalDistribution nd = new NormalDistribution(0, 1);
            map.put(algorithm.getAlgorithmName(), f);
        }
        return map;
    }

    @Override
    public String getName() {
        return "ZValue";
    }
}
