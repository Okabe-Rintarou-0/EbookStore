package com.catstore.dao;

import com.catstore.entity.Book;
import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {
    UserAuthority checkAuthority(String userAccount, String userPassword);

    List<User> getAllUsers();

    User getUser();

    void setUserSignature(String userSignature);

    BigDecimal getUserProperty();

    void updateUserProperty(BigDecimal delta);

    Boolean banUserByUserId(Integer userId);

    Boolean unbanUserByUserId(Integer userId);
}

