package com.catstore.controller;

import com.catstore.entity.Book;
import com.catstore.searching.LuceneSearcher;
import com.catstore.service.BookService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@WebService
@Component
public class BookSearchWsController {
    @Autowired
    private BookService bookService;

    @WebMethod
    public List<Book> searchBooksBy(@WebParam(name = "parameters") String keyword) {
        List<Integer> bookIdList = LuceneSearcher.searchBooksBy(keyword);
        List<Book> books = new ArrayList<>();
        for (Integer bookId : bookIdList) {
            books.add(bookService.getBookById(bookId));
        }
        return books;
    }
}
