package Tests.PAdjust;

import SecondOutput.AlgorithmMeans;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Unadjusted implements IAdjust
{

    @Override
    public Map<String, Double> adjust(List<AlgorithmMeans> means, AlgorithmMeans algorithmName, double factor)
    {
        NormalDistribution nd = new NormalDistribution(0, 1);
        Map<String, Double> map = new Z().adjust(means, algorithmName, factor);
        for (String key : map.keySet())
        {
            map.put(key, nd.cumulativeProbability(map.get(key)));
        }
        return map;
    }

    @Override
    public String getName() {
        return "UnadjustedPValue";
    }
}
