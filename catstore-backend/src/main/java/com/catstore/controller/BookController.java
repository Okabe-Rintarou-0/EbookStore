package com.catstore.controller;

import com.catstore.entity.Book;
import com.catstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    BookService bookService;

    @Autowired
    void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/getBooks")
    List<Book> getBooks() {
        return bookService.getBooks();
    }

    @RequestMapping("/getBookById")
    Book getBookById(@RequestParam("bookId") Integer bookId) {
        return bookService.getBookById(bookId);
    }
}
