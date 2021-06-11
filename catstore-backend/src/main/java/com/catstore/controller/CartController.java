package com.catstore.controller;

import com.catstore.entity.Cart;
import com.catstore.service.CartService;
import com.catstore.utils.messageUtils.Message;
import com.catstore.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CartController {

    CartService cartService;

    @Autowired
    void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping("/deleteCart")
    void deleteCartItem(@RequestParam("cartId") Integer cartId) {
        cartService.deleteCartItem(cartId);
    }

    @RequestMapping("/getAllCart")
    List<Cart> getAllCartItems() {
        return cartService.getAllCartItems();
    }

    @RequestMapping("/addToCart")
    Message addToCart(@RequestParam("bookId") Integer bookId) {
        if (cartService.addToCart(bookId))
            return MessageUtil.createMessage(MessageUtil.ADD_TO_CART_SUCCESS_CODE, MessageUtil.ADD_TO_CART_SUCCESS_MSG);
        return MessageUtil.createMessage(MessageUtil.CART_ALREADY_EXIST_CODE, MessageUtil.CART_ALREADY_EXIST_MSG);
    }

    @RequestMapping("/searchCartItems")
    List<Cart> searchCartItems(@RequestParam("keyword") String keyword) {
        return cartService.searchCartItems(keyword);
    }
}
