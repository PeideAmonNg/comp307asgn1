package kNN;


public class Iris {

	private double[] attributes;
	private String className;


	public Iris(double[] attributes, String className){
		this.attributes=attributes;
		this.className=className;
	}

	public double[] getAttributes(){
		return this.attributes;
	}

	public String getClassName(){
		return this.className;
	}




}
