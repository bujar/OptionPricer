/**
 * Parent of all algorithms, contains doCalculation and doGraph methods
 */
import java.util.ArrayList;
public class Algorithm {

    // Basic values of a stock option; A client can change
    // these by using the "set" methods declared below
    double currPrice;
    double strikePrice;
    double riskFreeRate;
    double volatility;
    double term;
    boolean side;
    String optionType;
    double[] volArray = new double[11];
    double[] volIntervals = new double[11];
    /**abstract methods**/
    public double doCalculation(){
    	return 0.0;
    };
    
    public void doGraph() {
        for (int i = 0; i < volArray.length-1; ++i) {
            volIntervals[i] = ((float) i / 10);
            this.setVolatility(volIntervals[i]);
            volArray[i] = this.doCalculation();
        }
        Drawer graph = new Drawer("Option Prcer", volArray, volIntervals);
        graph.pack();
        graph.setVisible(true);
        
    }

    // get and set methods
    public final double getcurrPrice() {
        return currPrice;
    }

    public final void setcurrPrice(double s) {
        currPrice = s;
    }

    public final double getStrikePrice() {
        return strikePrice;
    }

    public final void setStrikePrice(double s) {
        strikePrice = s;
    }

    public final double getRiskFreeRate() {
        return riskFreeRate;
    }

    public final void setRiskFreeRate(double r) {
        riskFreeRate = r;
    }

    public final double getVolatility() {
        return volatility;
    }

    public final void setVolatility(double v) {
        volatility = v;
    }

    public final double getTerm() {
        return term;
    }

    public final void setTerm(double t) {
        term = t;
    }
}