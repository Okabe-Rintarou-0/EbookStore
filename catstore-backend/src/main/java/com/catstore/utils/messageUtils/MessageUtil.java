package com.catstore.utils.messageUtils;

import com.catstore.model.Message;
import net.sf.json.JSONArray;
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
    public static final String PLACE_ORDER_SUCCESS_MSG = "下单成功，请耐心等待后台结算。";
    public static final String DELETE_SUCCESS_MSG = "删除成功!";
    public static final String DELETE_FAIL_MSG = "删除失败！";
    public static final String HAVE_NO_AUTHORITY_MSG = "您没有相关权限!";
    public static final String PUT_ON_SALE_SUCCESS_MSG = "上架商品成功！";
    public static final String PUT_ON_SALE_FAIL_MSG = "上架商品失败！";
    public static final String UNDERCARRIAGE_FAIL_MSG = "下架商品失败！";
    public static final String UNDERCARRIAGE_SUCCESS_MSG = "下架商品成功！";
    public static final String BANNED_MSG = "您的账号已被封禁！请联系管理员。";
    public static final String GENERAL_SUCCESS_MSG = "操作成功！";
    public static final String GENERAL_FAIL_MSG = "操作失败！";
    public static final String WRONG_FILE_FORMAT_MSG = "上传失败！请上传正确的csv文件！";
    public static final String UPLOAD_SUCCESS_MSG = "上传成功！";
    public static final String REGISTER_FAIL_MSG = "注册失败！";

    public static final String REGISTER_SUCCESS_MSG = "注册成功!";

    public static final int UPLOAD_SUCCESS_CODE = 13;
    public static final int WRONG_FILE_FORMAT_CODE = -13;
    public static final int PUT_ON_SALE_FAIL_CODE = -12;
    public static final int PUT_ON_SALE_SUCCESS_CODE = 10;
    public static final int BANNED_CODE = -10;
    public static final int LOGIN_ERROR_CODE = -1;
    public static final int LOGIN_SUCCESS_CODE = 1;
    public static final int NOT_LOGIN_CODE = -2;
    public static final int ALREADY_LOGIN_CODE = 0;
    public static final int LOGOUT_SUCCESS_CODE = 2;
    public static final int FAVOURITE_ALREADY_EXIST_CODE = -4;
    public static final int ADD_FAVOURITE_SUCCESS_CODE = 5;
    public static final int LOGOUT_ERROR_CODE = -3;
    public static final int ADD_TO_CART_SUCCESS_CODE = 6;
    public static final int CART_ALREADY_EXIST_CODE = -6;
    public static final int DELETE_SUCCESS_CODE = 2;
    public static final int HAVE_NO_AUTHORITY_CODE = -50;
    public static final int DELETE_FAIL_CODE = -6;
    public static final int UNDERCARRIAGE_FAIL_CODE = -8;
    public static final int UNDERCARRIAGE_SUCCESS_CODE = 9;
    public static final int STAT_INVALID = -1;
    public static final int STAT_OK = 200;

    public static Message createMessage(int statusCode, String message) {
        return new Message(statusCode, message);
    }

    public static Message createMessage(int statusCode, String message, JSONObject data) {
        return new Message(statusCode, message, data);
    }

    public static Message createMessage(int statusCode, String message, JSONArray arrayData) {
        return new Message(statusCode, message, arrayData);
    }
}
