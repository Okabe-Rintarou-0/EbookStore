package com.catstore.daoimpl;

import com.catstore.crawlers.BookCrawler;
import com.catstore.dao.BookDao;
import com.catstore.entity.Book;
import com.catstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    @Override
    public void postModifiedBook(Map<String, String> book) {
        try {
            Integer bookId = Integer.parseInt(book.get("bookId"));
            String bookCover = book.get("bookCover");
            String bookTitle = book.get("bookTitle");
            String bookAuthor = book.get("bookAuthor");
            String bookDescription = book.get("bookDescription");
            String bookDetails = book.get("bookDetails");
            Integer bookStock = Integer.parseInt(book.get("bookStock"));
            String bookTag = book.get("bookTag");
            String bookType = book.get("bookType");
            BigDecimal bookPrice = new BigDecimal(book.get("bookPrice"));
            bookRepository.modifyBookWithBookId(bookId, bookCover, bookTitle, bookAuthor, bookDescription, bookDetails,
                    bookStock, bookPrice, bookTag, bookType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
