package com.catstore.controller;

import com.catstore.entity.Book;
import com.catstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @RequestMapping("/getBookTitle")
    String getBookTitleByBookId(@RequestParam("bookId") Integer bookId) {
        return bookService.getBookTitleByBookId(bookId);
    }

    @RequestMapping("/getBooksByKeyword")
    List<Book> getBooksByKeyword(@RequestParam("keyword") String keyword) {
        return bookService.getBooksByKeyword(keyword);
    }

    @RequestMapping("/getBookById")
    Book getBookById(@RequestParam("bookId") Integer bookId) {
        return bookService.getBookById(bookId);
    }

    @RequestMapping("/getConcernedBookInfo")
    List<Map<String, String>> getConcernedBookInfo(@RequestParam("bookTitle") String bookTitle) {
        return bookService.getConcernedBookInfo(bookTitle);
    }
}
