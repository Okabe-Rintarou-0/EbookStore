package com.catstore.dao;

import com.catstore.entity.Cart;

import java.util.List;
import java.util.Map;

public interface CartDao {

    void deleteCartItem(Integer cartId);

    void addCartItem(Integer bookId);

    List<Cart> getAllCartItems();

    Boolean existsBook(Integer bookId);

    List<Cart> searchCartItems(String keyword);
}
