package com.catstore.daoimpl;

import com.catstore.dao.CartDao;
import com.catstore.entity.Cart;
import com.catstore.repository.CartRepository;
import com.catstore.repository.UserRepository;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CartDaoImplement implements CartDao {

    CartRepository cartRepository;
    UserRepository userRepository;

    @Autowired
    void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Autowired
    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void deleteCartItem(Integer cartId) {
        cartRepository.deleteUserCartByCartId(cartId);
    }

    @Override
    public void addCartItem(Integer bookId) {
        Integer userId = SessionUtil.getUserId();
        if (userId != null) {
            Cart cart = new Cart();
            cart.setBookId(bookId);
            cart.setPurchaseNumber(1);
            cart.setUserId(userId);
            cartRepository.save(cart);
        }
    }

    @Override
    public List<Cart> getAllCartItems() {
        Integer userId = SessionUtil.getUserId();
        if (userId != null)
            return cartRepository.getAllCartItems(userId);
        else
            return null;
    }

    @Override
    public Boolean existsBook(Integer bookId) {
        return cartRepository.getCartByBookIdAndUserId(bookId, SessionUtil.getUserId()) != null;
    }

    @Override
    public List<Cart> searchCartItems(String keyword) {
        return cartRepository.searchCartItems(SessionUtil.getUserId(), keyword);
    }
}
