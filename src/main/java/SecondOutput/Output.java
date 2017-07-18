package SecondOutput;

public class Output
{
    private String message;
    private double pValue;
    private double tValue;
    private Method method;

    private Output(String message, double p, double t, String method, double alpha)
    {
        this.message = message;
        this.pValue = p;
        this.tValue = t;
        this.method = new Method(method, alpha);
    }

    public static Output rejected(double p, double t, String method, double alpha)
    {
        return new Output("The null hypothesis is rejected.", p ,t, method, alpha);
    }

    public static Output confirmed(double p, double t, String method, double alpha)
    {
        return new Output("The null hypothesis is not rejected.", p ,t, method, alpha);
    }
}
