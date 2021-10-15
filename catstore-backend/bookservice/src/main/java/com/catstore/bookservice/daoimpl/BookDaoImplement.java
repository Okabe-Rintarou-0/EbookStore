package com.catstore.bookservice.daoimpl;

import com.catstore.bookservice.dao.BookDao;
import com.catstore.bookservice.entity.Book;
import com.catstore.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImplement implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public String getAuthorByTitle(String title) {
        Book book = bookRepository.findBookByBookTitleLike(title);
        System.out.println("Found book: " + (book == null ? "null" : book.toString()));
        return book != null ? book.getBookAuthor() : null;
    }
}
