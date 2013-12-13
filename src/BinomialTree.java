/**
 * For BinomialTree
 */
import static java.lang.Math.*;

public class BinomialTree extends Algorithm {

    int numIntervals;

    // Basic values of a stock option; A client can change
// these by using the "set" methods declared below
    public BinomialTree(double sN, double sP, double rFR, double v, double t,  boolean side, String type, int num) {

        currPrice = sN;
        strikePrice = sP;
        riskFreeRate = rFR;
        volatility = v;
        term = t;
        numIntervals = num;
        this.side = side;
        optionType = type;
    }
    // An inner class used by the binomial tree valuation method

    private static class Price {

        public double stockPrice = 0;
        public double optionPrice = 0;
    }
    
    @Override
    public final double doCalculation() {
        double deltaT = term / numIntervals;
        double up = 1.0 + riskFreeRate * deltaT
                + (volatility * Math.sqrt(deltaT));
        double down = 1.0 + riskFreeRate * deltaT
                - (volatility * Math.sqrt(deltaT));
        double upProb = 0.5;
        double downProb = 0.5;
        double binomValue;
        Price[][] binomialTree = new Price[numIntervals + 1][numIntervals + 1];

        for (int i = 0; i <= numIntervals; i++) {
            for (int j = 0; j <= i; j++) {
                binomialTree[i][j] = new Price();
                binomialTree[i][j].stockPrice = currPrice * Math.pow(up, j)
                        * Math.pow(down, i - j);
            }
        }
        for (int j = 0; j < numIntervals; j++) {
            if (side) {
                binomialTree[numIntervals][j].optionPrice = max(
                        binomialTree[numIntervals][j].stockPrice - strikePrice,
                        0.0);
            }
            if (!side) {
                binomialTree[numIntervals][j].optionPrice = max(strikePrice
                        - binomialTree[numIntervals - 1][j].stockPrice, 0.0);
            }
        }
        double discount = Math.exp(-riskFreeRate * deltaT);
        for (int i = numIntervals - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                if (side) {
                    binomialTree[i][j].optionPrice = max(
                            binomialTree[i][j].stockPrice - strikePrice,
                            discount
                            * (upProb
                            * binomialTree[i + 1][j + 1].optionPrice + downProb
                            * binomialTree[i + 1][j].optionPrice));
                }
                if (!side) {
                    binomialTree[i][j].optionPrice = max(
                            strikePrice - binomialTree[i][j].stockPrice,
                            discount
                            * (upProb
                            * binomialTree[i + 1][j + 1].optionPrice + downProb
                            * binomialTree[i + 1][j].optionPrice));
                }
            }
        }
        binomValue = binomialTree[0][0].optionPrice;
        for (int n = 0; n <= numIntervals; n++) {
            binomialTree[n] = null;
        }
        binomialTree = null;
        return binomValue;
    }
    public void createContentsInPage(){};
}
