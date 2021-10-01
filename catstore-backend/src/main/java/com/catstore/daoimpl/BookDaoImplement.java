package com.catstore.daoimpl;

import com.catstore.crawlers.BookCrawler;
import com.catstore.dao.BookDao;
import com.catstore.entity.Book;
import com.catstore.repository.BookRepository;
import com.catstore.utils.redisUtils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImplement implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCrawler bookCrawler;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> getBooks(PageRequest pageRequest) {
        return bookRepository.findAll(pageRequest);
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

    //下架书籍，采用先更新数据库再删除redis缓存的方法。
    @Override
    @Transactional
    public Boolean undercarriageBookByBookId(Integer bookId) {
        if (bookRepository.undercarriageBookByBookId(bookId) > 0) {
            redisUtil.del("book" + bookId);
            return true;
        }
        return false;
    }

    //下架书籍，采用先删除redis缓存再更新数据库的方法。
    //使用“延时双删”
    @Override
    @Transactional
    public Boolean putOnSale(Integer bookId) {
        // delete cache first
        redisUtil.del("book" + bookId);
        // then modify database
        if (bookRepository.putOnSale(bookId) > 0) {
            redisUtil.del("book" + bookId, 1000L);
            return true;
        }
        return false;
    }

    //修改书籍存货
    //先修改数据库，再删除cache
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void adjustStock(Integer bookId, Integer delta) {
        bookRepository.adjustStock(bookId, delta);
        redisUtil.del("book" + bookId);
    }

    @Override
    public ArrayList<Book> getAllRankedBooks() {
        return bookRepository.getAllRankedBooks();
    }

    @Override
    public void saveBook(Book book) {
        if (book != null) {
            bookRepository.save(book);
            redisUtil.del("book" + book.getBookId());
        }
    }

    //修改书籍信息
    //先修改数据库，再删除cache
    @Override
    @Transactional
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
            redisUtil.del("book" + bookId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
