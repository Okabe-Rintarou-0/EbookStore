package com.catstore.controller;

import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;
import com.catstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/checkAuthority")
    UserAuthority checkAuthority(@RequestParam("userAccount") String userAccount, @RequestParam("userPassword") String userPassword) {
        return userService.checkAuthority(userAccount, userPassword);
    }

    @RequestMapping("/getUser")
    User getUser() {
        return userService.getUser();
    }

    @RequestMapping("/setUserSignature")
    void setUserSignature(@RequestBody Map<String, String> params) {
        String userSignature = params.get("userSignature");
        if (userSignature != null) {
            userService.setUserSignature(userSignature);
            System.out.println(userSignature);
        }
    }
}
