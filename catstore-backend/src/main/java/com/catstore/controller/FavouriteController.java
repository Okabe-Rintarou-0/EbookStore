package com.catstore.controller;

import com.catstore.dao.FavouriteDao;
import com.catstore.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class FavouriteController {

    FavouriteService favouriteService;

    @Autowired
    void setFavouriteService(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @RequestMapping("/addFavouriteBook")
    void addFavouriteBook(@RequestParam("bookId") Integer bookId) {
        favouriteService.addFavouriteBook(bookId);
    }

    @RequestMapping("/deleteFavouriteBook")
    void deleteFavouriteBook(@RequestParam("bookId") Integer bookId) {
        favouriteService.deleteFavouriteBook(bookId);
    }

    @RequestMapping("/moveToCart")
    void moveToCart(@RequestParam("bookId") Integer bookId) {
        favouriteService.moveToCart(bookId);
    }

    @RequestMapping("/getFavouriteBooks")
    List<Map<String, String>> getFavouriteBooks() {
        return favouriteService.getFavouriteBooks();
    }
}
