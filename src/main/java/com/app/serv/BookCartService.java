package com.app.serv;

import java.util.List;

import com.app.model.BillingDetails;
import com.app.model.Book;
import com.app.model.BooksDiscountDetails;
import com.app.model.ShoppingCartItem;

public interface BookCartService {

	public void saveBook(final Book book);

	// Get all books  from the h2 database.
	public List<Book> getAllBooks();

	public void saveBooksDiscountDetails(BooksDiscountDetails discountItemInfo);

	public List<BooksDiscountDetails> getAllDiscountDetailsWithDiffBooks();

	public BillingDetails calculatePrice(List<ShoppingCartItem> shoppingCartItem);
}
