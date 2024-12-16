package com.ifortex.bookservice.data.impl;

import com.ifortex.bookservice.data.BookRepository;
import com.ifortex.bookservice.dto.SearchCriteria;
import com.ifortex.bookservice.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Override
    public List<Book> getBooksByCriteria(SearchCriteria searchCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> bookRoot = criteriaQuery.from(Book.class);

        if (searchCriteria == null) {
            criteriaQuery.select(bookRoot);
            criteriaQuery.orderBy(criteriaBuilder.desc(bookRoot.get("publicationDate")));
            TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);
            return query.getResultList();
        }

        List<Predicate> predicates = new ArrayList<>();

        if (searchCriteria.getTitle() != null && !searchCriteria.getTitle().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(bookRoot.get("title")), "%" + searchCriteria.getTitle().toLowerCase() + "%"));
        }

        if (searchCriteria.getAuthor() != null && !searchCriteria.getAuthor().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(bookRoot.get("author")), "%" + searchCriteria.getAuthor().toLowerCase() + "%"));
        }

        if (searchCriteria.getGenre() != null && !searchCriteria.getGenre().isEmpty()) {
            predicates.add(criteriaBuilder.isMember(searchCriteria.getGenre().toLowerCase(), bookRoot.get("genres")));
        }

        if (searchCriteria.getDescription() != null && !searchCriteria.getDescription().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(bookRoot.get("description")), "%" + searchCriteria.getDescription().toLowerCase() + "%"));
        }

        if (searchCriteria.getYear() != null) {
            LocalDateTime startOfYear = LocalDateTime.of(searchCriteria.getYear(), 1, 1, 0, 0, 0, 0);
            LocalDateTime endOfYear = LocalDateTime.of(searchCriteria.getYear(), 12, 31, 23, 59, 59, 999999999);
            predicates.add(criteriaBuilder.between(bookRoot.get("publicationDate"), startOfYear, endOfYear));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        criteriaQuery.orderBy(criteriaBuilder.desc(bookRoot.get("publicationDate")));

        TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
