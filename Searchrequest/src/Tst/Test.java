package Tst;

import java.util.ArrayList;

import cleaning.SaveData;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		SaveData<Integer> s = new SaveData<>();
		ArrayList<Integer> L = s.getArrayOfObjects("L.txt");
		System.out.println(L.size());
	}

}
