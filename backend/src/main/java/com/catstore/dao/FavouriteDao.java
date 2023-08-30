package com.catstore.dao;

import com.catstore.repository.FavouriteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FavouriteDao {
    boolean addFavouriteBook(Integer bookId);

    void deleteFavouriteBook(Integer bookId);

    List<Map<String, String>> getFavouriteBooks();
}
