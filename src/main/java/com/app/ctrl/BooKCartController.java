package com.app.ctrl;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.BillingDetails;
import com.app.model.Book;
import com.app.model.BooksDiscountDetails;
import com.app.model.ShoppingCartItem;
import com.app.serv.BookCartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController // Useful to create the RESTful webservices.
@Api("Book Cart")
public class BooKCartController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// @Autowired annotation provides the automatic dependency injection.
	@Autowired
	BookCartService service;

	// Save book entity in the h2 database.
	// @PostMapping annotation handles the http post request matched with the given
	// uri.
	// @RequestBody annotation binds the http request body to the domain object.
	// @Valid annotation validates a model after binding the user input to it.
	@ApiOperation("Save Book Detalis")
	@PostMapping(value = "/book/save")
	public int saveBook(final @RequestBody @Valid Book book) {
		log.info("Saving book details in the database.");
		service.saveBook(book);
		return book.getId();
	}

	// Get all books from the h2 database.
	// @GetMapping annotation handles the http get request matched with the given
	// uri.
	@ApiOperation("Retrive Books")
	@GetMapping(value = "/book/getall", produces = "application/vnd.jcg.api.v1+json")
	public List<Book> getAllBooks() {
		log.info("Getting book details from the database.");
		return service.getAllBooks();
	}

	// Save book entity in the h2 database.
	// @PostMapping annotation handles the http post request matched with the given
	// uri.
	// @RequestBody annotation binds the http request body to the domain object.
	// @Valid annotation validates a model after binding the user input to it.
	@ApiOperation("Save Book Detalis")
	@PostMapping(value = "/discovents/save")
	public String saveDiscounts(final @RequestBody @Valid BooksDiscountDetails discountItemInfo) {
		log.info("Saving book details in the database.");
		service.saveBooksDiscountDetails(discountItemInfo);
		return "Saving DiscountDetails details in the database.";
	}

	// Get all books from the h2 database.
	// @GetMapping annotation handles the http get request matched with the given
	// uri.
	@ApiOperation("Retrive Books")
	@GetMapping(value = "/book/getallDiscountWithDiffBooks", produces = "application/vnd.jcg.api.v1+json")
	public List<BooksDiscountDetails> getAllDiscountDetailsWithDiffBooks() {
		log.info("Getting book details from the database.");
		return service.getAllDiscountDetailsWithDiffBooks();
	}

	// Save book Cart entity in the h2 database.
	// @PostMapping annotation handles the http post request matched with the given
	// uri.
	// @RequestBody annotation binds the http request body to the domain object.
	// @Valid annotation validates a model after binding the user input to it.
	@ApiOperation("Book Cart Detalis")
	@PostMapping(value = "/book/calculatePrice")
	public BillingDetails calculatePrice(final @RequestBody @Valid List<ShoppingCartItem> shoppingCartItem) {
		log.info("Saving book details in the database.");

		return service.calculatePrice(shoppingCartItem);
	}

}
