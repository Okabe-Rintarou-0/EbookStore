package com.catstore.service;

import java.util.Map;

/**
 * @author
 * @Title:
 * @Package
 * @Description:
 * @date 2021/9/14 13:16
 */
public interface RegisterService {
    boolean register(Map<String, String> registerInfo);

    boolean checkDuplication(String account);
}
