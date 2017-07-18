package DSC2;

import Output.Response;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class DSC2Algorithm
{
    public double calculatePValue(Response input, INonParametricTest nonParametricTest)
    {
        RealMatrix m = MatrixUtils.createRealMatrix(input.getProblems().size(), input.getNumberOfAlgorithms());
        for(int i = 0; i<input.getProblems().size(); i++)
        {
            for(int j = 0; j<input.getNumberOfAlgorithms(); j++)
            {
                m.setEntry(i, j, input.getProblems().get(i).getResult().get(j).getRank());
            }
        }
        return nonParametricTest.getPValue(m.getData());
    }

}
