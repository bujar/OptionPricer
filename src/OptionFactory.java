/**
 * OptionFactory, to generate different objects
 */
public class OptionFactory {
	public Option createProducts(String choice){
		if(choice.equals("American Call"))
			return new AmericanCall();
		if(choice.equals("American Put"))
			return new AmericanPut();
		if(choice.equals("European Call"))
			return new EuropeanCall();
		if(choice.equals("European Put"))
			return new EuropeanPut();
		if(choice.equals("Asian Call"))
			return new AsianCall();
		if(choice.equals("Asian Put"))
			return new AsianPut();
		return null;
	}
}
