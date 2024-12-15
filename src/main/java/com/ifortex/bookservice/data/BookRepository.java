package com.ifortex.bookservice.data;

import java.util.List;


public interface BookRepository {
    List<Object[]> countBooksByGenre();
}
