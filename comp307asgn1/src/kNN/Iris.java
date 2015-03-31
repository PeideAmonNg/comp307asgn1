package kNN;


public class Iris {

	private double[] attributes;
	private String className;


	public Iris(double[] attributes, String className){
		this.attributes=attributes;
		this.className=className;
	}

	public double getAttributeSum(){
		double total=0d;
		for(double attr:attributes){
			total+=attr;
		}
		return total;
	}

	public String getClassName(){
		return this.className;
	}




}
