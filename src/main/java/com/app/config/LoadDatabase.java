package com.app.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.model.Book;
import com.app.model.BooksDiscountDetails;
import com.app.repo.BookRepository;
import com.app.repo.BooksDiscountRepository;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(BookRepository bookRepository, BooksDiscountRepository booksDiscountRepository) {

		return args -> {
			List<Book> listBooks = new ArrayList<Book>();
			int price = 50;
			int bookList[] = { 1, 2, 3, 4, 5 };
			String title[] = { "Clean Code", "Clean Coder", "Clean Architecture", "Test Driven Development by Example",
					"Working effectively with Legacy Code" };
			for (int i = 0; i < bookList.length; i++) {
				Book book = new Book();
				book.setId(bookList[i]);
				book.setTitle(title[i]);
				book.setPrice(price);
				listBooks.add(book);
			}
			bookRepository.saveAll(listBooks);
			List<BooksDiscountDetails> byDifferentCopiesDiscountList = new ArrayList<>();

			int differentCopies[] = { 2, 3, 4, 5 };
			int discountList[] = { 5, 10, 20, 25 };

			for (int i = 0; i < discountList.length; i++) {
				BooksDiscountDetails booksDiscountDetails = new BooksDiscountDetails();
				booksDiscountDetails.setId(i + 1);
				booksDiscountDetails.setDifferentCopies(differentCopies[i]);
				booksDiscountDetails.setDiscount(discountList[i]);
				byDifferentCopiesDiscountList.add(booksDiscountDetails);

			}

			booksDiscountRepository.saveAll(byDifferentCopiesDiscountList);

			booksDiscountRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));

		};
	}
}