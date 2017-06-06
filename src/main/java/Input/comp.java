package Input;

import java.util.Comparator;


public class comp implements Comparator<SetsSort>
{
    public int compare(SetsSort o1, SetsSort o2)
    {
        return (int)Math.ceil(o1.getMean() - o2.getMean());
    }
}
