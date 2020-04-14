package model;

import java.io.Serializable;

public class PurchasedProduct implements Serializable{
	private String barcode;
    private int quantity;
    private double purchasedprice;
    private MyDate purchasedDate;
    private MyDate expiryDate;
	public PurchasedProduct(String barcode, int quantity, double purchasedPrice, MyDate purchasedDate,MyDate expiryDate) {
		super();
		this.barcode = barcode;
		this.quantity = quantity;
		this.purchasedprice = purchasedPrice;
		this.purchasedDate = purchasedDate;
		this.expiryDate = expiryDate;
	}
	@Override
	public String toString() {
		return "PurchasedProduct [barcode=" + barcode + ", quantity=" + quantity + ", purchasedprice=" + purchasedprice
				+ ", purchasedDate=" + purchasedDate + ", expiryDate=" + expiryDate + "]";
	}
	public String getBarcode() {
		return barcode;
	}
	public int getQuantity() {
		return quantity;
	}
	public double getPurchasedprice() {
		return purchasedprice;
	}
	public MyDate getPurchasedDate() {
		return purchasedDate;
	}
	public MyDate getExpiryDate() {
		return expiryDate;
	}
    
}
