package com.catstore.repository;

import com.catstore.dao.FavouriteDao;
import com.catstore.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional
public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {
    @Query(value = "select * from (favourite natural join book) where user_id = ?1", nativeQuery = true)
    List<Map<String, String>> getFavouriteBooksByUserId(Integer userId);

    @Modifying
    @Query(value = "insert into favourite (user_id,book_id) values(?1,?2)", nativeQuery = true)
    void addFavouriteBook(Integer userId, Integer bookId);

    @Query(value = "from Favourite where userId=?1 and bookId =?2")
    Favourite getFavouriteByUserIdAndBookId(Integer userId, Integer bookId);

    @Modifying
    @Query(value = "delete from Favourite where userId = ?1 and bookId = ?2")
    void deleteFavouriteBookByUserIdAndBookId(Integer userId, Integer bookId);
}
