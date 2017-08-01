package Output;


public class Method
{
    private String name;
    private double alpha;

    public Method(String name, double alpha)
    {
        this.name = name;
        this.alpha = alpha;
    }

    public void setName(String name){this.name = name;}

    public String getName()
    {
        return name;
    }

    public double getAlpha()
    {
        return alpha;
    }
}
