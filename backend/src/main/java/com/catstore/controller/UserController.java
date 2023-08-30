package com.catstore.controller;

import com.catstore.entity.User;
import com.catstore.model.Message;
import com.catstore.service.UserService;
import com.catstore.constants.Constant;
import com.catstore.utils.messageUtils.MessageUtil;
import com.catstore.utils.sessionUtils.SessionUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Scope(value = "session")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    User getUser() {
        return userService.getUser(SessionUtil.getUserId());
    }

    @GetMapping("/signature")
    void setUserSignature(@RequestParam("newSig") String newSignature) {
        if (newSignature != null) {
            userService.setUserSignature(newSignature);
        }
    }

    @GetMapping("/all")
    Message getAllUsers() {
        Integer userIdentity = SessionUtil.getUserIdentity();
        if (userIdentity != null && userIdentity.equals(Constant.MANAGER)) {
            return MessageUtil.createMessage(MessageUtil.STAT_OK, MessageUtil.GENERAL_SUCCESS_MSG, JSONArray.fromObject(userService.getAllUsers()));
        }
        return MessageUtil.createMessage(MessageUtil.STAT_INVALID, MessageUtil.HAVE_NO_AUTHORITY_MSG);
    }

    @PostMapping("/ban")
    Message banUsers(@RequestBody ArrayList<Integer> userIdList) {
        Integer userIdentity = SessionUtil.getUserIdentity();
        if (userIdentity != null && userIdentity == 1) {
            if (userService.banUsers(userIdList))
                return MessageUtil.createMessage(MessageUtil.STAT_OK, MessageUtil.GENERAL_SUCCESS_MSG);
            else
                return MessageUtil.createMessage(MessageUtil.STAT_INVALID, MessageUtil.GENERAL_FAIL_MSG);
        }
        return MessageUtil.createMessage(MessageUtil.STAT_INVALID, MessageUtil.HAVE_NO_AUTHORITY_MSG);
    }

    @PostMapping("/unban")
    Message unbanUsers(@RequestBody ArrayList<Integer> userIdList) {
        Integer userIdentity = SessionUtil.getUserIdentity();
        if (userIdentity != null && userIdentity == 1) {
            if (userService.unbanUsers(userIdList))
                return MessageUtil.createMessage(MessageUtil.STAT_OK, MessageUtil.GENERAL_SUCCESS_MSG);
            else
                return MessageUtil.createMessage(MessageUtil.STAT_INVALID, MessageUtil.GENERAL_FAIL_MSG);
        }
        return MessageUtil.createMessage(MessageUtil.STAT_INVALID, MessageUtil.HAVE_NO_AUTHORITY_MSG);
    }
}
