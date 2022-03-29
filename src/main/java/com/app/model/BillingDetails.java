package com.app.model;

import java.util.List;

public class BillingDetails {
	String totalAmount = null;
	List<BooksSet> setsOfDifferentBooks = null;

	public String getTotalAmount() {

		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<BooksSet> getSetsOfDifferentBooks() {
		return setsOfDifferentBooks;
	}

	public void setSetsOfDifferentBooks(List<BooksSet> setsOfDifferentBooks) {
		this.setsOfDifferentBooks = setsOfDifferentBooks;
	}

}
