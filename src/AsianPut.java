/**
 * AsianPut extends Option
 */
public class AsianPut extends Option{
	public boolean getDoBinomialTree(){
		return false;
	}
	public boolean getDoFiniteDifferences(){
		return false;
	}
	public boolean getDoSimulation(){
		return false;
	}
}
