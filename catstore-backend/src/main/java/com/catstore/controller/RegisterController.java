package com.catstore.controller;

import com.catstore.service.RegisterService;
import com.catstore.model.Message;
import com.catstore.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author lzh
 * @Title:
 * @Package
 * @Description:
 * @date 2021/9/14 13:15
 */
@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping
    Message register(@RequestBody Map<String, String> registerInfo) {
        if (registerService.register(registerInfo))
            return MessageUtil.createMessage(MessageUtil.STAT_OK, MessageUtil.REGISTER_SUCCESS_MSG);
        else
            return MessageUtil.createMessage(MessageUtil.STAT_INVALID, MessageUtil.REGISTER_FAIL_MSG);
    }

    @GetMapping("/checkDup")
    Boolean checkDuplication(@RequestParam("account") String account) {
        return registerService.checkDuplication(account);
    }
}
