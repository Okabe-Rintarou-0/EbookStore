package com.catstore.dao;

import com.catstore.entity.Book;

import java.util.List;

public interface BookDao {
    List<Book> getBooks();

    Book getBookById(Integer bookId);
}
