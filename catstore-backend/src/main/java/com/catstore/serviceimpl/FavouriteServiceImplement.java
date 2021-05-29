package com.catstore.serviceimpl;

import com.catstore.dao.CartDao;
import com.catstore.dao.FavouriteDao;
import com.catstore.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FavouriteServiceImplement implements FavouriteService {

    FavouriteDao favouriteDao;
    CartDao cartDao;

    @Autowired
    void setFavouriteDao(FavouriteDao favouriteDao) {
        this.favouriteDao = favouriteDao;
    }

    @Autowired
    void setCartDao(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public boolean addFavouriteBook(Integer bookId) {
        return favouriteDao.addFavouriteBook(bookId);
    }

    @Override
    public void deleteFavouriteBook(Integer bookId) {
        favouriteDao.deleteFavouriteBook(bookId);
    }

    @Override
    public void moveToCart(Integer bookId) {
        favouriteDao.deleteFavouriteBook(bookId);
        if (!cartDao.existsBook(bookId))
            cartDao.addCartItem(bookId);
    }

    @Override
    public List<Map<String, String>> getFavouriteBooks() {
        return favouriteDao.getFavouriteBooks();
    }
}
