/**
 * For Simulation
 */
import static java.lang.Math.*;

public class Simulation extends Algorithm {

    int numTrials;
    int numIntervals;

    public Simulation(double sN, double sP, double rFR, double v, double t, boolean side, String type, int trials, int intervals) {

        currPrice = sN;
        strikePrice = sP;
        riskFreeRate = rFR;
        volatility = v;
        term = t;
        this.side = side;
        optionType = type;
        numTrials = trials;
        numIntervals = intervals;
    }

    @Override
    public final double doCalculation() {
        int i;
        int trialCount;
        double deltaT = term / (double) numIntervals;
        double trialRunningSum;
        double trialAverage;
        double trialPayoff;
        double simulationRunningSum;
        double simulationAveragePayoff;
        double s;

        java.util.Random rand = new java.util.Random();
        simulationRunningSum = 0.0;
        for (trialCount = 1; trialCount <= numTrials; trialCount++) {
            s = currPrice;
            trialRunningSum = 0.0;
            double nns = 0;
            for (i = 0; i < numIntervals; i++) {
                nns = (double) rand.nextGaussian();
                s = s
                        * Math.exp((riskFreeRate - volatility * volatility / 2)
                        * deltaT + volatility * nns * Math.sqrt(deltaT));
                trialRunningSum += s;

            }
            trialAverage = trialRunningSum / numIntervals;
            if (side) {
                trialPayoff = max(trialAverage - strikePrice, 0.0);
            } else {
                trialPayoff = max(strikePrice - trialAverage, 0.0);
            }
            simulationRunningSum += trialPayoff;
        }
        simulationAveragePayoff = simulationRunningSum / numTrials;
        double valueOfOption;
        valueOfOption = simulationAveragePayoff
                * Math.exp(-riskFreeRate * term);
        return valueOfOption;
    }
    public void createContentsInPage(){};
}
