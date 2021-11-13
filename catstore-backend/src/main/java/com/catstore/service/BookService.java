package com.catstore.service;

import com.catstore.entity.Book;
import com.catstore.entity.Book4Neo;
import com.catstore.entity.BookTag;
import com.catstore.model.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookService {
    void saveBookAndTags(Book4Neo book, List<BookTag> tags);

    List<Book> getBooks();

    Message getBooks(int page);

    Book getBookById(Integer bookId);

    List<Book> getBooksByKeyword(String keyword);

    String getAuthorByTitle(String title);

    String getBookTitleByBookId(Integer bookId);

    List<Map<String, String>> getConcernedBookInfo(String bookTitle, String websiteSrc);

    Boolean deleteBooks(ArrayList<Integer> bookIdList);

    Boolean undercarriage(ArrayList<Integer> bookIdList);

    Boolean putOnSale(ArrayList<Integer> bookIdList);

    ArrayList<Book> getRankedBooks(Date from, Date to);

    ArrayList<Book> getAllRankedBooks();

    void postModifiedBook(Map<String, String> book);

    List<Book> searchByTags(List<String> tags);
}
