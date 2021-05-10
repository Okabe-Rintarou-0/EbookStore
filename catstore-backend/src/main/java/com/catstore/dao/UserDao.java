package com.catstore.dao;

import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;

public interface UserDao {
    UserAuthority checkAuthority(String userAccount, String userPassword);

    User getUser();

    void setUserSignature(String userSignature);

    Float getUserProperty();

    void updateUserProperty(Float delta);
}

