package com.catstore.endpoint;

import com.catstore.entity.Book;
import com.catstore.searching.LuceneSearcher;
import com.catstore.service.BookService;
import com.catstore.webService.BookSearchRequest;
import com.catstore.webService.BookSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class BookSearchWsEndpoint {
    private static final String NAMESPACE = "bookSearch";

    @Autowired
    private BookService bookService;

    @PayloadRoot(namespace = NAMESPACE, localPart = "bookSearchRequest")
    @ResponsePayload
    public BookSearchResponse searchBooksBy(@RequestPayload BookSearchRequest request) {
        BookSearchResponse response = new BookSearchResponse();
        List<Integer> bookIdList = LuceneSearcher.searchBooksBy(request.getKeyword());
        for (Integer bookId : bookIdList) {
            Book book = bookService.getBookById(bookId);
            com.catstore.webService.Book wsBook = new com.catstore.webService.Book();
            wsBook.setId(bookId);
            wsBook.setDetails(book.getBookDetails());
            wsBook.setTitle(book.getBookTitle());
            wsBook.setStock(book.getBookStock());
            wsBook.setPrice(Float.parseFloat(book.getBookPrice().toString()));
            wsBook.setDescription(book.getBookDescription());
            response.getBook().add(wsBook);
        }
        return response;
    }
}
