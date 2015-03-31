package kNN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KNN {

	private int k=5;


	public static void main(String[] ar) {
		String trainingFile=ar[0];
		String testFile=ar[1];


		List<Iris> trainingIrises=getIrises(trainingFile);
		List<Iris> testIrises=getIrises(testFile);

		System.out.println(trainingIrises.size());
		System.out.println(testIrises.size());
	}

	public static List<Iris> getIrises(String fileName){
		BufferedReader br = null;
		List<Iris> irises=new ArrayList<Iris>();
		try {
			String currentLine;
			br = new BufferedReader(new FileReader(fileName));
			while ((currentLine = br.readLine()) != null) {
				String[] values=currentLine.split("  ");
				if(values.length==5){
					double[] doubles=new double[4];
					for(int i=0;i<4;i++){
						doubles[i]=Double.parseDouble(values[i]);
					}
					irises.add(new Iris(doubles, values[4]));
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return irises;
	}

	public static void testClassifier(List<Iris> trainingIrises, List<Iris> testIrises, String testFile){
		for(Iris iris:testIrises){
			List<DistDiff> diffList=compareIrises(iris, trainingIrises);
			Collections.sort(diffList, new IrisComparator());
			//sort the set.
			//according to k, choose the number of neighbours.

		}
	}


	//compare each test iris with all training irises
	//Result: a set of objects with distances and class name.
	private static List<DistDiff> compareIrises(Iris testIris, List<Iris> trainingIrises){
		List<DistDiff> diffList=new ArrayList<DistDiff>();
		for(Iris iris:trainingIrises){
			double diff=Math.abs(testIris.getAttributeSum()-iris.getAttributeSum());
			DistDiff distDiff=new DistDiff(diff, iris.getClassName());
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

