package model;

import java.io.Serializable;

public class Product implements Serializable{
	private String barcode;
	private String name;
	private String category;
	private String supplier;
	private double purchasedPrice;
	private double sellPrice;
	private int stock;

	public Product(String barcode, String name, String category, String supplier, double purchasedPrice, double sellPrice) {
		super();
		this.barcode = barcode;
		this.name = name;
		this.category = category;
		this.supplier = supplier;
		this.purchasedPrice = purchasedPrice;
		this.sellPrice = sellPrice;
		stock=0;
	}
	
	
	
	public Product(String barcode, String name, double sellPrice, int stock) {
		super();
		this.barcode = barcode;
		this.name = name;
		this.sellPrice = sellPrice;
		this.stock = stock;
	}



	public String getBarcode() {
		return barcode;
	}
	
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public double getPurchasedPrice() {
		return purchasedPrice;
	}
	public void setPurchasedPrice(double purchasedPrice) {
		this.purchasedPrice = purchasedPrice;
	}
	public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
