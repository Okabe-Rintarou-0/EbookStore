package com.catstore.bookservice.serviceimpl;


import com.catstore.bookservice.dao.BookDao;
import com.catstore.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImplement implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public String getAuthorByTitle(String title) {
        return bookDao.getAuthorByTitle(title);
    }
}
