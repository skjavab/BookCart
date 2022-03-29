package com.app.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

}
