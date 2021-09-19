package com.catstore.dtoimpl;

import com.catstore.dao.BookDao;
import com.catstore.dao.UserOrderDao;
import com.catstore.dto.BookDto;
import com.catstore.entity.Book;
import com.catstore.entity.OrderItem;
import com.catstore.entity.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BookDtoImplement implements BookDto {

    BookDao bookDao;
    UserOrderDao userOrderDao;

    @Autowired
    void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Autowired
    void setUserOrderDao(UserOrderDao userOrderDao) {
        this.userOrderDao = userOrderDao;
    }

    @Override
    public ArrayList<Book> getAllRankedBooks() {
        return bookDao.getAllRankedBooks();
    }

    @Override
    public ArrayList<Book> getRankedBooks(Date from, Date to) {
        ArrayList<Book> rankedBooks = new ArrayList<>();
        ArrayList<UserOrder> userOrders = userOrderDao.getOrdersInRangeForManager(from, to);
        Map<Integer, Integer> bookSales = new HashMap<>();
        for (UserOrder userOrder : userOrders) {
            Set<OrderItem> orderItems = userOrder.getOrders();
            for (OrderItem orderItem : orderItems) {
                Integer bookId = orderItem.getBookId();
                Integer thisBookSales = bookSales.get(bookId);
                Integer purchaseNumber = orderItem.getPurchaseNumber();
                if (thisBookSales != null) {
                    bookSales.put(bookId, thisBookSales + purchaseNumber);
                } else {
                    bookSales.put(bookId, purchaseNumber);
                    rankedBooks.add(orderItem.getBook());
                }
            }
        }
        for (Book book : rankedBooks) {
            book.setSales(bookSales.get(book.getBookId()));
        }
        rankedBooks.sort(new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o2.getSales() - o1.getSales();
            }
        });
        return rankedBooks;
    }
}
