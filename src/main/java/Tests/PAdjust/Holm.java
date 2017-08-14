package Tests.PAdjust;

import SecondOutput.AlgorithmMeans;
import Tests.PAdjust.Util.RFunctions;

import java.util.List;
import java.util.Map;

public class Holm implements IAdjust {
    @Override
    public Map<String, Double> adjust(List<AlgorithmMeans> means, AlgorithmMeans algorithmName, double factor)
    {
        Map<String, Double> map = new Unadjusted().adjust(means, algorithmName, factor);
        double[] order = new double[map.size()];
        int i = 0;
        for (String key : map.keySet())
        {
            order[i]=map.get(key);
            i++;
        }
        List<Integer> intOrder = RFunctions.order(order);

        double[] order2 = new double[order.length];
        for (int j = 0; j < order2.length; j++)
        {
            order2[j] = (order.length - j)*order[intOrder.get(j)];
        }

        double[] cum = RFunctions.cummax(order2);
        for (int j = 0; j < cum.length; j++)
        {
            if(cum[j]>1)
                cum[j]=1;
        }

        List<Integer> ro = RFunctions.order(intOrder.stream().mapToDouble(d -> d).toArray());
        double[] reorder = new double[cum.length];
        for (int j = 0; j < reorder.length; j++)
        {
            reorder[j] = cum[ro.get(j)];
        }

        i=0;
        for (String key : map.keySet())
        {
            map.put(key, reorder[i]);
            i++;
        }


        return map;
    }

    @Override
    public String getName() {
        return "Holm";
    }

}
