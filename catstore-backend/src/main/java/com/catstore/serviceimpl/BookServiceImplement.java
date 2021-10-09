package com.catstore.serviceimpl;

import com.catstore.dao.BookDao;
import com.catstore.dto.BookDto;
import com.catstore.entity.Book;
import com.catstore.service.BookService;
import com.catstore.constants.Constant;
import com.catstore.model.Message;
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
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public Message getBooks(int page) {
        PageRequest pageRequest = PageRequest.of(page, Constant.BOOK_PAGE_SIZE);
        Page<Book> bookPage = bookDao.getBooks(pageRequest);
        List<Book> books = bookPage.getContent();
        if (books.size() == 0)
            return MessageUtil.createMessage(MessageUtil.STAT_INVALID, MessageUtil.GENERAL_FAIL_MSG);
        JSONObject messageContent = new JSONObject();
        messageContent.put("total", bookPage.getTotalElements());
        messageContent.put("books", bookPage.getContent());
        return MessageUtil.createMessage(MessageUtil.STAT_OK, MessageUtil.GENERAL_SUCCESS_MSG, messageContent);
    }

    @Override
    public Book getBookById(Integer bookId) {
        Book book = redisUtil.get("book" + bookId, Book.class);
        if (book == null) {
            System.out.println("Fetch book " + bookId + " from database.");
            book = bookDao.getBookById(bookId);
            if (book != null)
                redisUtil.set("book" + bookId, book);
        } else
            System.out.println("Directly fetch book " + bookId + " from redis.");
        return book;
    }

    ///TODO: pagination
    @Override
    public List<Book> getBooksByKeyword(String keyword) {
        return bookDao.getBooksByKeyword(keyword);
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
        if (from != null && to != null) {
            return bookDto.getRankedBooks(from, to);
        }
        return bookDto.getAllRankedBooks();
    }

    @Override
    @Transactional
    public void postModifiedBook(Map<String, String> book) {
        bookDao.postModifiedBook(book);
    }
}
