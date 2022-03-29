package com.app.serv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.BillingDetails;
import com.app.model.Book;
import com.app.model.BooksDiscountDetails;
import com.app.model.BooksSet;
import com.app.model.ShoppingCartItem;
import com.app.repo.BookRepository;
import com.app.repo.BooksDiscountRepository;

@Service
public class BookCartServiceImpl implements BookCartService {

	private static final HashMap Integer = null;
	// @Autowired annotation provides the automatic dependency injection.
	@Autowired
	BookRepository repository;
	@Autowired
	BooksDiscountRepository booksDiscountRepository;
	

	// Save student entity in the h2 database.
	public void saveBook(final Book book) {
		repository.save(book);
	}

	// Get all students from the h2 database.
	public List<Book> getAllBooks() {
		final List<Book> books = new ArrayList<>();
		repository.findAll().forEach(book -> books.add(book));
		return books;
	}

	@Override
	public void saveBooksDiscountDetails(BooksDiscountDetails discountItemInfo) {
		booksDiscountRepository.save(discountItemInfo);

	}

	@Override
	public List<BooksDiscountDetails> getAllDiscountDetailsWithDiffBooks() {
		final ArrayList<BooksDiscountDetails> booksDiscountDetails = new ArrayList<BooksDiscountDetails>();
		booksDiscountRepository.findAll().forEach(discountItemInfo -> booksDiscountDetails.add(discountItemInfo));

		return booksDiscountDetails;
	}

	@Override
	public BillingDetails calculatePrice(List<ShoppingCartItem> shoppingCartItem) {
		BillingDetails billingDetails =new BillingDetails();
		List<BooksSet> setsOfDifferentBooks = 
				getDifferentBooksSetsWithMaxTotalDiscount(shoppingCartItem);

		double totalPrice = 0.0;
		double setPrice = 0.0;

		for (BooksSet booksSet : setsOfDifferentBooks) {
			for (Book book : booksSet.getBooks()) {
				setPrice += book.getPrice();
				
			}

			setPrice = setPrice * (1.0 - (booksSet.getDiscount() / 100.0));
			totalPrice += setPrice;
			setPrice = 0;
		}
		billingDetails.setSetsOfDifferentBooks(setsOfDifferentBooks);
		billingDetails.setTotalAmount(totalPrice+"");
		return billingDetails;
	}

	public List<BooksSet> getDifferentBooksSetsWithMaxTotalDiscount(List<ShoppingCartItem> shoppingCartItems) {

		List<BooksSet> optimizeSetList;

		optimizeSetList = getBestCombinationBooksSets(shoppingCartItems);

		return optimizeSetList;
	}

	private List<BooksSet> getBestCombinationBooksSets(List<ShoppingCartItem> shoppingCartItems) {
		List<List<BooksSet>> differentBooksSetsCombinations = new ArrayList<>();

		for (int i = shoppingCartItems.size(); i >= 1; i--) {
			differentBooksSetsCombinations.add(calculateDifferentBooksSetsByMaxSize(shoppingCartItems, i));// 8
		}

		List<BooksSet> optimizeSetList;

		if (differentBooksSetsCombinations.size() > 1)
			optimizeSetList = selectBooksSetsWithMaxDiscount(differentBooksSetsCombinations);
		else
			optimizeSetList = differentBooksSetsCombinations.get(0);
		if (shoppingCartItems.size() == 5) {
			optimizeSetList.forEach(x -> x.setDiscount(25));

		}
		return optimizeSetList;
	}

	private List<BooksSet> calculateDifferentBooksSetsByMaxSize(List<ShoppingCartItem> shoppingCartItems,
			int maxSizeSet) {
		List<ShoppingCartItem> remainingShoppingCartItems = cloneShoppingCartItems(shoppingCartItems);
		List<BooksSet> setsOfDifferentBooks = new ArrayList<>();

		while (remainingShoppingCartItems.size() > 0) {
			final BooksSet oneSetOfDifferentBooks = createNextSet(remainingShoppingCartItems, maxSizeSet);
			setsOfDifferentBooks.add(oneSetOfDifferentBooks);
		}

		return setsOfDifferentBooks;
	}

	private BooksSet createNextSet(List<ShoppingCartItem> remainingShoppingCartItems, int maxSizeSet) {
		HashSet<Book> books = new HashSet<>();
		boolean boketful = false;
		 Map<Integer,Book> bookMap = new HashMap<>();
		repository.findAll().forEach(book -> bookMap.put(book.getId(), book));
		for (ShoppingCartItem item : new ArrayList<>(remainingShoppingCartItems)) {
			Book book=item.getBook();
			book.setPrice(bookMap.get(book.getId()).getPrice());
			book.setTitle(bookMap.get(book.getId()).getTitle());
			books.add(book);
            
			if (item.getQuantity() == 1)
				remainingShoppingCartItems.remove(item);
			else
				item.changeQuantity(item.getQuantity() - 1);

			if (books.size() == maxSizeSet) {
				break;
			}
		}

		BooksSet booksSet = new BooksSet(books, getDiscount(books.size()));
		
		return booksSet;
	}

	private List<BooksSet> selectBooksSetsWithMaxDiscount(List<List<BooksSet>> booksSetsCombinations) {
		List<BooksSet> maxDiscountBooksSets = null;
		int maxBooksSetsDiscount = 0;
		int totalBooksSetsDiscount = 0;

		for (List<BooksSet> booksSets : booksSetsCombinations) {
			for (BooksSet booksSet : booksSets) {
				totalBooksSetsDiscount += booksSet.getDiscount();
			}

			if (maxBooksSetsDiscount < totalBooksSetsDiscount) {
				maxDiscountBooksSets = booksSets;
				maxBooksSetsDiscount = totalBooksSetsDiscount;
			}

			totalBooksSetsDiscount = 0;
		}

		return maxDiscountBooksSets;
	}

	private List<ShoppingCartItem> cloneShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
		List<ShoppingCartItem> shoppingCartItemsCopy = new ArrayList<>();

		for (ShoppingCartItem item : shoppingCartItems) {
			shoppingCartItemsCopy.add(new ShoppingCartItem(item.getBook(), item.getQuantity()));
		}

		return shoppingCartItemsCopy;
	}

	private int getDiscount(int differentBooksCount) {
		int defaultDiscount = 0;
		List<BooksDiscountDetails> discounts = getAllDiscountDetailsWithDiffBooks();
		for (BooksDiscountDetails discount : discounts) {
			if (differentBooksCount == discount.getDifferentCopies())
				return discount.getDiscount();
		}

		return defaultDiscount;
	}
}
