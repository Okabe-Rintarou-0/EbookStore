package com.catstore.repository;

import com.catstore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query(value = "select * from (user_cart natural join cart_record natural join book) where user_id = ?1", nativeQuery = true)
    List<Map<String, String>> getAllCartItems(Integer userId);

    @Query(value = "from Cart where cartId = ?1")
    Cart getUserCartByCartId(Integer cartId);

    @Modifying
    @Query(value = "delete from Cart where cartId=?1")
    void deleteUserCartByCartId(Integer cartId);

    @Query(value = "from Cart where bookId = ?1")
    Cart getCartByBookId(Integer bookId);
}
