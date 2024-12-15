package com.ifortex.bookservice.service.impl;

import com.ifortex.bookservice.data.BookRepository;
import com.ifortex.bookservice.dto.SearchCriteria;
import com.ifortex.bookservice.model.Book;
import com.ifortex.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    @Override
    public Map<String, Long> getBooks() {
        List<Object[]> rawData = bookRepository.countBooksByGenre();

        return rawData.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> ((Number) row[1]).longValue()
                ));
    }

    @Override
    public List<Book> getAllByCriteria(SearchCriteria searchCriteria) {
        return null;
    }
}
