package com.ifortex.bookservice.controller;

import com.ifortex.bookservice.dto.SearchCriteria;
import com.ifortex.bookservice.model.Book;
import com.ifortex.bookservice.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/books")
@AllArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping("statistic")
  public Map<String, Long> getStatistic() {
    return bookService.getBooks();
  }

  @GetMapping("search")
  public List<Book> findBooks(@RequestParam(required = false) String title,
                              @RequestParam(required = false) String author,
                              @RequestParam(required = false) String genre,
                              @RequestParam(required = false) String description,
                              @RequestParam(required = false) Integer year) {
    SearchCriteria searchCriteria = SearchCriteria.builder()
            .title(title)
            .author(author)
            .genre(genre)
            .description(description)
            .year(year)
            .build();

    return bookService.getAllByCriteria(searchCriteria);
  }
}
