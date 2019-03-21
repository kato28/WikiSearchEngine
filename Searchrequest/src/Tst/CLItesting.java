package Tst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CLItesting {
	static ArrayList<Double> C = new ArrayList<>();
	static ArrayList<Integer> L = new ArrayList<>();
	static ArrayList<Integer> I = new ArrayList<>();
	static ArrayList<Double> pagerank = new ArrayList<Double>();
	static ArrayList<Double> pagerank2 = new ArrayList<>();
	static ArrayList<Integer> randoms = new ArrayList<>();


	final private static double dampingFactor = 0.15;
	final private static double eps = 0.001;
	private static final int K = 7;

	public static void main(String[] args) {

		C.addAll(Arrays.asList(0.5,0.5,0.5,0.5,1.0,1/5d,1/5d,1/5d,1/5d,1/5d,1/2d,1/2d));
		L.addAll(Arrays.asList(0,2,2,4,5,5,10,12,12));
		I.addAll(Arrays.asList(0,1,0,2,1,0,1,2,3,4,0,1));
		for(int i=0;i<L.size()-1;i++) {
			randoms.add(i);
		}

		calculPageRank();
		System.out.println(C);

	}


	public static void calculPageRank() {

		boolean exit = false;
		double init;
		double somme = 0;
		init = (double) 1/(L.size()-1);
		double a,b;

		//Initialiser à 1/N (1/ total pages ie 200K or nodes?)
		for (int i = 0; i < L.size()-1; i++) {
			pagerank2.add(i, 0.0);
			pagerank.add(i, init);

		}

		while (!exit) {
			//System.out.println("whille ::pagerank1"+pagerank);
			//System.out.println("while:: pagerank2"+pagerank2);
			for (int i = 0; i < L.size()-1; i++) {
				if(L.get(i) != L.get(i+1)) {
					for (int j = L.get(i); j < L.get(i + 1); j++) {

						a =  C.get(j) * pagerank.get(i);

						//b =  (1-dampingFactor ) * ( pagerank2.get(I.get(j)) + a ) +(dampingFactor/(L.size()-1));

						pagerank2.set(I.get(j),pagerank2.get(I.get(j))+a);
					}
					//System.out.println("for ::pagerank1"+pagerank);
					//System.out.println("for ::pagerank2"+pagerank2);
				}else {
					calculRandomIteration(i);
				}
			}
			
			for(int j=0;j<pagerank2.size();j++) {
				pagerank2.set(j, (1 - dampingFactor) * pagerank2.get(j) +(dampingFactor/(L.size()-1)));
			}
			
			//dumping factor


			//Stop condition
			exit = false;
			for (int i = 0; i < pagerank.size(); i++) {
				if (Math.abs(pagerank.get(i)-pagerank2.get(i))<eps) {
					exit=true;
					break;
				}
			}
			
			pagerank.clear();
			for(int k=0;k<pagerank2.size();k++) {
				pagerank.add(pagerank2.get(k));
			}
			for(int i=0;i<pagerank2.size();i++) {
				pagerank2.set(i, 0.0);
			}
			
		}
		for(Double d : pagerank) {
			somme += d;
		}
		
		System.out.println(pagerank2);

		System.out.println("Page Rank ");
		for (int i = 0; i < L.size()-1; i++) {
			System.out.print("Page "+i+":"+pagerank.get(i)+" ** ");
		}
		System.out.println();
		System.out.println("la somme : "+somme);
	}
	private static void calculRandomIteration(int i) {
		int[] indexs = randomIndexs();
		double a;
		double b;
		for( int j=0;j<indexs.length;j++) {
			a= pagerank.get(i)/K;
			b= pagerank2.get(indexs[j])+a;
			pagerank2.set(indexs[j], b);
		}
	}
	
	private static int[] randomIndexs() {
		int [] result = new int[K];
		Collections.shuffle(randoms);
		for(int i=0;i<K;i++) {
			result[i] = randoms.get(i);
		}
		return result;
	}
}