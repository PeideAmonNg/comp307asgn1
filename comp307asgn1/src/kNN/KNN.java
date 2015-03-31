package kNN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KNN {

	public static int k=5;
	public static int attrNo=4;


	public static void main(String[] ar) {
		String trainingFile=ar[0];
		String testFile=ar[1];


		List<Iris> trainingIrises=readIrisFromFile(trainingFile);
		List<Iris> testIrises=readIrisFromFile(testFile);

		testClassifier(trainingIrises, testIrises);
	}

	public static List<Iris> readIrisFromFile(String fileName){
		BufferedReader br = null;
		List<Iris> irises=new ArrayList<Iris>();
		try {
			String currentLine;
			br = new BufferedReader(new FileReader(fileName));
			while ((currentLine = br.readLine()) != null) {
				String[] values=currentLine.split("  ");
				if(values.length==5){
					double[] doubles=new double[KNN.attrNo];
					for(int i=0;i<KNN.attrNo;i++){
						doubles[i]=Double.parseDouble(values[i]);
					}
					irises.add(new Iris(doubles, values[KNN.attrNo]));
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return irises;
	}

	public static void testClassifier(List<Iris> trainingIrises, List<Iris> testIrises){
		int correct=0;
		for(Iris iris:testIrises){
			List<DistDiff> diffList=compareIrises(iris, trainingIrises);
			Collections.sort(diffList, new IrisComparator());
			String className=findClass(diffList);
			if(className.equals(iris.getClassName())){
				correct++;
			}
		}
		System.out.printf("correct classification: %.1f%%", ((double)correct)/testIrises.size()*100);
	}
	
	//helper class
	public static String findClass(List<DistDiff> diffList){
		Map<String, Integer> map=new HashMap<String, Integer>();
		for(int i=0;i<KNN.k;i++){
			DistDiff diff=diffList.get(i);
			if(map.containsKey(diff.getClassName())){
				map.put(diff.getClassName(), map.get(diff.getClassName())+1);
			}else{
				map.put(diff.getClassName(), 1);
			}
		}
		
		int max=0;
		String className=null;
		for (Map.Entry<String, Integer> entry : map.entrySet()){
			if(entry.getValue()>max){
				className=entry.getKey();
			}
		}
		return className;
	}


	//compare each test iris with all training irises
	//Result: a set of objects with distances and class name.
	public static List<DistDiff> compareIrises(Iris testIris, List<Iris> trainingIrises){
		List<DistDiff> diffList=new ArrayList<DistDiff>();		
		for(Iris iris:trainingIrises){
			double totalDiff=0d;
			for(int i=0;i<KNN.attrNo;i++){
				totalDiff+=Math.pow(testIris.getAttributes()[i]-iris.getAttributes()[i], 2);
			}
			
			DistDiff distDiff=new DistDiff(Math.sqrt(totalDiff), iris.getClassName());
			diffList.add(distDiff);
		}
		return diffList;
	}
}

class IrisComparator implements Comparator<DistDiff>{
	@Override
	public int compare(DistDiff a, DistDiff b) {
		if(a.getDist()<b.getDist()){
			return -1;
		}if(a.getDist()>b.getDist()){
			return 1;
		}else{
			return 0;
		}

	}

}

