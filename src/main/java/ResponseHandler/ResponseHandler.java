package ResponseHandler;


import DSC.DSCAlgorithm;
import DSC.ISimilarityTest;
import DSC.Tests.AndersonDarlingTest;
import DSC.Tests.KolmogorovSmirnovTest;
import Input.Algorithm;
import Input.Request;
import JsonParsing.Parsing;
import Output.AlgorithmRank;
import Output.Method;
import Output.ProblemSolutions;
import Output.Response;
import com.google.gson.Gson;

import java.util.Map;

public class ResponseHandler
{
    public static String generateResponse(String request)
    {
        Request data = Parsing.getRequest(request);
        DSCAlgorithm dscAlgorithm = new DSCAlgorithm();

        //Changing test based on input
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
        //TODO correct parametric test initialization
        Response r = new Response(data.getNumberOfAlgorithms(), 0 /*parametric_tests initialization*/, m);
        for (int i = 0; i < data.getAlgorithm(0).getNumberOfProblems(); i++)
        {
            String problemName = data.getAlgorithm(0).getProblem(i).getName();
            ProblemSolutions s = new ProblemSolutions(problemName);
            Map<Algorithm, Double> map = dscAlgorithm.ranking(data, i, test);
            map.forEach((key, value) -> s.addAlgorithm(new AlgorithmRank(key.getName(), value)));
            r.addProblem(s);
        }
        return new Gson().toJson(r);
    }
}
