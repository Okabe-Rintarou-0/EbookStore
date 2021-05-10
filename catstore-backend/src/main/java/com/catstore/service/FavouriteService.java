package com.catstore.service;

import java.util.List;
import java.util.Map;

public interface FavouriteService {
    void addFavouriteBook(Integer bookId);

    void deleteFavouriteBook(Integer bookId);

    void moveToCart(Integer bookId);

    List<Map<String, String>> getFavouriteBooks();
}
