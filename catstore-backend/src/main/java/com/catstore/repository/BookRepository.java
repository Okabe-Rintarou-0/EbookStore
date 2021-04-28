package com.catstore.repository;

import com.catstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "from Book")
    List<Book> getBooks();

    @Query(value = "from Book where bookId =:bookId")
    Book getBookById(@Param("bookId") Integer bookId);
}
