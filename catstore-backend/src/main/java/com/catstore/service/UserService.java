package com.catstore.service;

import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface UserService {
    UserAuthority checkAuthority(String userAccount, String userPassword);

    User getUser();

    void setUserSignature(String userSignature);

    BigDecimal getUserProperty();

    List<User> getAllUsers();

    Boolean banUsers(ArrayList<Integer> userIdList);

    Boolean unbanUsers(ArrayList<Integer> userIdList);

    Boolean checkDuplication(String username);

    Boolean register(String userAccount, String username, String password, String email);
}
