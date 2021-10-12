package com.catstore.wsclient.controller;

import com.catstore.wsclient.client.BookSearchClient;
import com.catstore.wsclient.webService.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class BookSearchController {
    @Autowired
    BookSearchClient client;

    @GetMapping
    public List<Book> searchBooksBy(@RequestParam(name = "keyword") String keyword) {
        System.out.println("Read keyword: " + keyword);
        return client.searchBooksBy(keyword);
    }
}
