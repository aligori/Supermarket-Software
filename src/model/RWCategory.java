package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RWCategory {
	private ArrayList<Category> categories;
	private static final String file="categories.bin";
	private File fi;
	
	public RWCategory() {
		categories=new ArrayList<>();
		fi=new File(file);
		if(fi.exists()) {
			readCategory();
		} else {
			writeCategory();
		}
	}

	private void writeCategory() {
		try {
			FileOutputStream fos=new FileOutputStream(fi);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(categories);
			oos.close();fos.close();
		} catch (FileNotFoundException e) {
			System.err.println("File cannot be written!!!");
		} catch (IOException e) {
			System.err.println("Problem with writing object");
		}
	}
	
	private void readCategory() {
		try {
			FileInputStream fis=new FileInputStream(fi);
			ObjectInputStream ois=new ObjectInputStream(fis);
			categories=(ArrayList<Category>)ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found!!!");
		} catch (IOException e) {
			System.err.println("File is corrupted");
		} catch (ClassNotFoundException e) {
			System.err.println("Class does not match");
		}
	}

	public ArrayList<Category> getCategories() {
		return categories;
	}
    
	public void addCategory(Category c) {
		categories.add(c);
		writeCategory();
	}
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
	
}
