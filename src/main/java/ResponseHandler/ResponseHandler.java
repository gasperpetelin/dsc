package ResponseHandler;


import DSC.DSC;
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
        DSC dsc = new DSC();

        Method m = new Method(data.getMethod(), data.getAlpha());
        //TODO correct parametric test initialization
        Response r = new Response(data.getNumberOfAlgorithms(), 0, m);
        for (int i = 0; i < data.getAlgorithm(0).getNumberOfProblems(); i++)
        {
            String problemName = data.getAlgorithm(0).getProblem(i).getName();
            ProblemSolutions s = new ProblemSolutions(problemName);
            Map<Algorithm, Double> map = dsc.ranking(data, i);
            map.forEach((key, value) -> s.addAlgorithm(new AlgorithmRank(key.getName(), value)));
            r.addProblem(s);
        }
        return new Gson().toJson(r);
    }
}
