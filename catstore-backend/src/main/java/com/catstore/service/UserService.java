package com.catstore.service;

import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;

import java.math.BigDecimal;

public interface UserService {
    UserAuthority checkAuthority(String userAccount, String userPassword);

    User getUser();

    void setUserSignature(String userSignature);

    BigDecimal getUserProperty();

}
