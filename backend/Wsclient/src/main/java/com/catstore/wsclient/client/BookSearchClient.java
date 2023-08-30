package com.catstore.wsclient.client;

import com.catstore.wsclient.webService.Book;
import com.catstore.wsclient.webService.BookSearchRequest;
import com.catstore.wsclient.webService.BookSearchResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.util.List;

public class BookSearchClient extends WebServiceGatewaySupport {
    private static final String WS_PATH = "http://localhost:8080/ws/bookSearch";

    public List<Book> searchBooksBy(String keyword) {
        BookSearchRequest request = new BookSearchRequest();
        request.setKeyword(keyword);

        BookSearchResponse response = (BookSearchResponse) getWebServiceTemplate().
                marshalSendAndReceive(WS_PATH, request);
        return response.getBook();
    }
}
