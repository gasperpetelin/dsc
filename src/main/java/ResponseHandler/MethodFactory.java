package ResponseHandler;

import Tests.GroupDifferenceTest.*;
import Tests.SimilarityTest.AndersonDarlingTest;
import Tests.SimilarityTest.ISimilarityTest;
import Tests.SimilarityTest.KolmogorovSmirnovTest;

public class MethodFactory
{
    public static IGroupDifferenceTest getGroupDifferenceTest(String testName, int numberOfAlgorithms, int parametricValue)
    {
        if(numberOfAlgorithms<=1)
            throw new IllegalArgumentException("At least 2 algorithms are requred");
        if(parametricValue != 0 && parametricValue != 1)
            throw new IllegalArgumentException("At parametric value");


        IGroupDifferenceTest test;
        if(numberOfAlgorithms==2)
        {
            if(parametricValue==0)
            {
                switch (testName)
                {
                    case "WSR":
                    case "WilcoxonSignedRank":
                        return new WilcoxonSignedRank();
                    default:
                        throw new IllegalArgumentException("Invalid method argument");
                }
            }
            else
            {
                switch (testName)
                {
                    case "PT":
                    case "PairedT":
                        return new PairedT();
                    default:
                        throw new IllegalArgumentException("Invalid method argument");
                }
            }
        }
        else
        {
            if(parametricValue==0)
            {
                switch (testName)
                {
                    case "F":
                    case "Friedman":
                        return new Friedman();
                    case "FA":
                    case "FriedmanAlign":
                        return new FriedmanAlign();
                    case "ID":
                    case "ImanDavenport":
                        return new ImanDavenport();
                    default:
                        throw new IllegalArgumentException("Invalid method argument");
                }
            }
            else
            {
                switch (testName)
                {
                    case "ANOVA":
                    case "RepeatedMeasureAnova":
                        return new RepeatedMeasureAnova();
                    default:
                        throw new IllegalArgumentException("Invalid method argument");
                }
            }
        }
    }

    public static ISimilarityTest getSimilarityTest(String testName)
    {
        ISimilarityTest test = null;
        if(testName == null)
        {
            return new KolmogorovSmirnovTest();
        }
        else
        {
            switch (testName)
            {
                case "AD":
                case "AndersonDarling":
                    return new AndersonDarlingTest();
                case "KS":
                case "KolmogorovSmirnov":
                    return new KolmogorovSmirnovTest();
                default:
                    throw new IllegalArgumentException("Invalid method argument");
            }
        }
    }
}
