package ResponseHandler;


import DSC.DSCAlgorithm;
import DSC2.DSC2Algorithm;
import DSC2.PValueCalculationReturn;
import Input.Algorithm;
import Input.Request;
import Output.AlgorithmRank;
import Output.Method;
import Output.ProblemSolutions;
import Output.Response;
import SecondOutput.AlgorithmMeans;
import SecondOutput.Output;
import Tests.GroupDifferenceTest.*;
import Tests.NormalityTests.KolmogorovSmirnov;
import Tests.PAdjust.Holm;
import Tests.PAdjust.IAdjust;
import Tests.PAdjust.Unadjusted;
import Tests.PAdjust.Z;
import Tests.SimilarityTest.AndersonDarlingTest;
import Tests.SimilarityTest.ISimilarityTest;
import Tests.SimilarityTest.KolmogorovSmirnovTest;
import ThirdInput.Input;
import ThirdOutput.Adjusted;
import com.google.gson.Gson;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.util.Pair;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler
{
    public static String calculatePValue(String request)
    {
        Response data = new Gson().fromJson(request, Response.class);
        IGroupDifferenceTest test = MethodFactory.getGroupDifferenceTest(data.getMethod().getName(), data.getNumberOfAlgorithms(), data.getParametricTest());
        DSC2Algorithm dsc2Algorithm = new DSC2Algorithm();
        PValueCalculationReturn p_t_value = dsc2Algorithm.calculatePValue(data, test);
        Output out = null;
        if(p_t_value.getP() > data.getMethod().getAlpha())
            out = Output.confirmed(p_t_value.getP(), p_t_value.getT(), data.getMethod().getName(), data.getMethod().getAlpha(), p_t_value.getMeans());
        else
            out = Output.rejected(p_t_value.getP(), p_t_value.getT(), data.getMethod().getName(), data.getMethod().getAlpha(), p_t_value.getMeans());
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
        //TODO Sprememba testa
        r.getMethod().setName("F");
        r.setParametricTest(dscAlgorithm.parametricTests(r, new KolmogorovSmirnov()) ? 1 : 0);
        return new Gson().toJson(r);
    }

    public static String postHoc(String request)
    {
        Input data = new Gson().fromJson(request, Input.class);
        Map<String, Double> map = new HashMap<>();

        AlgorithmMeans R1 = null;

        for(AlgorithmMeans algorithm:data.getAlgorithmMeans())
        {
            if(algorithm.getAlgorithmName().equals(data.getBaseAlgorithm()))
            {
                R1 = algorithm;
                break;
            }
        }

        if(R1==null)
            throw new IllegalArgumentException("No algorithm with name " + data.getBaseAlgorithm());



        double factor = Math.sqrt((double)(data.getK()*(data.getK()+1))/(6*data.getN()));
        if(data.getMethod().equals("FriedmanAlign")||data.getMethod().equals("FA"))
            factor = Math.sqrt((double)(data.getK()*(data.getN()+1))/6);

        data.getAlgorithmMeans().remove(R1);


        List<IAdjust> iAdjusts = new ArrayList<>();
        iAdjusts.add(new Z());
        iAdjusts.add(new Unadjusted());
        iAdjusts.add(new Holm());


        List<Adjusted> adjustedList = new ArrayList<>();
        for(IAdjust test:iAdjusts)
        {
            List<ThirdOutput.Algorithm> ls = new ArrayList<>();
            Map<String, Double> adjMap = test.adjust(data.getAlgorithmMeans(), R1, factor);
            for (String key : adjMap.keySet())
            {
                ls.add(new ThirdOutput.Algorithm(key, adjMap.get(key)));
            }
            adjustedList.add(new Adjusted(test.getName(), ls));

        }
        ThirdOutput.Output out = new ThirdOutput.Output(adjustedList);
        return new Gson().toJson(out);
    }
}
