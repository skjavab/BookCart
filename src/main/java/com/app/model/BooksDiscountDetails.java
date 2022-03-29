package com.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BooksDiscountDetails {

	@Id
	@GeneratedValue
	private int id;
	private int differentCopies;
	private int discount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDifferentCopies(int differentCopies) {
		this.differentCopies = differentCopies;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getDifferentCopies() {
		return differentCopies;
	}

	public int getDiscount() {
		return discount;
	}
}
