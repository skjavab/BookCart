package com.app.repo;

import org.springframework.data.repository.CrudRepository;

import com.app.model.BooksDiscountDetails;

public interface BooksDiscountRepository extends CrudRepository<BooksDiscountDetails, Integer>{

}
