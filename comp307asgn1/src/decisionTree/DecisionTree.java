package decisionTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DecisionTree {
	
	public static int TRUE=0;
	public static int FALSE=1;
	
	
	public static void main(String[] arg){
		String trainingFile=arg[0];
		DataFileReader reader=new DataFileReader();
		List<Instance> instances=reader.readDataFile(trainingFile);
		
		Node root=new Node();
		root.buildTree(instances, initialiseAttrList(reader.numAtts));
		printTree(root);
	}
	
	
	
	public static List<Integer> initialiseAttrList(int attrNo){
			List<Integer> list=new ArrayList<>();
			for(int i=0;i<attrNo;i++){
				list.add(i);
			}
			return list;
	}
	
	public static void printTree(Node root){
		
	}
}
