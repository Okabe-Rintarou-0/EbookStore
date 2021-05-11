package com.catstore.controller;

import com.catstore.entity.UserAuthority;
import com.catstore.service.UserService;
import com.catstore.utils.messageUtils.Message;
import com.catstore.utils.messageUtils.MessageUtil;
import com.catstore.utils.sessionUtils.SessionUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    final UserService userService;

    @Autowired
    LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/checkSession")
    public Message checkSession() {
        JSONObject authority = SessionUtil.getAuthority();
        if (authority != null) {
            return MessageUtil.createMessage(MessageUtil.ALREADY_LOGIN_CODE, MessageUtil.ALREADY_LOGIN_MSG);
        } else
            return MessageUtil.createMessage(MessageUtil.NOT_LOGIN_CODE, MessageUtil.NOT_LOGIN_MSG);
    }

    @RequestMapping("/login")
    public Message login(@RequestBody Map<String, String> params) {
        String userAccount = params.get("userAccount");
        String userPassword = params.get("userPassword");
        UserAuthority userAuthority = userService.checkAuthority(userAccount, userPassword);
        System.out.println(userAuthority);
        if (userAuthority != null) {
            JSONObject newSession = new JSONObject();
            newSession.put("userId", userAuthority.getUserId());
            newSession.put("userAccount", userAuthority.getUserAccount());
            newSession.put("userIdentity", userAuthority.getUserIdentity());
            SessionUtil.setSession(newSession);

            JSONObject responseData = JSONObject.fromObject(userAuthority);
            responseData.remove("userPassword");

            return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE, MessageUtil.LOGIN_SUCCESS_MSG, responseData);
        } else {
            return MessageUtil.createMessage(MessageUtil.LOGIN_ERROR_CODE, MessageUtil.LOGIN_ERROR_MSG);
        }
    }

    @RequestMapping("/logout")
    public Message logout() {
        boolean status = SessionUtil.removeSession();
        if (!status) {
            return MessageUtil.createMessage(MessageUtil.LOGOUT_ERROR_CODE, MessageUtil.LOGOUT_ERROR_MSG);
        } else
            return MessageUtil.createMessage(MessageUtil.LOGOUT_SUCCESS_CODE, MessageUtil.LOGOUT_SUCCESS_MSG);
    }
}
