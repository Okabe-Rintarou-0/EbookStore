package com.catstore.repository;

import com.catstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "from Book where bookId =:bookId")
    Book getBookById(@Param("bookId") Integer bookId);

    @Query(value = "from Book where bookTitle like concat('%',:keyword,'%') or bookAuthor like concat('%',:keyword,'%')")
    List<Book> getBooksByKeyword(@Param("keyword") String keyword);

    Book findByBookTitle(String title);

    @Modifying
    @Query(value = "delete from Book where bookId = ?1")
    Integer deleteBookByBookId(Integer bookId);

    @Modifying
    @Query(value = "update Book set forSale = false where bookId = ?1")
    Integer undercarriageBookByBookId(Integer bookId);

    @Modifying
    @Query(value = "update Book set forSale = true where bookId = ?1")
    Integer putOnSale(Integer bookId);

    @Modifying
    @Query(value = "update Book set sales = sales + ?2, bookStock = bookStock - ?2 where bookId = ?1")
    void adjustStock(Integer bookId, Integer increment);

    @Query(value = "from Book order by sales desc")
    ArrayList<Book> getAllRankedBooks();

    @Modifying
    @Query(value = "update Book set bookCover = ?2,bookTitle = ?3,bookAuthor = ?4,bookDescription = ?5," +
            "bookDetails = ?6,bookStock=?7,bookPrice = ?8,bookTag = ?9,bookType = ?10 where bookId = ?1")
    void modifyBookWithBookId(Integer bookId, String bookCover, String bookTitle, String bookAuthor, String bookDescription,
                              String bookDetails, Integer bookStock, BigDecimal bookPrice, String bookTag, String bookType);


    List<Book> findAllByBookIdIn(List<Integer> bookId);
}
