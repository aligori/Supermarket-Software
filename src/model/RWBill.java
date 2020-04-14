package model;

import java.io.*;
import java.util.ArrayList;

public class RWBill {
	private ArrayList<Bill> bills;
	private static final String file = "bills.bin";
	private File fi;
	
	public RWBill() {
		bills=new ArrayList<>();
		fi=new File(file);
		if(fi.exists()) {
			readBills();
		} else {
			writeBills();
		}
	}

	private void writeBills() {
		try {
			FileOutputStream fos=new FileOutputStream(fi);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(bills);
			oos.close();fos.close();
		} catch (FileNotFoundException e) {
			System.err.println("File cannot be written!!!");
		} catch (IOException e) {
			System.err.println("Problem with writing object");
		}
	}
	
	private void readBills() {
		try {
			FileInputStream fis=new FileInputStream(fi);
			ObjectInputStream ois=new ObjectInputStream(fis);
			bills=(ArrayList<Bill>)ois.readObject();
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
	
	public ArrayList<Bill> getBills() {
		return bills;
	}
	
	public void addBill(Bill bill) {
		bills.add(bill);
		writeBills();
	}
}
