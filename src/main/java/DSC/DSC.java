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

    public Map<Integer, Double> ranking(Request data, int problemNumber)
    {
        RealMatrix m = this.calculatePValueSimilarityMatrix(new KolmogorovSmirnovTest(), data, problemNumber);
        double alphaLimit = data.getAlpha()/CombinatoricsUtils.binomialCoefficient(data.getNumberOfAlgorithms(), 2);

        m = this.significanceLevelThreshold(m, alphaLimit);

        boolean transitive = this.transitivityCheck(m);


        HashMap<Algorithm, Double> algorithmMap = data.getMeans(problemNumber);

        double[] means = new double[data.getNumberOfAlgorithms()];

        for (int i = 0; i < data.getNumberOfAlgorithms(); i++)
        {
            double sum = 0;
            for(double d : data.getAlgorithm(i).getProblem(problemNumber).getData())
                sum+=d;
            means[i] = sum/data.getAlgorithm(i).getProblem(problemNumber).getData().length;
        }

        List<Integer> allAlgorithms = new ArrayList<Integer>();
        for (int i = 0; i < m.getRowDimension(); i++)
        {
            allAlgorithms.add(i);
        }

        RealMatrix m1 = MatrixUtils.createRealMatrix(data.getNumberOfAlgorithms(), data.getNumberOfAlgorithms());

        for (int i = 0; i < data.getNumberOfAlgorithms(); i++)
        {
            for (int j = 0; j < data.getNumberOfAlgorithms(); j++)
            {
                if(i==j)
                    m1.setEntry(i, j, 0);
                else
                    m1.setEntry(i, j , m.getEntry(i, j));
            }
        }

        List<Set<Algorithm>> sets = this.buildDisjunctiveSets(data.getAlgorithms(), m1);
        List<SetsSort> groups = new ArrayList<SetsSort>();
        for(Set<Algorithm> s:sets)
        {
            groups.add(new SetsSort(s, algorithmMap.get(s.iterator().next())));
        }


        Collections.sort(groups);

        if(transitive)
        {
            List<Integer> temp = new ArrayList<Integer>();
            for (int i = 1; i < groups.get(0).getAlgorithms().size() + 1; i++)
            {
                temp.add(i);
            }
            groups.get(0).rank = this.sum(temp)/temp.size();
            if(groups.size()>1)
            {
                for (int i = 1; i < groups.size() ; i++)
                {
                    int from = groups.get(i-1).getAlgorithms().size() + 1;
                    int to = groups.get(i-1).getAlgorithms().size() + groups.get(i).getAlgorithms().size();
                    int sm = 0;
                    for (int j = from; j < to +1 ; j++)
                    {
                        sm+=j/groups.get(i).getAlgorithms().size();
                    }
                    groups.get(i).rank = sm;
                }
            }

            HashMap<Integer, Double> rtrn = new HashMap<Integer, Double>();
            for(SetsSort g:groups)
            {
                for(Algorithm algofg:g.getAlgorithms())
                {
                    rtrn.put(data.getAlgorithms().lastIndexOf(algofg), g.rank);
                }
            }
            return rtrn;
        }
        else
        {
            List<AlgoMeanPair> ls = new ArrayList<AlgoMeanPair>();
            for (int i = 0; i < means.length; i++)
            {
                ls.add(new AlgoMeanPair(i, means[i]));
            }

            Collections.sort(ls);

            HashMap<Integer, Double> rtrn = new HashMap<Integer, Double>();

            double rang = 0.0;
            while (!ls.isEmpty())
            {
                List<AlgoMeanPair> sameMeanList = new ArrayList<AlgoMeanPair>();
                sameMeanList.add(ls.remove(0));
                while (!ls.isEmpty() && sameMeanList.get(0).mean == ls.get(0).mean)
                {
                    sameMeanList.add(ls.get(0));
                }

                if(sameMeanList.size()==1)
                {
                    rang++;
                    rtrn.put(sameMeanList.get(0).algorithm, rang);
                }
                else
                {
                    for (AlgoMeanPair p : sameMeanList)
                    {
                        rang++;
                        double r = (rang + rang + sameMeanList.size()-1) / sameMeanList.size();
                        rang += sameMeanList.size() - 1;
                        rtrn.put(p.algorithm, r);
                    }
                }
            }
            return  rtrn;
        }
    }

    public double sum(List<Integer> ls)
    {
        double d = 0;
        for(Integer db:ls)
        {
            d+=db;
        }
        return d;
    }

}
