package com.catstore.serviceimpl;

import com.catstore.constants.Constant;
import com.catstore.constants.RedisKeys;
import com.catstore.dao.BookDao;
import com.catstore.dto.BookDto;
import com.catstore.entity.Book;
import com.catstore.entity.Book4Neo;
import com.catstore.entity.BookTag;
import com.catstore.model.Message;
import com.catstore.searching.LuceneSearcher;
import com.catstore.service.BookService;
import com.catstore.utils.messageUtils.MessageUtil;
import com.catstore.utils.redisUtils.RedisUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImplement implements BookService {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookDto bookDto;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void saveBookAndTags(Book4Neo book, List<BookTag> tags) {
        bookDao.saveBookAndTags(book, tags);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public Message getBooks(int page) {
        // try fetch from redis:
        String pageRedisKey = RedisKeys.BookKey + ":page:" + page;
        String totalRedisKey = RedisKeys.BookKey + ":total:";
        List<Book> books = redisUtil.getWholeList(pageRedisKey, Book.class);
        Long total = redisUtil.get(totalRedisKey, Long.class);
        if (books.size() == 0 || total == null) {
            PageRequest pageRequest = PageRequest.of(page, Constant.BOOK_PAGE_SIZE);
            Page<Book> bookPage = bookDao.getBooks(pageRequest);
            books = bookPage.getContent();
            total = bookPage.getTotalElements();
            System.out.println("Fetch books of page = " + page + " from db.");
            // cache
            redisUtil.set(totalRedisKey, total);
            redisUtil.rpushObj(pageRedisKey, books);
            redisUtil.expire(totalRedisKey, 300);
            redisUtil.expire(pageRedisKey, 300);
        } else {
            System.out.println("Fetch books of page = " + page + " from redis.");
        }
        JSONObject messageContent = new JSONObject();
        messageContent.put("total", total);
        messageContent.put("books", books);
        return MessageUtil.createMessage(MessageUtil.STAT_OK, MessageUtil.GENERAL_SUCCESS_MSG, messageContent);
    }

    @Override
    public Book getBookById(Integer bookId) {
        String redisKey = RedisKeys.BookKey + ":" + bookId;
        Book book = redisUtil.get(redisKey, Book.class);
        if (book == null) {
            System.out.println("Fetch book " + bookId + " from database.");
            book = bookDao.getBookById(bookId);
            if (book != null) {
                redisUtil.set(redisKey, book);
                redisUtil.expire(redisKey, 60);
            }
        } else
            System.out.println("Directly fetch book " + bookId + " from redis.");
        return book;
    }

    @Override
    public List<Book> getBooksByKeyword(String keyword) {
        List<Integer> bookIdList = LuceneSearcher.searchBooksBy(keyword);
        List<Book> books = new ArrayList<>();
        System.out.println("Get book id list from lucene: " + bookIdList);
        for (Integer bookId : bookIdList) {
            books.add(bookDao.getBookById(bookId));
        }
        return books;
    }

    @Override
    public String getAuthorByTitle(String title) {
        return bookDao.getAuthorByTitle(title);
    }

    @Override
    public String getBookTitleByBookId(Integer bookId) {
        return bookDao.getBookTitleByBookId(bookId);
    }

    @Override
    public List<Map<String, String>> getConcernedBookInfo(String bookTitle, String websiteSrc) {
        return bookDao.getConcernedBookInfo(bookTitle, websiteSrc);
    }

    @Override
    public Boolean deleteBooks(ArrayList<Integer> bookIdList) {
        for (Integer bookId : bookIdList) {
            if (!bookDao.deleteBookByBookId(bookId))
                return false;
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean undercarriage(ArrayList<Integer> bookIdList) {
        for (Integer bookId : bookIdList) {
            if (!bookDao.undercarriageBookByBookId(bookId)) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean putOnSale(ArrayList<Integer> bookIdList) {
        for (Integer bookId : bookIdList) {
            if (!bookDao.putOnSale(bookId))
                return false;
        }
        return true;
    }

    @Override
    public ArrayList<Book> getRankedBooks(Date from, Date to) {
        return bookDto.getRankedBooks(from, to);
    }

    @Override
    public ArrayList<Book> getAllRankedBooks() {
        return bookDto.getAllRankedBooks();
    }

    @Override
    @Transactional
    public void postModifiedBook(Map<String, String> book) {
        bookDao.postModifiedBook(book);
    }

    @Override
    public List<Book> searchByTags(List<String> tags) {
        return bookDao.searchByTags(tags);
    }
}
