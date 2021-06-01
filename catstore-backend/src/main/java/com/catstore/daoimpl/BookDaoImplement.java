package com.catstore.daoimpl;

import com.catstore.crawlers.BookCrawler;
import com.catstore.dao.BookDao;
import com.catstore.entity.Book;
import com.catstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public List<Map<String, String>> getConcernedBookInfo(String bookTitle, String websiteSrc) {
        return bookCrawler.crawlBooks(bookTitle, websiteSrc);
    }

    @Override
    public Boolean deleteBookByBookId(Integer bookId) {
        return bookRepository.deleteBookByBookId(bookId) > 0;
    }

    @Override
    public Boolean undercarriageBookByBookId(Integer bookId) {
        return bookRepository.undercarriageBookByBookId(bookId) > 0;
    }

    @Override
    public Boolean putOnSale(Integer bookId) {
        return bookRepository.putOnSale(bookId) > 0;
    }

    @Override
    public void placeOrder(Integer bookId, Integer purchaseNumber) {
        bookRepository.placeOrder(bookId, purchaseNumber);
    }

    @Override
    public ArrayList<Book> getRankedBooks() {
        return bookRepository.getRankedBooks();
    }

    @Override
    public void saveBook(Book book) {
        if (book != null)
            bookRepository.save(book);
    }
}
