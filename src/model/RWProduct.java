package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class RWProduct {
	
	private ArrayList<Product> products;
	private static final String file="products.bin";
	private File fi;
	
	public RWProduct() {
		products=new ArrayList<>();
		fi=new File(file);
		if(fi.exists()) {
			readProducts();
		} else {
			writeProducts();
		}
	}

	private void writeProducts() {
		try {
			FileOutputStream fos=new FileOutputStream(fi);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(products);
			oos.close();fos.close();
		} catch (FileNotFoundException e) {
			System.err.println("File cannot be written!!!");
		} catch (IOException e) {
			System.err.println("Problem with writing object");
		}
	}
	
	private void readProducts() {
		try {
			FileInputStream fis=new FileInputStream(fi);
			ObjectInputStream ois=new ObjectInputStream(fis);
			products=(ArrayList<Product>)ois.readObject();
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
	
	public Product getProductByBarcode(String barcode) {
		for(Product x : products) {
			if((x.getBarcode()).equals(barcode)) {
				return x;
			}
		}			
		return null;
	}
	public Product getProductByName(String name) {
		for(Product x : products) {
			if((x.getBarcode()).equals(name)) {
				return x;
			}
		}			
		return null;
	}
	
	public boolean barcodeInUse(String barcode) {
		for(Product x : products) {
			if((x.getBarcode()).equals(barcode)) {
				return true;
			}
		}			
		return false;
	}
	
	
	public ArrayList<Product> getProducts() {
		return products;
	}

	public void addProduct(Product product) {
		products.add(product);
		writeProducts();
	}
	
	public void deleteProduct(Product product) {
		System.out.println(products.remove(product));
		writeProducts();
	}
	
	public int getPosition(Product ed) {
		
		for(int i=0; i<products.size(); i++)
		{
			if(products.get(i).getBarcode()==ed.getBarcode())
				return i;
		}	
		return -1;
	}
	
	public void addQuantity(int pos, int quantity) {
		products.get(pos).setStock(products.get(pos).getStock()+quantity);
		writeProducts();
	}

	public void removeQuantity(int pos, int quantity) {
		products.get(pos).setStock(products.get(pos).getStock()-quantity);
		writeProducts();
	}
}
