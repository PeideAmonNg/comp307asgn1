package decisionTree;

public class LeafNode extends Node{

	String classLabel;
	double prob;
	
	
	public LeafNode(String classLabel, double prob) {
		this.classLabel=classLabel;
		this.prob=prob;
	}
	

}
