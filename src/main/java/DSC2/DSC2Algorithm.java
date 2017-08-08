package DSC2;

import Input.Problem;
import Output.ProblemSolutions;
import Tests.GroupDifferenceTest.IGroupDifferenceTest;
import Output.Response;
import javafx.util.Pair;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Collections;

public class DSC2Algorithm
{
    public Pair<Double, Double> calculatePValue(Response input, IGroupDifferenceTest nonParametricTest)
    {
        RealMatrix m = MatrixUtils.createRealMatrix(input.getProblems().size(), input.getNumberOfAlgorithms());
        for(int i = 0; i<input.getProblems().size(); i++)
        {
            for(int j = 0; j<input.getNumberOfAlgorithms(); j++)
            {
                //ProblemSolutions p = input.getProblems().get(i);
                Collections.sort(input.getProblems().get(i).getResult());
                m.setEntry(i, j, input.getProblems().get(i).getResult().get(j).getRank());
            }
        }
        return new Pair<>(nonParametricTest.getPValue(m.getData()), nonParametricTest.getTSValue(m.getData()));
    }

}
