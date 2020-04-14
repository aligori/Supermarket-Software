package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SoldProduct implements Serializable{
	private String barcode;
	private String name;
    private int quantity;
    private double sellprice;
    private MyDate sellDate;
	public SoldProduct(String barcode,String name, int quantity, double sellprice) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		MyDate sellDate = new MyDate(sdf.format(new Date()));
		this.barcode = barcode;
		this.name=name;
		this.quantity = quantity;
		this.sellprice = sellprice;
		this.sellDate = sellDate;
	}
	public String getBarcode() {
		return barcode;
	}
	public String getName() {
		return name;
	}
	public int getQuantity() {
		return quantity;
	}
	public double getSellprice() {
		return sellprice;
	}
	public MyDate getSellDate() {
		return sellDate;
	}
	@Override
	public String toString() {
			return  name + "   " + quantity + "   " + sellprice + "   " + quantity*sellprice+"\n";
	}
	

    
}
