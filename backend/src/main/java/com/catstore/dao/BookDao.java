package com.catstore.dao;

import com.catstore.entity.Book;
import com.catstore.entity.Book4Neo;
import com.catstore.entity.BookTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BookDao {
    void saveBookAndTags(Book4Neo book, List<BookTag> tags);

    List<Book> getBooks();

    Page<Book> getBooks(PageRequest pageRequest);

    String getAuthorByTitle(String title);

    Book getBookById(Integer bookId);

    List<Book> getBooksByKeyword(String keyword);

    String getBookTitleByBookId(Integer bookId);

    List<Map<String, String>> getConcernedBookInfo(String bookTitle, String websiteSrc);

    Boolean deleteBookByBookId(Integer bookId);

    Boolean undercarriageBookByBookId(Integer bookId);

    Boolean putOnSale(Integer bookId);

    void adjustStock(Integer bookId, Integer delta);

    ArrayList<Book> getAllRankedBooks();

    void saveBook(Book book);

    void postModifiedBook(Map<String, String> book);

    List<Book> searchByTags(List<String> tags);
}
