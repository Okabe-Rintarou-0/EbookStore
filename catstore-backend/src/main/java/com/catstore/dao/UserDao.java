package com.catstore.dao;

import com.catstore.entity.Book;
import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;
import com.catstore.model.ChatRoomMemberInfo;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {
    UserAuthority checkAuthority(String userAccount, String userPassword);

    List<User> getAllUsers();

    User getUser(Integer userId);

    BigDecimal getUserProperty(int userId);

    void setUserSignature(String userSignature);

    void updateUserProperty(Integer userId, BigDecimal delta);

    Boolean banUserByUserId(Integer userId);

    Boolean unbanUserByUserId(Integer userId);

    UserAuthority getUserAuthorityByUserAccount(String userAccount);

    void addUserAuthority(Integer userId, String userAccount, String password);

    Integer addUser(String username, String email);

    ChatRoomMemberInfo getChatRoomMemberInfo(Integer userId);
}

