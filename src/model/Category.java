package model;

import java.io.Serializable;

public class Category implements Serializable {
    String category;

	public Category(String category) {
		super();
		this.category = category;
		
	}

	@Override
	public String toString() {
		return category;
	}
}
