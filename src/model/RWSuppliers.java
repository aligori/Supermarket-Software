package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class RWSuppliers {
	private ArrayList<Supplier> suppliers;
	private static final String file = "suppliers.bin";
	private File fi;
	
	public RWSuppliers() {
		suppliers=new ArrayList<>();
		fi=new File(file);
		if(fi.exists()) {
			readSuppliers();
		} else {
			writeSuppliers();
		}
	}

	private void writeSuppliers() {
		try {
			FileOutputStream fos=new FileOutputStream(fi);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(suppliers);
			oos.close();fos.close();
		} catch (FileNotFoundException e) {
			System.err.println("File cannot be written!!!");
		} catch (IOException e) {
			System.err.println("Problem with writing object");
		}
	}
	
	private void readSuppliers() {
		try {
			FileInputStream fis=new FileInputStream(fi);
			ObjectInputStream ois=new ObjectInputStream(fis);
			suppliers=(ArrayList<Supplier>)ois.readObject();
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
	
	public ArrayList<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(ArrayList<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public Supplier getSupplierByName(String name) {
		for(Supplier x : suppliers) {
			if(x.getName()==name) {
				return x;
			}
		}			
		return null;
	}
	
	public void addSupplier(Supplier supplier) {
		System.out.println(suppliers.add(supplier));
		writeSuppliers();
	}
	
	public void deleteSupplier(int position) {
		suppliers.remove(position); //method remove(object o) wasn't working that's why i removed supplier by position
		writeSuppliers();
	}
	
	public boolean supplierRegistered(String s) { //used when registering a new supplier
		for(Supplier x: suppliers) {
			if(x.getName().equals(s))
				return true;
		}
		return false;
	}
	
	public int getPosition(Supplier sup) {
		
		for(int i=0; i<suppliers.size(); i++)
		{
			if(suppliers.get(i).getName().equals(sup.getName()))
				return i;
		}	
		return -1;
	}
}
