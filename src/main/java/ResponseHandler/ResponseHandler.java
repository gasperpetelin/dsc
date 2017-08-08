package ResponseHandler;


import DSC.DSCAlgorithm;
import DSC2.DSC2Algorithm;
import Input.Algorithm;
import Input.Request;
import Output.AlgorithmRank;
import Output.Method;
import Output.ProblemSolutions;
import Output.Response;
import SecondOutput.Output;
import Tests.GroupDifferenceTest.*;
import Tests.NormalityTests.KolmogorovSmirnov;
import Tests.SimilarityTest.AndersonDarlingTest;
import Tests.SimilarityTest.ISimilarityTest;
import Tests.SimilarityTest.KolmogorovSmirnovTest;
import com.google.gson.Gson;
import javafx.util.Pair;

import java.util.Map;

public class ResponseHandler
{
    public static String calculatePValue(String request)
    {
        Response data = new Gson().fromJson(request, Response.class);

        IGroupDifferenceTest test = MethodFactory.getGroupDifferenceTest(data.getMethod().getName(), data.getNumberOfAlgorithms(), data.getParametricTest());

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

        ISimilarityTest test = MethodFactory.getSimilarityTest(data.getMethod());

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

        //TODO
        r.getMethod().setName("Friedman");
        r.setParametricTest(dscAlgorithm.parametricTests(r, new KolmogorovSmirnov()) ? 1 : 0);
        return new Gson().toJson(r);
    }
}
