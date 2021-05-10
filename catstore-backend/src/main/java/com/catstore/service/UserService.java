package com.catstore.service;

import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;

public interface UserService {
    UserAuthority checkAuthority(String userAccount, String userPassword);

    User getUser();

    void setUserSignature(String userSignature);

    Float getUserProperty();

}
