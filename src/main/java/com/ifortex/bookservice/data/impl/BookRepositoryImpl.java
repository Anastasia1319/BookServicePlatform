package com.ifortex.bookservice.data.impl;

import com.ifortex.bookservice.data.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private static final String COUNT_BY_GENRE = "SELECT genre, COUNT(*) AS book_count " +
            "FROM (SELECT UNNEST(genre) AS genre " +
            "FROM books) AS unnest_genres GROUP BY genre " +
            "ORDER BY book_count";

    @Override
    public List<Object[]> countBooksByGenre() {
        return  entityManager.createNativeQuery(COUNT_BY_GENRE, Object[].class).getResultList();
    }
}
