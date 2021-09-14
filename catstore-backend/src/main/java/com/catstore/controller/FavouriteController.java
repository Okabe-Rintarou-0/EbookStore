package com.catstore.controller;

import com.catstore.service.FavouriteService;
import com.catstore.model.Message;
import com.catstore.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/addFavouriteBook")
    Message addFavouriteBook(@RequestParam("bookId") Integer bookId) {
        if (favouriteService.addFavouriteBook(bookId))
            return MessageUtil.createMessage(MessageUtil.ADD_FAVOURITE_SUCCESS_CODE, MessageUtil.ADD_FAVOURITE_SUCCESS_MSG);
        else
            return MessageUtil.createMessage(MessageUtil.FAVOURITE_ALREADY_EXIST_CODE, MessageUtil.FAVOURITE_ALREADY_EXIST_MSG);
    }

    @GetMapping("/deleteFavouriteBook")
    void deleteFavouriteBook(@RequestParam("bookId") Integer bookId) {
        favouriteService.deleteFavouriteBook(bookId);
    }

    @GetMapping("/moveToCart")
    void moveToCart(@RequestParam("bookId") Integer bookId) {
        favouriteService.moveToCart(bookId);
    }

    @GetMapping("/getFavouriteBooks")
    List<Map<String, String>> getFavouriteBooks() {
        return favouriteService.getFavouriteBooks();
    }
}
