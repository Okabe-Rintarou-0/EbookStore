package com.catstore.controller;

import com.catstore.entity.Cart;
import com.catstore.service.CartService;
import com.catstore.model.Message;
import com.catstore.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    CartService cartService;

    @Autowired
    void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/delete")
    void deleteCartItem(@RequestParam("cartId") Integer cartId) {
        cartService.deleteCartItem(cartId);
    }

    @GetMapping("/all")
    List<Cart> getAllCartItems() {
        return cartService.getAllCartItems();
    }

    @GetMapping("/add")
    Message addToCart(@RequestParam("bookId") Integer bookId) {
        if (cartService.addToCart(bookId))
            return MessageUtil.createMessage(MessageUtil.ADD_TO_CART_SUCCESS_CODE, MessageUtil.ADD_TO_CART_SUCCESS_MSG);
        return MessageUtil.createMessage(MessageUtil.CART_ALREADY_EXIST_CODE, MessageUtil.CART_ALREADY_EXIST_MSG);
    }

    @GetMapping("/search")
    List<Cart> searchCartItems(@RequestParam("keyword") String keyword) {
        return cartService.searchCartItems(keyword);
    }
}
