package com.catstore.daoimpl;

import com.catstore.dao.CartDao;
import com.catstore.entity.Cart;
import com.catstore.entity.CartRecord;
import com.catstore.repository.CartRecordRepository;
import com.catstore.repository.CartRepository;
import com.catstore.repository.UserRepository;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CartDaoImplement implements CartDao {

    CartRepository cartRepository;
    CartRecordRepository cartRecordRepository;
    UserRepository userRepository;

    @Autowired
    void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Autowired
    void setCartRecordRepository(CartRecordRepository cartRecordRepository) {
        this.cartRecordRepository = cartRecordRepository;
    }

    @Autowired
    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void deleteCartItem(Integer cartId) {
        cartRecordRepository.deleteCartItemByCartId(cartId);
        cartRepository.deleteUserCartByCartId(cartId);
    }

    @Override
    public void addCartItem(Integer bookId) {
        Integer userId = SessionUtil.getUserId();
        if (userId != null) {
            Cart cart = new Cart();
            cart.setBookId(bookId);
            cart.setPurchaseNumber(1);
            cart = cartRepository.save(cart);
            CartRecord cartRecord = new CartRecord();
            cartRecord.setCartId(cart.getCartId());
            cartRecord.setUserId(userId);
            cartRecordRepository.save(cartRecord);
        }
    }

    @Override
    public List<Map<String, String>> getAllCartItems() {
        Integer userId = SessionUtil.getUserId();
        if (userId != null)
            return cartRepository.getAllCartItems(userId);
        else
            return null;
    }

    @Override
    public Boolean existsBook(Integer bookId) {
        return cartRepository.getCartByBookId(bookId) != null;
    }

    @Override
    public Cart getUserCartByCartId(Integer cartId) {
        return cartRepository.getUserCartByCartId(cartId);
    }

    @Override
    public CartRecord getCartRecordByCartId(Integer orderId) {
        return cartRecordRepository.getCartRecordByCartId(orderId);
    }
}
