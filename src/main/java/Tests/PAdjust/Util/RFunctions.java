package Tests.PAdjust.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RFunctions
{
    public static List<Integer> order(final double[] arr)
    {
        List<Integer> indices = new ArrayList<Integer>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            indices.add(i);
        }
        Comparator<Integer> comparator = new Comparator<Integer>()
        {
            public int compare(Integer i, Integer j)
            {
                return Double.compare(arr[i], arr[j]);
            }
        };
        Collections.sort(indices, comparator);
        return indices;
    }

    public static List<Integer> orderReverse(final double[] arr)
    {
        List<Integer> indices = new ArrayList<Integer>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            indices.add(i);
        }
        Comparator<Integer> comparator = new Comparator<Integer>()
        {
            public int compare(Integer i, Integer j)
            {
                return Double.compare(arr[j], arr[i]);
            }
        };
        Collections.sort(indices, comparator);
        return indices;
    }

    public static double[] cummax(double[] data)
    {
        double[] d = new double[data.length];
        d[0] = data[0];
        for (int i = 1; i < data.length; i++)
        {
            d[i] = Math.max(d[i-1], data[i]);
        }
        return d;
    }

    public static double[] cummin(double[] data)
    {
        double[] d = new double[data.length];
        d[0] = data[0];
        for (int i = 1; i < data.length; i++)
        {
            d[i] = Math.min(d[i-1], data[i]);
        }
        return d;
    }
}
