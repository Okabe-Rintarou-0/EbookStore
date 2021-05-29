package com.catstore.service;

import java.util.List;
import java.util.Map;

public interface CartService {
    Boolean addToCart(Integer bookId);

    void deleteCartItem(Integer cartId);

    List<Map<String, String>> getAllCartItems();
}
