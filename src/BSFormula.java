/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Steve
 */
public class BSFormula extends Algorithm {

    public BSFormula(double sN, double sP, double rFR, double v, double t, boolean side, String type) {

        currPrice = sN;
        strikePrice = sP;
        riskFreeRate = rFR;
        volatility = v;
        term = t;
        this.side = side;
        optionType = type;
    }

    /*
     * Black-Scholes Forumla algorithm obtained from
     * http://trader2coder.wordpress.com/2013/06/30/black-scholes-model/ and
     * adjusted to fit our needs.
     */
    @Override
    public final double doCalculation() {
        double d1 = (Math.log(currPrice / strikePrice) + (riskFreeRate + (Math
                .pow(volatility, 2)) / 2) * term)
                / (volatility * Math.sqrt(term));
        double d2 = d1 - volatility * Math.sqrt(term);
        double Nd1 = calculateStNormDistrib(d1);
        double Nd2 = calculateStNormDistrib(d2);
        double callPremium = (currPrice * Nd1)
                - (Nd2 * strikePrice * (Math.exp(-riskFreeRate * term)));

// calculatePut
        double putPremium = callPremium + strikePrice
                * (Math.exp(-riskFreeRate * term)) - currPrice;

        if (side) {
            return callPremium;
        } else {
            return putPremium;
        }
    }

    /*
     * Function to calculate Gaussian Normal Distribution obtained from:
     * http://trader2coder.wordpress.com/2013/06/30/black-scholes-model/
     */
    public double calculateStNormDistrib(double x) {
        int neg = (x < 0d) ? 1 : 0;
        if (neg == 1) {
            x *= -1d;
        }

        double k = (1d / (1d + 0.2316419 * x));
        double y = ((((1.330274429 * k - 1.821255978) * k + 1.781477937) * k - 0.356563782)
                * k + 0.319381530)
                * k;
        y = 1.0 - 0.398942280401 * Math.exp(-0.5 * x * x) * y;

        return (1d - neg) * y + neg * (1d - y);
    }
    public void createContentsInPage(){};
}
