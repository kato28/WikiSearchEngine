package main;

import java.util.ArrayList;

public class readPageRankSaved {

	public static void main(String[] args) {
		SaveData<Double> sv = new SaveData<>();
		double somme =0.0;
		try {
			ArrayList<Double> pgSaved  = sv.getArrayOfObjects("pagerank.txt");
			for(int i=0; i<pgSaved.size(); i++) {
				if(i<2000)
					System.out.println(pgSaved.get(i));
				somme+=pgSaved.get(i);
			}
			System.out.println("somme : "+somme);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
