package com.catstore.service;

import com.catstore.crawlers.BookCrawler;
import com.catstore.entity.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookService {
    List<Book> getBooks();

    Book getBookById(Integer bookId);

    List<Book> getBooksByKeyword(String keyword);

    String getBookTitleByBookId(Integer bookId);

    List<Map<String, String>> getConcernedBookInfo(String bookTitle, String websiteSrc);

    Boolean deleteBooks(ArrayList<Integer> bookIdList);

    Boolean undercarriage(ArrayList<Integer> bookIdList);

    Boolean putOnSale(ArrayList<Integer> bookIdList);

    ArrayList<Book> getRankedBooks(ArrayList<Date> startNEndDates);

    void postModifiedBook(Map<String, String> book);
}
