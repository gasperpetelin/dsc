package Input;


public class Problem
{
    private String name;
    private double[] data;

    public Problem(String name, double[] data)
    {
        this.name = name;
        this.data = data;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double[] getData()
    {
        return data;
    }

    public void setData(double[] data)
    {
        this.data = data;
    }

    public double dataMean()
    {
        double sum = 0;
        for (double d:this.data)
            sum+=d;
        return sum/this.data.length;
    }




}
