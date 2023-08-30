package com.catstore.controller;

import com.catstore.entity.UserAuthority;
import com.catstore.model.Message;
import com.catstore.service.UserService;
import com.catstore.constants.Constant;
import com.catstore.utils.messageUtils.MessageUtil;
import com.catstore.utils.sessionUtils.SessionUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    final UserService userService;

    @Autowired
    LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/checkSession")
    public Message checkSession() {
        JSONObject authority = SessionUtil.getAuthority();
        if (authority != null) {
            return MessageUtil.createMessage(MessageUtil.ALREADY_LOGIN_CODE, MessageUtil.ALREADY_LOGIN_MSG, authority);
        } else
            return MessageUtil.createMessage(MessageUtil.NOT_LOGIN_CODE, MessageUtil.NOT_LOGIN_MSG);
    }

    @PostMapping("/login")
    public Message login(@RequestBody Map<String, String> params) {
        String userAccount = params.get("account");
        String userPassword = params.get("password");
        UserAuthority userAuthority = userService.checkAuthority(userAccount, userPassword);
        System.out.println(userAuthority);
        if (userAuthority != null) {
            if (userAuthority.getUserIdentity().equals(Constant.BANNED_USER))
                return MessageUtil.createMessage(MessageUtil.BANNED_CODE, MessageUtil.BANNED_MSG);
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

    @GetMapping("/logout")
    public Message logout() {
        boolean status = SessionUtil.removeSession();
        if (!status) {
            return MessageUtil.createMessage(MessageUtil.LOGOUT_ERROR_CODE, MessageUtil.LOGOUT_ERROR_MSG);
        } else
            return MessageUtil.createMessage(MessageUtil.LOGOUT_SUCCESS_CODE, MessageUtil.LOGOUT_SUCCESS_MSG);
    }
}
