package com.catstore.serviceimpl;

import com.catstore.dao.BookDao;
import com.catstore.dto.BookDto;
import com.catstore.entity.Book;
import com.catstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImplement implements BookService {

    BookDao bookDao;
    BookDto bookDto;

    @Autowired
    void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Autowired
    void setBookDto(BookDto bookDto) {
        this.bookDto = bookDto;
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public Book getBookById(Integer bookId) {
        return bookDao.getBookById(bookId);
    }

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
    public Boolean undercarriage(ArrayList<Integer> bookIdList) {
        for (Integer bookId : bookIdList) {
            if (!bookDao.undercarriageBookByBookId(bookId))
                return false;
        }
        return true;
    }

    @Override
    public Boolean putOnSale(ArrayList<Integer> bookIdList) {
        for (Integer bookId : bookIdList) {
            if (!bookDao.putOnSale(bookId))
                return false;
        }
        return true;
    }

    @Override
    public ArrayList<Book> getRankedBooks(ArrayList<Date> startNEndDates) {
        if (startNEndDates != null && startNEndDates.size() == 2) {
            Date start = startNEndDates.get(0);
            Date end = startNEndDates.get(1);
            if (start != null && end != null)
                return bookDto.getRankedBooks(start, end);
        }
        return bookDto.getAllRankedBooks();
    }

    @Override
    public void postModifiedBook(Map<String, String> book) {
        bookDao.postModifiedBook(book);
    }
}
