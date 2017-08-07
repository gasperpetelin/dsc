package ResponseHandler;


import DSC.DSCAlgorithm;
import Tests.NormalityTests.KolmogorovSmirnov;
import Tests.SimilarityTest.AndersonDarlingTest;
import Tests.SimilarityTest.ISimilarityTest;
import Tests.SimilarityTest.KolmogorovSmirnovTest;
import DSC2.DSC2Algorithm;
import Tests.GroupDifferenceTest.IGroupDifferenceTest;
import Tests.GroupDifferenceTest.Friedman;
import Tests.GroupDifferenceTest.PairedT;
import Tests.GroupDifferenceTest.RepeatedMeasureAnova;
import Tests.GroupDifferenceTest.WilcoxonSignedRank;
import Input.Algorithm;
import Input.Request;
import Output.AlgorithmRank;
import Output.Method;
import Output.ProblemSolutions;
import Output.Response;
import SecondOutput.Output;
import com.google.gson.Gson;
import javafx.util.Pair;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

public class ResponseHandler
{
    public static String calculatePValue(String request)
    {
        Response data = new Gson().fromJson(request, Response.class);
        IGroupDifferenceTest test = null;

        //TODO remove
        data.getMethod().setName("Friedman");

        //Test if only 2 algorithms
        if(data.getNumberOfAlgorithms()==2)
        {
            if(data.getParametricTest()==0)
            {
                switch (data.getMethod().getName())
                {
                    case "WSR":
                    case "WilcoxonSignedRank":
                        test = new WilcoxonSignedRank();
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid method argument");
                }
            }
            else
            {
                switch (data.getMethod().getName())
                {
                    case "PT":
                    case "PairedT":
                        test = new PairedT();
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid method argument");
                }
            }
        }
        else
        {
            if(data.getParametricTest()==0)
            {
                switch (data.getMethod().getName())
                {
                    case "F":
                    case "Friedman":
                        test = new Friedman();
                        break;
                    case "FA":
                    case "FriedmanAlign":
                        //TODO implement
                        throw new NotImplementedException();
                    case "ID":
                    case "ImanDavenport":
                        //TODO implement
                        throw new NotImplementedException();
                    default:
                        throw new IllegalArgumentException("Invalid method argument");
                }
            }
            else
            {
                switch (data.getMethod().getName())
                {
                    case "ANOVA":
                    case "RepeatedMeasureAnova":
                        test = new RepeatedMeasureAnova();
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid method argument");
                }
            }
        }

        DSC2Algorithm dsc2Algorithm = new DSC2Algorithm();
        Pair<Double, Double> p_t_value = dsc2Algorithm.calculatePValue(data, test);
        Output out = null;
        if(p_t_value.getKey() > data.getMethod().getAlpha())
            out = Output.confirmed(p_t_value.getKey(), p_t_value.getValue(), data.getMethod().getName(), data.getMethod().getAlpha());
        else
            out = Output.rejected(p_t_value.getKey(), p_t_value.getValue(), data.getMethod().getName(), data.getMethod().getAlpha());
        return new Gson().toJson(out);
    }

    public static String calculateRank(String request)
    {
        Request data = new Gson().fromJson(request, Request.class);
        DSCAlgorithm dscAlgorithm = new DSCAlgorithm();

        //Changing getPValue based on input
        ISimilarityTest test = null;
        if(data.getMethod() == null)
        {
            test = new KolmogorovSmirnovTest();
        }
        else
        {
            switch (data.getMethod())
            {
                case "AD":
                case "AndersonDarling":
                    test = new AndersonDarlingTest();
                    break;
                case "KS":
                case "KolmogorovSmirnov":
                    test = new KolmogorovSmirnovTest();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid method argument");
            }
        }


        Method m = new Method(data.getMethod(), data.getAlpha());
        Response r = new Response(data.getNumberOfAlgorithms(), m);
        for (int i = 0; i < data.getAlgorithm(0).getNumberOfProblems(); i++)
        {
            String problemName = data.getAlgorithm(0).getProblem(i).getName();
            ProblemSolutions s = new ProblemSolutions(problemName);
            Map<Algorithm, Double> map = dscAlgorithm.ranking(data, i, test);
            map.forEach((key, value) -> s.addAlgorithm(new AlgorithmRank(key.getName(), value)));
            r.addProblem(s);
        }
        r.setParametricTest(dscAlgorithm.parametricTests(r, new KolmogorovSmirnov()) ? 1 : 0);
        return new Gson().toJson(r);
    }
}
