package com.catstore.serviceimpl;

import com.catstore.dao.FavouriteDao;
import com.catstore.dao.UserOrderDao;
import com.catstore.entity.Favourite;
import com.catstore.entity.UserOrder;
import com.catstore.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FavouriteServiceImplement implements FavouriteService {

    FavouriteDao favouriteDao;
    UserOrderDao userOrderDao;

    @Autowired
    void setFavouriteDao(FavouriteDao favouriteDao) {
        this.favouriteDao = favouriteDao;
    }

    @Autowired
    void setUserOrderDao(UserOrderDao userOrderDao) {
        this.userOrderDao = userOrderDao;
    }

    @Override
    public void addFavouriteBook(Integer bookId) {
        favouriteDao.addFavouriteBook(bookId);
    }

    @Override
    public void deleteFavouriteBook(Integer bookId) {
        favouriteDao.deleteFavouriteBook(bookId);
    }

    @Override
    public void moveToCart(Integer bookId) {
        favouriteDao.deleteFavouriteBook(bookId);
        userOrderDao.addUserOrder("未购买", 1, bookId);
    }

    @Override
    public List<Map<String, String>> getFavouriteBooks() {
        return favouriteDao.getFavouriteBooks();
    }
}
