package com.catstore.dao;

import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;

import java.math.BigDecimal;

public interface UserDao {
    UserAuthority checkAuthority(String userAccount, String userPassword);

    User getUser();

    void setUserSignature(String userSignature);

    BigDecimal getUserProperty();

    void updateUserProperty(BigDecimal delta);
}

