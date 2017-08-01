package DSC2.Tests;

import jsc.datastructures.MatchedData;
import jsc.distributions.Beta;
import jsc.distributions.ChiSquared;
import jsc.distributions.FriedmanM;
import jsc.tests.SignificanceTest;
import jsc.util.Maths;

public class FriedmanOriginal implements SignificanceTest {
    int k;
    int n;
    double C;
    double M;
    double S;
    double W;
    private double SP;
    private final MatchedData ranks;

    public FriedmanOriginal(MatchedData var1, double var2, boolean var4) {
        double var8 = 0.0D;
        this.S = 0.0D;
        this.n = var1.getBlockCount();
        this.k = var1.getTreatmentCount();
        if(this.k < 2) {
            throw new IllegalArgumentException("Less than two samples.");
        } else if(this.n < 2) {
            throw new IllegalArgumentException("Less than two blocks.");
        } else {
            this.ranks = var1.copy();
            double var10 = 0.5D * ((double)this.k + 1.0D);
            int var17 = this.ranks.rankByBlocks(var2);
            double[][] var12 = this.ranks.getData();

            for(int var6 = 0; var6 < this.k; ++var6) {
                var8 = 0.0D;

                for(int var5 = 0; var5 < this.n; ++var5) {
                    var8 += var12[var5][var6];
                }

                var8 /= (double)this.n;
                this.S += (var8 - var10) * (var8 - var10);
            }

            double var13 = (double)(this.k * (this.k + 1));
            double var15 = (double)(this.k * (this.k * this.k - 1));
            this.C = 1.0D - (double)var17 / ((double)this.n * var15);
            this.M = 12.0D * (double)this.n * this.S / var13 / this.C;
            this.W = this.M / ((double)this.n * ((double)this.k - 1.0D));

            try
            {
                this.SP = betaApproxSP(this.n, this.k, this.S, this.C);
            }
            catch (Exception ex)
            {
                this.SP = chiSquaredApproxSP(this.n, this.M);
            }
            //this.SP = exactSP(this.n, this.k, this.M);
            //this.SP = betaApproxSP(this.n, this.k, this.S, this.C);
            //if(var4) {
            //    this.SP = betaApproxSP(this.n, this.k, this.S, this.C);
            //} else if(this.k == 2 && this.n < 25 || this.k == 3 && this.n < 11 || this.k == 4 && this.n < 7 || this.k == 5 && this.n < 5 || this.k == 6 && this.n < 4 || this.k == 7 && this.n < 3 || this.k == 8 && this.n < 3 || this.k == 9 && this.n < 3 || this.k == 10 && this.n < 3) {
            //    this.SP = exactSP(this.n, this.k, this.M);
            //} else {
            //    this.SP = betaApproxSP(this.n, this.k, this.S, this.C);
            //}

        }
    }

    public FriedmanOriginal(MatchedData var1) {
        this(var1, 0.0D, false);
    }

    public static double betaApproxSP(int var0, int var1, double var2, double var4) {
        if(var1 < 2) {
            throw new IllegalArgumentException("Less than two samples.");
        } else if(var0 < 2) {
            throw new IllegalArgumentException("Less than two blocks.");
        } else if(var2 < 0.0D) {
            throw new IllegalArgumentException("Invalid S value.");
        } else if(var4 > 0.0D && var4 <= 1.0D) {
            double var6 = 0.5D * ((double)var1 - 1.0D) - 1.0D / (double)var0;
            double var8 = ((double)var0 - 1.0D) * var6;
            double var10 = (double)(var0 * var0) * var2;
            double var12 = var4 * 12.0D * (var10 - 1.0D) / ((double)(var0 * var0 * var1) * ((double)(var1 * var1) - 1.0D) + 2.0D);
            try {
                return 1.0D - Beta.incompleteBeta(var12, var6, var8, Maths.lnB(var6, var8));
            } catch (IllegalArgumentException var15) {
                throw new IllegalArgumentException("Cannot calculate beta approximation.");
            }
        } else {
            throw new IllegalArgumentException("Invalid correction factor for ties.");
        }
    }

    public static double chiSquaredApproxSP(int var0, double var1) {
        if(var0 < 2) {
            throw new IllegalArgumentException("Less than two samples.");
        } else if(var1 < 0.0D) {
            throw new IllegalArgumentException("Invalid M value.");
        } else {
            ChiSquared var3 = new ChiSquared((double)(var0 - 1));
            return 1.0D - var3.cdf(var1);
        }
    }

    public static double exactSP(int var0, int var1, double var2) {
        if(var1 < 2) {
            throw new IllegalArgumentException("Less than two samples.");
        } else if(var0 < 2) {
            throw new IllegalArgumentException("Less than two blocks.");
        } else if(var2 < 0.0D) {
            throw new IllegalArgumentException("Invalid M value.");
        } else {
            FriedmanM var4 = new FriedmanM(var0, var1);
            return 1.0D - var4.cdf(var2);
        }
    }

    public double getC() {
        return this.C;
    }

    public MatchedData getRanks() {
        return this.ranks;
    }

    public double getS() {
        return this.S;
    }

    public double getSP() {
        return this.SP;
    }

    public double getTestStatistic() {
        return this.M;
    }

    public double getW() {
        return this.W;
    }
}