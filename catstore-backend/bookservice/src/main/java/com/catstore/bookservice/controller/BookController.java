package com.catstore.bookservice.controller;

import com.catstore.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/author/{title}")
    String getAuthorByTitle(@PathVariable(name = "title") String title) {
        System.out.println("Someone invoke me to get the author by its title: " + title);
        return bookService.getAuthorByTitle(title);
    }
}
