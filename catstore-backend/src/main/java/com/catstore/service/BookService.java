package com.catstore.service;

import com.catstore.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooks();

    Book getBookById(Integer bookId);
}
