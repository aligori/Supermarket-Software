package model;

import java.io.Serializable;

public class Supplier implements Serializable{
     private String name;
     private String address;
     private String email;
     private String phone;
     
	public Supplier(String name, String address, String email, String phone) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
     
	
}
