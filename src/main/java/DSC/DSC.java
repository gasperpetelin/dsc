package DSC;

import Input.AlgoMeanPair;
import Input.Algorithm;
import Input.Request;
import Input.SetsSort;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.*;

public class DSC
{
    private RealMatrix calculatePValueSimilarityMatrix(ISimilarityTest test, Request data, int problemNumber)
    {
        RealMatrix m = MatrixUtils.createRealMatrix(new double[data.getNumberOfAlgorithms()][data.getNumberOfAlgorithms()]);
        for (int i = 0; i < data.getNumberOfAlgorithms() ; i++)
        {
            for (int j = i; j < data.getNumberOfAlgorithms(); j++)
            {
                if(i==j)
                {
                    m.setEntry(i, j, 1);
                }
                else
                {
                    double[] algo1data = data.getAlgorithm(i).getProblem(problemNumber).getData();
                    double[] algo2data = data.getAlgorithm(j).getProblem(problemNumber).getData();
                    double pValue = test.getPValue(algo1data, algo2data);
                    m.setEntry(i, j, pValue);
                    m.setEntry(j, i, pValue);
                }
            }
        }
        return m;
    }

    private RealMatrix significanceLevelThreshold(RealMatrix matrix, double alphaLimit)
    {
        int matrixDimension = matrix.getColumnDimension();
        RealMatrix m = MatrixUtils.createRealMatrix(new double[matrixDimension][matrixDimension]);
        for (int i = 0; i < matrixDimension ; i++)
        {
            for (int j = 0; j < matrixDimension; j++)
            {
                if(matrix.getEntry(i, j) >= alphaLimit)
                    m.setEntry(i, j, 1);
                else
                    m.setEntry(i, j, 0);
            }
        }
        return m;
    }

    private boolean transitivityCheck(RealMatrix matrix)
    {
        RealMatrix t = matrix.multiply(matrix);
        for (int i = 0; i < matrix.getRowDimension(); i++)
        {
            for (int j = 0; j < matrix.getColumnDimension(); j++)
            {
                if(t.getEntry(i, j)!=0)
                {
                    if(matrix.getEntry(i, j) == 0)
                        return false;
                }
            }
        }
        return true;
    }

    private List<Set<Algorithm>> buildDisjunctiveSets(List<Algorithm> algorithms, RealMatrix connectionMatrix)
    {
        List<Integer> allAlgorithms = new ArrayList<Integer>();
        for (int i = 0; i < algorithms.size(); i++)
        {
            allAlgorithms.add(i);
        }

        List<Set<Integer>> sets = new ArrayList<Set<Integer>>();
        while (allAlgorithms.size()!=0)
        {
            int algo = allAlgorithms.remove(0);
            Set<Integer> s = new HashSet<Integer>();
            for (int i = 0; i < algorithms.size(); i++)
            {
                if(i==algo)
                {
                    s.add(algo);
                }
                else
                {
                    if(connectionMatrix.getEntry(algo, i)==1)
                    {
                        allAlgorithms.remove(new Integer(i));
                        s.add(i);
                    }

                }
            }
            sets.add(s);
        }
        List<Set<Algorithm>> returnSets = new ArrayList<Set<Algorithm>>();
        for(Set<Integer> s:sets)
        {
            Set<Algorithm> rs = new HashSet<Algorithm>();
            for (int i:s)
            {
                rs.add(algorithms.get(i));
            }
            returnSets.add(rs);
        }
        return returnSets;
    }

    public Map<Algorithm, Double> ranking(Request data, int problemNumber)
    {
        //p-values between algorithm pairs
        RealMatrix m = this.calculatePValueSimilarityMatrix(new KolmogorovSmirnovTest(), data, problemNumber);
        //threshold limit
        double alphaLimit = data.getAlpha()/CombinatoricsUtils.binomialCoefficient(data.getNumberOfAlgorithms(), 2);
        //computing threshold
        m = this.significanceLevelThreshold(m, alphaLimit);

        //Algorithm means for specified problem
        HashMap<Algorithm, Double> algorithmMap = data.getMeans(problemNumber);


        //List of groups
        List<SetsSort> groups = new ArrayList<>();
        this.buildDisjunctiveSets(data.getAlgorithms(), m).forEach((s)->groups.add(new SetsSort(s, algorithmMap.get(s.iterator().next()))));
        Collections.sort(groups);

        if(this.transitivityCheck(m))
        {
            int max = groups.get(0).getAlgorithms().size();
            groups.get(0).rank = (double)this.naturalNumberSum(1, max)/max;
            if(groups.size()>1)
            {
                for (int i = 1; i < groups.size() ; i++)
                {
                    int from = groups.get(i-1).getAlgorithms().size() + 1;
                    int to = groups.get(i-1).getAlgorithms().size() + groups.get(i).getAlgorithms().size();
                    int sum = this.naturalNumberSum(from, to);
                    groups.get(i).rank = (double) sum/groups.get(i).getAlgorithms().size();
                }
            }

            final HashMap<Algorithm, Double> rtrn = new HashMap<>();
            for(SetsSort g:groups)
            {
                g.getAlgorithms().forEach((a)->rtrn.put(a, g.rank));
            }
            return rtrn;
        }
        else
        {
            List<AlgoMeanPair> ls = new ArrayList<>();
            for (int i = 0; i < data.getNumberOfAlgorithms(); i++)
            {
                Algorithm a = data.getAlgorithm(i);
                ls.add(new AlgoMeanPair(a, algorithmMap.get(a)));
            }

            Collections.sort(ls);

            HashMap<Algorithm, Double> rtrn = new HashMap<>();

            double rang = 0.0;
            while (!ls.isEmpty())
            {
                List<AlgoMeanPair> sameMeanList = new ArrayList<>();
                sameMeanList.add(ls.remove(0));
                while (!ls.isEmpty() && sameMeanList.get(0).getMean() == ls.get(0).getMean())
                {
                    sameMeanList.add(ls.get(0));
                }

                if(sameMeanList.size()==1)
                {
                    rang++;
                    rtrn.put(sameMeanList.get(0).getAlgorithm(), rang);
                }
                else
                {
                    for (AlgoMeanPair p : sameMeanList)
                    {
                        rang++;
                        double r = (rang + rang + sameMeanList.size()-1) / sameMeanList.size();
                        rang += sameMeanList.size() - 1;
                        rtrn.put(p.getAlgorithm(), r);
                    }
                }
            }
            return  rtrn;
        }
    }

    public int naturalNumberSum(int from, int to)
    {
        int max = (to*(to+1))/2;
        from--;
        int min = (from*(from+1))/2;
        return max-min;
    }

}
