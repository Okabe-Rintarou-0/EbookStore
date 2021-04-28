package com.catstore.serviceimpl;

import com.catstore.dao.BookDao;
import com.catstore.entity.Book;
import com.catstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImplement implements BookService {

    BookDao bookDao;

    @Autowired
    void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public Book getBookById(Integer bookId) {
        return bookDao.getBookById(bookId);
    }
}
