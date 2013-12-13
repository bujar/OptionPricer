/**
 * AlgorithmFactory, to generate different Algorithms
 */
import java.util.ArrayList;
public class AlgorithmFactory {
	public Algorithm createAlgorithm(String a, ArrayList variables ){
    	if(a.equals("B-S Formula")){
			return new BSFormula((Double)variables.get(0),(Double)variables.get(1),(Double)variables.get(2),(Double)variables.get(3),(Double)variables.get(4),(Boolean)variables.get(5),(String)variables.get(6));
		}
		if(a.equals("Binomial Tree")){
			return new BinomialTree((Double)variables.get(0),(Double)variables.get(1),(Double)variables.get(2),(Double)variables.get(3),(Double)variables.get(4),(Boolean)variables.get(5),(String)variables.get(6),(Integer)variables.get(7));
		}
		if(a.equals("Numerical Integration")){
			return new FiniteDifferences((Double)variables.get(0),(Double)variables.get(1),(Double)variables.get(2),(Double)variables.get(3),(Double)variables.get(4),(Boolean)variables.get(5),(String)variables.get(6),(Integer)variables.get(7),(Integer)variables.get(8),(Double)variables.get(9));
		}
		if(a.equals("Simulation")){
			return new Simulation((Double)variables.get(0),(Double)variables.get(1),(Double)variables.get(2),(Double)variables.get(3),(Double)variables.get(4),(Boolean)variables.get(5),(String)variables.get(6),(Integer)variables.get(7),(Integer)variables.get(8));
		}	
		return null;
    }
}
