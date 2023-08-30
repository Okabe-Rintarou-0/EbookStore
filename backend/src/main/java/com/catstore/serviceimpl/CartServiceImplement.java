package com.catstore.serviceimpl;

import com.catstore.dao.CartDao;
import com.catstore.entity.Cart;
import com.catstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartServiceImplement implements CartService {

    CartDao cartDao;

    @Autowired
    void setCartDao(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public Boolean addToCart(Integer bookId) {
        if (!cartDao.existsBook(bookId)) {
            cartDao.addCartItem(bookId);
            return true;
        } else return false;
    }

    @Override
    public void deleteCartItem(Integer cartId) {
        cartDao.deleteCartItem(cartId);
    }

    @Override
    public List<Cart> getAllCartItems() {
        return cartDao.getAllCartItems();
    }

    @Override
    public List<Cart> searchCartItems(String keyword) {
        return cartDao.searchCartItems(keyword);
    }
}
