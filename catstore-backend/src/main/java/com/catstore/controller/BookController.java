package com.catstore.controller;

import com.catstore.annotation.SkipSessionCheck;
import com.catstore.constants.Constant;
import com.catstore.entity.Book;
import com.catstore.entity.BookTag;
import com.catstore.feign.BookServiceClient;
import com.catstore.model.Message;
import com.catstore.service.BookService;
import com.catstore.utils.messageUtils.MessageUtil;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookServiceClient bookServiceClient;

    @GetMapping("/getAll")
    List<Book> getAllBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/author/{title}")
    @SkipSessionCheck
    String getAuthorByTitle(@PathVariable(name = "title") String title) {
        //        return bookService.getAuthorByTitle(title);
        return bookServiceClient.getAuthorByTitle(title);
    }

    @GetMapping
    Message getBooks(@RequestParam int page) {
        return bookService.getBooks(page);
    }

    @GetMapping("/search")
    List<Book> getBooksByKeyword(@RequestParam("keyword") String keyword) {
        return bookService.getBooksByKeyword(keyword);
    }

    @GetMapping("/get")
    Book getBookById(@RequestParam("bookId") Integer bookId) {
        return bookService.getBookById(bookId);
    }

    @GetMapping("/concerned")
    List<Map<String, String>> getConcernedBookInfo(@RequestParam("bookTitle") String bookTitle, @RequestParam("website") String websiteSrc) {
        return bookService.getConcernedBookInfo(bookTitle, websiteSrc);
    }

    @PostMapping("/delete")
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

    @PostMapping("/undercarriage")
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

    @PostMapping("/putOnSale")
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

    @GetMapping("/rank")
    ArrayList<Book> getRankedBooks(@RequestParam Date from, @RequestParam Date to) {
        return bookService.getRankedBooks(from, to);
    }

    @GetMapping("/rank/all")
    ArrayList<Book> getAllRankedBooks() {
        return bookService.getAllRankedBooks();
    }

    @PostMapping("/modify")
    void postModifiedBook(@RequestBody Map<String, String> book) {
        System.out.println(book.toString());
        bookService.postModifiedBook(book);
    }

    @SkipSessionCheck
    @GetMapping("/tag/{tag}")
    List<Book> findBooksByTag(@PathVariable(name = "tag") List<String> tags) {
        System.out.println("read tags: " + tags);
        return bookService.searchByTags(tags);
    }
}
