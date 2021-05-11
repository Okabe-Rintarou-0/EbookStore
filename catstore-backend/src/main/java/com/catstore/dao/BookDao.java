package com.catstore.dao;

import com.catstore.entity.Book;

import java.util.List;
import java.util.Map;

public interface BookDao {
    List<Book> getBooks();

    Book getBookById(Integer bookId);

    List<Book> getBooksByKeyword(String keyword);

    String getBookTitleByBookId(Integer bookId);

    List<Map<String, String>> getConcernedBookInfo(String bookTitle);
}
