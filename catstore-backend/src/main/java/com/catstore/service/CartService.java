package com.catstore.service;

import com.catstore.entity.Cart;

import java.util.List;
import java.util.Map;

public interface CartService {
    Boolean addToCart(Integer bookId);

    void deleteCartItem(Integer cartId);

    List<Cart> getAllCartItems();

    List<Cart> searchCartItems(String keyword);
}
