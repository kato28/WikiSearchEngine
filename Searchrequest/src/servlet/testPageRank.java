package servlet;

import java.util.ArrayList;

import main.CLI;
import main.SaveData;

public class testPageRank {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		SaveData<Double> sc = new SaveData<>();
		SaveData<Integer> s = new SaveData<>();

		ArrayList<Double> C = sc.getArrayOfObjects("C.txt");
		ArrayList<Integer> L = s.getArrayOfObjects("L.txt");
		ArrayList<Integer> I = s.getArrayOfObjects("I.txt");
		
		CLI cli = new CLI();
		cli.setC(C);
		cli.setI(I);
		cli.setL(L);
		cli.calculPageRank();
		double somme=0.0;
		for(Double d : cli.getPagerank()) {
			somme += d;
		}

		System.out.println("PAGERANK = "+somme);
	}

}
