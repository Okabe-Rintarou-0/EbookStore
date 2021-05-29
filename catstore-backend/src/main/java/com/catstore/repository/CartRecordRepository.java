package com.catstore.repository;

import com.catstore.entity.CartRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface CartRecordRepository extends JpaRepository<CartRecord, Integer> {

    @Modifying
    @Query(value = "delete from CartRecord where cartId =?1")
    void deleteCartItemByCartId(Integer cartId);

    @Query(value = "from CartRecord where cartId = ?1")
    CartRecord getCartRecordByCartId(Integer orderId);

    @Query(value = "select max(user_cart.cart_id) from user_cart", nativeQuery = true)
    Integer getMaxCartId();
}
