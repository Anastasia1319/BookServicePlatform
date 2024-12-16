package com.ifortex.bookservice.data;

import com.ifortex.bookservice.dto.SearchCriteria;
import com.ifortex.bookservice.model.Book;

import java.util.List;


public interface BookRepository {
    List<Object[]> countBooksByGenre();
    List<Book> getBooksByCriteria(SearchCriteria searchCriteria);
}
