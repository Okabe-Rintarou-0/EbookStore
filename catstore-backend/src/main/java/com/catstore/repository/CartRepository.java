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

    @Query(value = "from Cart where userId = ?1")
    List<Cart> getAllCartItems(Integer userId);

    @Query(value = "from Cart where cartId = ?1")
    Cart getUserCartByCartId(Integer cartId);

    @Query(value = "from Cart where userId = ?1 and (book.bookTitle like concat('%',?2,'%') or book.bookAuthor like concat('%',?2,'%'))")
    List<Cart> searchCartItems(Integer userId, String keyword);

    @Modifying
    @Query(value = "delete from Cart where cartId=?1")
    void deleteUserCartByCartId(Integer cartId);

    @Query(value = "from Cart where bookId = ?1 and userId = ?2")
    Cart getCartByBookIdAndUserId(Integer bookId, Integer userId);
}
