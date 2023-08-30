package com.catstore.bookservice.repository;

import com.catstore.bookservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findBookByBookTitleLike(String title);
}
