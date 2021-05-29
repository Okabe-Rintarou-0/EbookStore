package com.catstore.service;

import com.catstore.crawlers.BookCrawler;
import com.catstore.entity.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    List<Book> getBooks();

    Book getBookById(Integer bookId);

    List<Book> getBooksByKeyword(String keyword);

    String getBookTitleByBookId(Integer bookId);

    List<Map<String, String>> getConcernedBookInfo(String bookTitle, String websiteSrc);
}
