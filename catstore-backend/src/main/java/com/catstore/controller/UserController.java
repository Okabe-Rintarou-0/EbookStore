package com.catstore.controller;

import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;
import com.catstore.service.UserService;
import com.catstore.utils.Constant;
import com.catstore.utils.messageUtils.Message;
import com.catstore.utils.messageUtils.MessageUtil;
import com.catstore.utils.sessionUtils.SessionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    @RequestMapping("/manager/getAllUsers")
    Message getAllUsers() {
        Integer userIdentity = SessionUtil.getUserIdentity();
        if (userIdentity != null && userIdentity == Constant.MANAGER) {
            return MessageUtil.createMessage(MessageUtil.GENERAL_SUCCESS_CODE, MessageUtil.GENERAL_SUCCESS_MSG, JSONArray.fromObject(userService.getAllUsers()));
        }
        return MessageUtil.createMessage(MessageUtil.HAVE_NO_AUTHORITY_CODE, MessageUtil.HAVE_NO_AUTHORITY_MSG);
    }

    @RequestMapping("/manager/banUsers")
    Message banUsers(@RequestBody ArrayList<Integer> userIdList) {
        Integer userIdentity = SessionUtil.getUserIdentity();
        if (userIdentity != null && userIdentity == 1) {
            if (userService.banUsers(userIdList))
                return MessageUtil.createMessage(MessageUtil.GENERAL_SUCCESS_CODE, MessageUtil.GENERAL_SUCCESS_MSG);
            else
                return MessageUtil.createMessage(MessageUtil.GENERAL_FAIL_CODE, MessageUtil.GENERAL_FAIL_MSG);
        }
        return MessageUtil.createMessage(MessageUtil.HAVE_NO_AUTHORITY_CODE, MessageUtil.HAVE_NO_AUTHORITY_MSG);
    }

    @RequestMapping("/manager/unbanUsers")
    Message unbanUsers(@RequestBody ArrayList<Integer> userIdList) {
        Integer userIdentity = SessionUtil.getUserIdentity();
        if (userIdentity != null && userIdentity == 1) {
            if (userService.unbanUsers(userIdList))
                return MessageUtil.createMessage(MessageUtil.GENERAL_SUCCESS_CODE, MessageUtil.GENERAL_SUCCESS_MSG);
            else
                return MessageUtil.createMessage(MessageUtil.GENERAL_FAIL_CODE, MessageUtil.GENERAL_FAIL_MSG);
        }
        return MessageUtil.createMessage(MessageUtil.HAVE_NO_AUTHORITY_CODE, MessageUtil.HAVE_NO_AUTHORITY_MSG);
    }

    @RequestMapping("/getUserProperty")
    BigDecimal getUserProperty() {
        return userService.getUserProperty();
    }

    @RequestMapping("/checkDuplication")
    Boolean checkDuplication(@RequestParam("username") String username) {
        return userService.checkDuplication(username);
    }

    @RequestMapping("/register")
    Message register(@RequestBody Map<String, String> registerInfo) {
        System.out.println(registerInfo);
        String userAccount = registerInfo.get("userAccount");
        String username = registerInfo.get("username");
        String email = registerInfo.get("email");
        String password = registerInfo.get("password");
        userService.register(userAccount, username, password, email);
        return MessageUtil.createMessage(MessageUtil.REGISTER_SUCCESS_CODE, MessageUtil.REGISTER_SUCCESS_MSG);
    }
}
