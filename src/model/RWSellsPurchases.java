package model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RWSellsPurchases {
    ArrayList<SoldProduct> soldList;
    ArrayList<PurchasedProduct> purchasedList;
    
	private static final String soldfile="sold.bin";
	private static final String purchasedfile = "purchased.bin";
	private File soldfi;
	private File purchasedfi;
	
    public RWSellsPurchases() {
		soldList=new ArrayList<>();
		purchasedList=new ArrayList<>();
		soldfi=new File(soldfile);
		purchasedfi= new File(purchasedfile);
		if(soldfi.exists()) {
			readSoldProducts();
		} else {
			writeSoldProducts();
		}
		if(purchasedfi.exists()) {
			readPurchasedProducts();
		} else {
			writePurchasedProducts();
		}
	}
    
	private void writeSoldProducts() {
		try {
			FileOutputStream fos=new FileOutputStream(soldfi);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(soldList);
			oos.close();fos.close();
		} catch (FileNotFoundException e) {
			System.err.println("File cannot be written!!!");
		} catch (IOException e) {
			System.err.println("Problem with writing object");
		}
	}
	
	private void readSoldProducts() {
		try {
			FileInputStream fis=new FileInputStream(soldfi);
			ObjectInputStream ois=new ObjectInputStream(fis);
			soldList=(ArrayList<SoldProduct>)ois.readObject();
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
	
	private void writePurchasedProducts() {
		try {
			FileOutputStream fos=new FileOutputStream(purchasedfi);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(purchasedList);
			oos.close();fos.close();
		} catch (FileNotFoundException e) {
			System.err.println("File cannot be written!!!");
		} catch (IOException e) {
			System.err.println("Problem with writing object");
		}
	}
	
	private void readPurchasedProducts() {
		try {
			FileInputStream fis=new FileInputStream(purchasedfi);
			ObjectInputStream ois=new ObjectInputStream(fis);
			purchasedList=(ArrayList<PurchasedProduct>)ois.readObject();
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
	
	public void addPurchasedProduct(int pos, String barcode, int quantity, double purchasedPrice, MyDate expiration) {	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		MyDate purchasedDate = new MyDate(sdf.format(new Date()));
		purchasedList.add(new PurchasedProduct(barcode, quantity, purchasedPrice, purchasedDate, expiration));
		for(PurchasedProduct i: purchasedList) {
			System.out.println(i);
		}
		RWProduct rwp = new RWProduct();
		rwp.addQuantity(pos,quantity);
		writePurchasedProducts();
	}
	
	public void addSoldProduct(int pos, SoldProduct soldProduct) {
		soldList.add(soldProduct);
	    RWProduct rwp = new RWProduct();
	    for(SoldProduct i: soldList) {
			System.out.println(i.toString());
		}
	    rwp.removeQuantity(pos,soldProduct.getQuantity());
		writeSoldProducts();
	}

	public ArrayList<SoldProduct> getSoldList() {
		return soldList;
	}

	public ArrayList<PurchasedProduct> getPurchasedList() {
		return purchasedList;
	}

}
