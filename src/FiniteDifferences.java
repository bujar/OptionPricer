/**
 * Finite Differences Algorithms
 * */
import static java.lang.Math.max;
public class FiniteDifferences extends Algorithm {

    int N;
    int M;
    double sMax;

    public FiniteDifferences(double sN, double sP, double rFR, double v, double t, boolean side, String type, int timeInterval, int priceInterval, double Max) {

        currPrice = sN;
        strikePrice = sP;
        riskFreeRate = rFR;
        volatility = v;
        term = t;
        this.side = side;
        optionType = type;
        N = timeInterval;
        M = priceInterval;
        sMax = Max;


    }

    @Override
    public final double doCalculation() {
        int i;
        int j;
        double deltaT = term / N;
        double deltaS = sMax / M;
        double[][] f = new double[N + 1][];
        for (i = 0; i <= N; i++) {
            f[i] = new double[M + 1];
            for (j = 0; j < M + 1; j++) {
                f[i][j] = 0.0;
            }
        }
        double[] stockPrice = new double[M + 1];
        for (j = 0; j <= M; j++) {
            stockPrice[j] = j * sMax / M;
        }
        for (i = 0; i <= N; i++) {
            f[i][0] = strikePrice;
            f[i][M] = 0.0;
        }
        for (j = 0; j <= M; j++) {
            f[N][j] = max(strikePrice - stockPrice[j], 0.0);
        }
        double[] a = new double[M - 1];
        double[] b = new double[M - 1];
        double[] c = new double[M - 1];
        double[] r = new double[M - 1];
        double[] u = new double[M - 1];
        for (j = 0; j < M - 1; j++) // note the offsets
        {
            a[j] = aj(j + 1, deltaT);
            b[j] = bj(j + 1, deltaT);
            c[j] = cj(j + 1, deltaT);
        }
        for (i = 1; i <= N; i++) {
            for (j = 0; j < M - 1; j++) {
                r[j] = f[N - i + 1][j + 1];
            }
            r[0] = r[0] - a[0] * f[N - i][0];
            r[M - 2] = r[M - 2] - c[M - 2] * f[N - i][M];

            tridiag(a, b, c, r, u, M - 1);
            for (j = 1; j < M; j++) {
                if (u[j - 1] < strikePrice - j * deltaS) {
                    f[N - i][j] = strikePrice - j * deltaS;
                } else {
                    f[N - i][j] = u[j - 1];
                }
            }
        }
        double optionValue;
        j = 0;
        while (stockPrice[j] < currPrice) {
            j++;
        }
        if (stockPrice[j] == currPrice) {
            optionValue = f[0][j];
        } else {
            optionValue = f[0][j - 1] + (f[0][j] - f[0][j - 1])
                    * (currPrice - stockPrice[j - 1])
                    / (stockPrice[j] - stockPrice[j - 1]);
        }
        a = null;
        b = null;
        c = null;
        r = null;
        u = null;
        stockPrice = null;
        for (i = 0; i <= N; i++) {
            f[i] = null;
        }
        f = null;
        return optionValue;
    }

    // Solve a tridiagonal system of linear equations
// Stolen directly from "Numerical Recipies in C++", 2nd ed. p. 54
// Some methods to generate the coefficients needed in
// Hull's technique for approximating a solution to
// the Black-Scholes solution for an American put option
// See Hull, 5th ed., p. 420
    public final void tridiag(double[] a, double[] b, double[] c, double[] r,
            double[] u, int length) {
        int j;
        double bet;
        double[] gam = new double[length];
        u[0] = r[0] / (bet = b[0]);
        for (j = 1; j < length; j++) {
            gam[j] = c[j - 1] / bet;
            bet = b[j] - a[j] * gam[j];
            u[j] = (r[j] - a[j] * u[j - 1]) / bet;
        }
        for (j = (length - 2); j >= 0; j--) {
            u[j] -= gam[j + 1] * u[j + 1];
        }
    }

    public final double aj(int j, double deltaT) {
        return (0.5 * riskFreeRate * j * deltaT - 0.5 * volatility * volatility
                * j * j * deltaT);
    }

    public final double bj(int j, double deltaT) {
        return (1.0 + volatility * volatility * j * j * deltaT + riskFreeRate
                * deltaT);
    }

    public final double cj(int j, double deltaT) {
        return (-0.5 * riskFreeRate * j * deltaT - 0.5 * volatility
                * volatility * j * j * deltaT);
    }
    public void createContentsInPage(){};
}
