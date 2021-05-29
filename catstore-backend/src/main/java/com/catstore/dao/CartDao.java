package com.catstore.dao;

import com.catstore.entity.Cart;
import com.catstore.entity.CartRecord;

import java.util.List;
import java.util.Map;

public interface CartDao {

    void deleteCartItem(Integer cartId);

    void addCartItem(Integer bookId);

    List<Map<String, String>> getAllCartItems();

    Boolean existsBook(Integer bookId);

    Cart getUserCartByCartId(Integer cartId);

    CartRecord getCartRecordByCartId(Integer orderId);
}
