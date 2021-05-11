package com.catstore.daoimpl;

import com.catstore.crawlers.BookCrawler;
import com.catstore.dao.BookDao;
import com.catstore.entity.Book;
import com.catstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImplement implements BookDao {

    BookRepository bookRepository;

    BookCrawler bookCrawler;

    @Autowired
    void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    void setBookCrawler(BookCrawler bookCrawler) {
        this.bookCrawler = bookCrawler;
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.getBooks();
    }

    @Override
    public Book getBookById(Integer bookId) {
        return bookRepository.getBookById(bookId);
    }

    @Override
    public List<Book> getBooksByKeyword(String keyword) {
        return bookRepository.getBooksByKeyword(keyword);
    }

    @Override
    public String getBookTitleByBookId(Integer bookId) {
        return bookRepository.getBookById(bookId).getBookTitle();
    }

    @Override
    public List<Map<String, String>> getConcernedBookInfo(String bookTitle) {
        return bookCrawler.crawlBooks(bookTitle);
    }
}
