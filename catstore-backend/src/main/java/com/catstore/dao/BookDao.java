package com.catstore.dao;

import com.catstore.crawlers.BookCrawler;
import com.catstore.entity.Book;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BookDao {
    List<Book> getBooks();

    Book getBookById(Integer bookId);

    List<Book> getBooksByKeyword(String keyword);

    String getBookTitleByBookId(Integer bookId);

    List<Map<String, String>> getConcernedBookInfo(String bookTitle, String websiteSrc);

    Boolean deleteBookByBookId(Integer bookId);

    Boolean undercarriageBookByBookId(Integer bookId);

    Boolean putOnSale(Integer bookId);

    void placeOrder(Integer bookId, Integer purchaseNumber);

    ArrayList<Book> getRankedBooks();

    void saveBook(Book book);
}
