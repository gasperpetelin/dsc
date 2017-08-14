package DSC2;

import Output.Response;
import SecondOutput.AlgorithmMeans;
import Tests.GroupDifferenceTest.IGroupDifferenceTest;
import Tests.GroupDifferenceTest.Utility.MatrixOperations;
import javafx.util.Pair;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DSC2Algorithm
{
    public PValueCalculationReturn calculatePValue(Response input, IGroupDifferenceTest nonParametricTest)
    {
        RealMatrix m = MatrixUtils.createRealMatrix(input.getProblems().size(), input.getNumberOfAlgorithms());

        for(int i = 0; i<input.getProblems().size(); i++)
        {
            for(int j = 0; j<input.getNumberOfAlgorithms(); j++)
            {
                Collections.sort(input.getProblems().get(i).getResult());
                m.setEntry(i, j, input.getProblems().get(i).getResult().get(j).getRank());
            }
        }

        List<AlgorithmMeans> means = new ArrayList<>();
        for (int i = 0; i < m.getColumnDimension(); i++)
        {
            double avg = Arrays.stream(m.getColumn(i)).sum()/input.getProblems().size();
            means.add(new AlgorithmMeans(input.getProblems().get(0).getResult().get(i).getAlgorithmName(), avg));
        }
        return new PValueCalculationReturn(nonParametricTest.getPValue(m.getData()), nonParametricTest.getTSValue(m.getData()), means);
    }

}
