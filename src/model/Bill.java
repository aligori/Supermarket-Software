package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Bill implements Serializable{
    private static int billNo=0;
    private String cashierUsername;
    private MyDate billDate;
    private double total;
    private ArrayList<SoldProduct> soldProducts;
    
	public Bill(String cashierUsername, MyDate billDate, double total,ArrayList<SoldProduct> soldProducts) {
		this.cashierUsername = cashierUsername;
		this.billDate = billDate;
		this.total = total;
		this.soldProducts=soldProducts;
		billNo++;
	}
	public static int getBillNo() {
		return billNo;
	}
	public String getCashierUsername() {
		return cashierUsername;
	}
	public MyDate getBillDate() {
		return billDate;
	}
	public double getTotal() {
		return total;
	}
	public ArrayList<SoldProduct> getSoldProducts() {
		return soldProducts;
	}
    
    
}
