package DSC;

import DSC.NormalityTest.INormalityTest;
import DSC.Tests.ISimilarityTest;
import Input.Algorithm;
import Input.Request;
import Output.AlgorithmRank;
import Output.ProblemSolutions;
import Output.Response;
import com.datumbox.framework.common.dataobjects.FlatDataCollection;
import com.datumbox.framework.common.dataobjects.TransposeDataCollection;
import com.datumbox.framework.core.statistics.parametrics.independentsamples.LevenesIndependentSamples;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.*;

public class DSCAlgorithm
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
        List<Integer> allAlgorithms = new ArrayList<>();
        for (int i = 0; i < algorithms.size(); i++)
        {
            allAlgorithms.add(i);
        }

        List<Set<Integer>> sets = new ArrayList<>();
        while (allAlgorithms.size()!=0)
        {
            int algo = allAlgorithms.remove(0);
            Set<Integer> s = new HashSet<>();
            for (int i = 0; i < algorithms.size(); i++)
            {
                if(i==algo)
                    s.add(algo);
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

        List<Set<Algorithm>> returnSets = new ArrayList<>();
        for(Set<Integer> s:sets)
        {
            Set<Algorithm> rs = new HashSet<>();
            for (int i:s)
            {
                rs.add(algorithms.get(i));
            }
            returnSets.add(rs);
        }
        return returnSets;
    }

    public Map<Algorithm, Double> ranking(Request data, int problemNumber, ISimilarityTest test)
    {
        //p-values between algorithm pairs
        RealMatrix m = this.calculatePValueSimilarityMatrix(test, data, problemNumber);
        //threshold limit
        double alphaLimit = data.getAlpha()/CombinatoricsUtils.binomialCoefficient(data.getNumberOfAlgorithms(), 2);
        //computing threshold
        m = this.significanceLevelThreshold(m, alphaLimit);

        //Algorithm means for specified problem
        HashMap<Algorithm, Double> algorithmMap = data.getMeans(problemNumber);


        //List of groups
        List<SetsSort> groups = new ArrayList<>();
        this.buildDisjunctiveSets(data.getAlgorithms(), m).forEach((s)->groups.add(new SetsSort(s, algorithmMap.get(s.iterator().next()))));
        Collections.sort(groups, Collections.reverseOrder());

        if(this.transitivityCheck(m))
        {
            List<Integer> temp = new ArrayList<>();
            for (int i = 0; i < groups.get(0).getAlgorithms().size(); i++) {
                temp.add(i+1);
            }

            int max = groups.get(0).getAlgorithms().size();
            groups.get(0).rank = (double)this.naturalNumberSum(1, max)/max;
            if(groups.size()>1)
            {
                for (int i = 1; i < groups.size() ; i++)
                {
                    int from = temp.get(temp.size()-1) + 1;
                    int to = temp.get(temp.size()-1) + groups.get(i).getAlgorithms().size();
                    temp = new ArrayList<>();
                    for (int j = from; j <= to ; j++) {
                        temp.add(j);
                    }
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
            //List of algorithms sorted by mean
            List<AlgoMeanPair> ls = new ArrayList<>();
            data.getAlgorithms().forEach((a)->ls.add(new AlgoMeanPair(a, algorithmMap.get(a))));
            Collections.sort(ls);

            HashMap<Algorithm, Double> rtrn = new HashMap<>();

            double rang = 0.0;
            while (!ls.isEmpty())
            {
                //Rank computation
                List<AlgoMeanPair> sameMeanList = new ArrayList<>();
                sameMeanList.add(ls.remove(0));
                while (!ls.isEmpty() && sameMeanList.get(0).getMean() == ls.get(0).getMean())
                {
                    sameMeanList.add(ls.remove(0));
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

    public boolean parametricTests(Response response, INormalityTest normalityTest)
    {
        Map<String, List<Double>> results = new HashMap<>();

        for(AlgorithmRank rank:response.getProblems().get(0).getResult())
        {
            results.put(rank.getAlgorithmName(), new ArrayList<>());
        }

        for(ProblemSolutions pr:response.getProblems())
        {
            for(AlgorithmRank r:pr.getResult())
            {
                results.get(r.getAlgorithmName()).add(r.getRank());
            }
        }

        for (Map.Entry<String, List<Double>> entry : results.entrySet())
        {
            double testResult = normalityTest.getPValue(entry.getValue().stream().mapToDouble(d->d).toArray());
            if (testResult < response.getMethod().getAlpha())
            {
                return false;
            }
        }

        TransposeDataCollection transposeDataCollection = new TransposeDataCollection();

        for (Map.Entry<String, List<Double>> entry : results.entrySet())
        {
            transposeDataCollection.put(entry.getKey(), new FlatDataCollection(Arrays.asList(entry.getValue().toArray())));
        }

        return LevenesIndependentSamples.testVariances(transposeDataCollection, response.getMethod().getAlpha());
    }
}
