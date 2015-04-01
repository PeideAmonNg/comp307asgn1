package decisionTree;

import java.util.ArrayList;
import java.util.List;

public class Node {

	
	public Node left;
	public Node right;

	
	
	public Node buildTree(List<Instance> instances, List<Integer> attributes){
		if(instances.isEmpty()){
			//		return a leaf node containing the name and probability of the overall
			//		most probable class (ie, the ‘‘baseline’’ predictor)
		}
		if(areInstancesPure(instances)){
			//		return a leaf node containing the name of the class of the instances
			//		in the node and probability 1			
		}
		
		if(attributes.isEmpty()){
			//		return a leaf node containing the name and probability of the
			//		majority class of the instances in the node (choose randomly
			//		if classes are equal)
			int cat1=0;
			int cat2=0;
			for(Instance instance:instances){
				if(instance.getCategory()==0){
					cat1++;
				}else{
					cat2++;
				}	
			}
			
			if(cat1>cat2){
				String label=DataFileReader.categoryNames.get(0);
				return new LeafNode(label, ((double)cat1)/(cat1+cat2));
			}else if(cat2>cat1){
				String label=DataFileReader.categoryNames.get(1);
				return new LeafNode(label, ((double)cat2)/(cat1+cat2));
			}else{
				int random=(int)(Math.random()*2);
				String label=DataFileReader.categoryNames.get(random);
				int cat = (random==0) ? cat1: cat2;
				return new LeafNode(label, ((double)cat)/(cat1+cat2));
			}
			
		}
		else{ //find best attr
			//		for each attribute
			//		separate instances into two sets:
			//		instances for which the attribute is true, and
			//		instances for which the attribute is false
			//		compute purity of each set.
			//		if weighted average purity of these sets is best so far
			//		bestAtt = this attribute
			//		bestInstsTrue = set of true instances
			//		bestInstsFalse = set of false instances
			//		build subtrees using the remaining attributes:
			//		left = BuildTree(bestInstsTrue, attributes - bestAtt)
			//		right = BuildTree(bestInstsFalse, attributes - bestAttr)
			//		return Node containing (bestAtt, left, right)
			Integer bestAttr=0;
			double minImpurity=100;
			List<Instance> bestInstsTrue=new ArrayList<Instance>();
			List<Instance> bestInstsFalse=new ArrayList<Instance>();
			for(Integer attr:attributes){
				List<Instance> trueInstances=getInstances(instances, attr, true);
				List<Instance> falseInstances=getInstances(instances, attr, false); 
				double trueImpurity=computeImpurity(trueInstances);
				double falseImpurity=computeImpurity(falseInstances);
				double impurity=computeWeightedAverageImPurity(trueImpurity, falseImpurity);
				if(impurity<minImpurity){
					bestAttr=attr;
					minImpurity=impurity;
					bestInstsTrue=trueInstances;
					bestInstsFalse=falseInstances;
				}
				attributes.remove(bestAttr);
				left=new Node();
				right=new Node();
				left.buildTree(bestInstsTrue, attributes);
				right.buildTree(bestInstsFalse, attributes);
			}
		}
		return null;
	}
	
	//helper method to builTree()
	public double computeWeightedAverageImPurity(double trueImpurity, double falseImpurity){		
		if((trueImpurity-0)<0.1 && (falseImpurity-0)<0.1){
			return 0;
		}else if((trueImpurity-0)<0.1){
			return falseImpurity*0.8;
		}else if((falseImpurity-0)<0.1){
			return trueImpurity*0.8;
		}else{
			return (trueImpurity+falseImpurity)/2;
		}
	}
	
	//helper method to builTree()
	private double computeImpurity(List<Instance> instances){
		int cat1=0;
		int cat2=0;
		for(Instance instance:instances){
			if(instance.getCategory()==0){
				cat1++;
			}else{
				cat2++;
			}
		}
		double impurity=((double)cat1)/instances.size() * ((double)cat2)/instances.size();
		return impurity;
	}
	
	//helper method to builTree()
	public List<Instance> getInstances(List<Instance> instances, int attr, boolean attrBool){
		List<Instance> list=new ArrayList<Instance>();
		for(Instance instance:instances){
			if(instance.getAtt(attr)==attrBool){
				list.add(instance);
			}
		}
		return list;
	}
	
	//helper method to builTree()
	public boolean areInstancesPure(List<Instance> instances){
		return false;
	}
}
