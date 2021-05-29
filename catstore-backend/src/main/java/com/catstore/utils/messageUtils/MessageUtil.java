package com.catstore.utils.messageUtils;

import net.sf.json.JSONObject;

public class MessageUtil {
    public static final String LOGIN_ERROR_MSG = "密码或用户名有误，请重新输入！";
    public static final String LOGIN_SUCCESS_MSG = "登陆成功！";
    public static final String ALREADY_LOGIN_MSG = "用户已经登陆。";
    public static final String NOT_LOGIN_MSG = "用户未登陆！";
    public static final String LOGOUT_SUCCESS_MSG = "退出登录成功！";
    public static final String LOGOUT_ERROR_MSG = "退出登录失败！";
    public static final String ADD_TO_CART_SUCCESS_MSG = "加入购物车成功!";
    public static final String ADD_FAVOURITE_SUCCESS_MSG = "加入收藏成功!";
    public static final String FAVOURITE_ALREADY_EXIST_MSG = "已加入收藏，请勿重复操作！";
    public static final String CART_ALREADY_EXIST_MSG = "购物车中已存在该商品，请勿重复添加！";
    public static final String PURCHASE_SUCCESS_MSG = "购买成功！";
    public static final String PURCHASE_FAIL_MSG = "购买失败！";


    public static final int LOGIN_ERROR_CODE = -1;
    public static final int LOGIN_SUCCESS_CODE = 1;
    public static final int NOT_LOGIN_CODE = -2;
    public static final int ALREADY_LOGIN_CODE = 0;
    public static final int LOGOUT_SUCCESS_CODE = 2;
    public static final int FAVOURITE_ALREADY_EXIST_CODE = -4;
    public static final int ADD_FAVOURITE_SUCCESS_CODE = 5;
    public static final int LOGOUT_ERROR_CODE = -3;
    public static final int ADD_TO_CART_SUCCESS_CODE = 6;
    public static final int PURCHASE_SUCCESS_CODE = 7;
    public static final int CART_ALREADY_EXIST_CODE = -6;
    public static final int PURCHASE_FAIL_CODE = -5;

    public static Message createMessage(int statusCode, String message) {
        return new Message(statusCode, message);
    }

    public static Message createMessage(int statusCode, String message, JSONObject data) {
        return new Message(statusCode, message, data);
    }
}
