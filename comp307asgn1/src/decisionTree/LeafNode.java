package decisionTree;

public class LeafNode extends Node{

	int count;
	String classLabel;
	double prob;
	
	
	public LeafNode(String classLabel, double prob, int count) {
		this.classLabel=classLabel;
		this.prob=prob;
		this.count=count;
	}
	
	@Override
	public boolean classify(Instance instance){
		int label=DataFileReader.categoryNames.indexOf(classLabel);
		if(label==instance.getCategory()){
			return true;
		}
		return false;
	}
	
	@Override
	public void report(String indent){
		if (count==0){
			System.out.format("%sUnknown\n", indent);
		}else{
			System.out.format("%sClass %s, prob=%4.2f\n", indent, classLabel, prob);
		}
	}

	

}
