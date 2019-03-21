package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SaveData<E> {
	

	public void writeObject(List<E> object, String file) throws IOException {
		 FileOutputStream fileOut = new FileOutputStream(file);
		 ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(object);
         out.close();
         fileOut.close();
      
	}
	
	public ArrayList<E> getArrayOfObjects(String file) throws Exception {
		FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        @SuppressWarnings("unchecked")
		ArrayList<E> listOfObjects = (ArrayList<E>) in.readObject();
        in.close();
        fileIn.close();
        return listOfObjects;
        
}
	public static void writeHashmap(HashMap<String, Integer> hashmap, String file) throws IOException{
		FileOutputStream fileOut = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(hashmap);
        out.close();
        fileOut.close();
	}
	
	public static HashMap<String, Integer> getHashMapData(String file) throws IOException, ClassNotFoundException{
		FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        @SuppressWarnings("unchecked")
		HashMap<String, Integer> hashmapRetrieved = (HashMap<String, Integer>) in.readObject();
        in.close();
        fileIn.close();
        return hashmapRetrieved;
		
	}
	public static void writeSortedMap(LinkedHashMap<String, Word> linkedMap, String file) throws IOException{
		FileOutputStream fileOut = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(linkedMap);
        out.close();
        fileOut.close();
	}
	
	public static LinkedHashMap<String, Word> getSortedMap(String file) throws IOException, ClassNotFoundException{
		FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        @SuppressWarnings("unchecked")
        LinkedHashMap<String, Word> linkedMapRetrieved = (LinkedHashMap<String, Word>) in.readObject();
        in.close();
        fileIn.close();
        return linkedMapRetrieved;
		
	}
	
}
