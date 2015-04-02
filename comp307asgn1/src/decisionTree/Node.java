package decisionTree;

import java.util.ArrayList;
import java.util.List;

public class Node {

	public int attr;
	public Node left;
	public Node right;

	public Node(){
		
	}
	
	public Node(int attr, Node left, Node right){
		this.attr=attr;
		this.left=left;
		this.right=right;
	}
	
	public boolean classify(Instance instance){
		boolean path=instance.getAtt(attr);
		if(path){
			return left.classify(instance);
		}else{
			return right.classify(instance);
		}
	
	}

	public void report(String indent){
		System.out.format("%s%s = True:\n", indent, DataFileReader.attNames.get(attr));
		left.report(indent+"	");
		System.out.format("%s%s = False:\n", indent, DataFileReader.attNames.get(attr));
		right.report(indent+"	");
		}
}
