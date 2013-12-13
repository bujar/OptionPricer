/**
 * Drawer File to draw the graph
 */
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartPanel;

public class Drawer extends JFrame {

private static final long serialVersionUID = 1L;

public Drawer(String applicationTitle, double[] vA, double[] vI) {
super(applicationTitle);

XYSeries set = new XYSeries("Option Pricer Volatility Chart");
/* increment through array and add to XY axis dataset */
for (int i = 0; i < vA.length - 1; ++i)
set.add(vA[i] * 10, vI[i]);

final XYSeriesCollection data = new XYSeriesCollection(set);
/* Define axis' and data */
final JFreeChart chart = ChartFactory.createXYLineChart("Result",
"Volatility (%)", "Stock Price", data,
PlotOrientation.VERTICAL, true, true, false);

final ChartPanel chartPanel = new ChartPanel(chart);
chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
setContentPane(chartPanel);
}
}