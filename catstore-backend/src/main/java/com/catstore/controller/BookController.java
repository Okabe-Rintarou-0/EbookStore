package com.catstore.controller;

import com.catstore.entity.Book;
import com.catstore.service.BookService;
import com.catstore.utils.Constant;
import com.catstore.utils.messageUtils.Message;
import com.catstore.utils.messageUtils.MessageUtil;
import com.catstore.utils.sessionUtils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class BookController {

    BookService bookService;

    @Autowired
    void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/getBooks")
    List<Book> getBooks() {
        return bookService.getBooks();
    }

    @RequestMapping("/getBookTitle")
    String getBookTitleByBookId(@RequestParam("bookId") Integer bookId) {
        return bookService.getBookTitleByBookId(bookId);
    }

    @RequestMapping("/getBooksByKeyword")
    List<Book> getBooksByKeyword(@RequestParam("keyword") String keyword) {
        return bookService.getBooksByKeyword(keyword);
    }

    @RequestMapping("/getBookById")
    Book getBookById(@RequestParam("bookId") Integer bookId) {
        return bookService.getBookById(bookId);
    }

    @RequestMapping("/getConcernedBookInfo")
    List<Map<String, String>> getConcernedBookInfo(@RequestParam("bookTitle") String bookTitle, @RequestParam("website") String websiteSrc) {
        return bookService.getConcernedBookInfo(bookTitle, websiteSrc);
    }

    @RequestMapping("/manager/deleteBooks")
    Message deleteBooks(@RequestBody ArrayList<Integer> bookIdList) {
        Integer userIdentity = SessionUtil.getUserIdentity();
        if (userIdentity != null && userIdentity.equals(Constant.MANAGER)) {
            if (bookService.deleteBooks(bookIdList))
                return MessageUtil.createMessage(MessageUtil.DELETE_SUCCESS_CODE, MessageUtil.DELETE_SUCCESS_MSG);
            else
                return MessageUtil.createMessage(MessageUtil.DELETE_FAIL_CODE, MessageUtil.DELETE_FAIL_MSG);
        }
        return MessageUtil.createMessage(MessageUtil.HAVE_NO_AUTHORITY_CODE, MessageUtil.HAVE_NO_AUTHORITY_MSG);
    }

    @RequestMapping("/manager/undercarriage")
    Message undercarriage(@RequestBody ArrayList<Integer> bookIdList) {
        Integer userIdentity = SessionUtil.getUserIdentity();
        if (userIdentity != null && userIdentity.equals(Constant.MANAGER)) {
            System.out.println(bookIdList.toString());
            if (bookService.undercarriage(bookIdList))
                return MessageUtil.createMessage(MessageUtil.UNDERCARRIAGE_SUCCESS_CODE, MessageUtil.UNDERCARRIAGE_SUCCESS_MSG);
            else
                return MessageUtil.createMessage(MessageUtil.UNDERCARRIAGE_FAIL_CODE, MessageUtil.UNDERCARRIAGE_FAIL_MSG);
        }
        return MessageUtil.createMessage(MessageUtil.HAVE_NO_AUTHORITY_CODE, MessageUtil.HAVE_NO_AUTHORITY_MSG);
    }

    @RequestMapping("/manager/putOnSale")
    Message putOnSale(@RequestBody ArrayList<Integer> bookIdList) {
        Integer userIdentity = SessionUtil.getUserIdentity();
        if (userIdentity != null && userIdentity.equals(Constant.MANAGER)) {
            System.out.println(bookIdList.toString());
            if (bookService.putOnSale(bookIdList))
                return MessageUtil.createMessage(MessageUtil.PUT_ON_SALE_SUCCESS_CODE, MessageUtil.PUT_ON_SALE_SUCCESS_MSG);
            else
                return MessageUtil.createMessage(MessageUtil.PUT_ON_SALE_FAIL_CODE, MessageUtil.PUT_ON_SALE_FAIL_MSG);
        }
        return MessageUtil.createMessage(MessageUtil.HAVE_NO_AUTHORITY_CODE, MessageUtil.HAVE_NO_AUTHORITY_MSG);
    }

    @RequestMapping("/getRankedBooks")
    ArrayList<Book> getRankedBooks() {
        return bookService.getRankedBooks();
    }
}
