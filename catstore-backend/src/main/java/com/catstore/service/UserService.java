package com.catstore.service;

import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;
import com.catstore.model.ChatRoomMemberInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface UserService {
    UserAuthority checkAuthority(String userAccount, String userPassword);

    User getUser(Integer userId);

    void setUserSignature(String userSignature);

    BigDecimal getUserProperty(Integer userId);

    List<User> getAllUsers();

    Boolean banUsers(ArrayList<Integer> userIdList);

    Boolean unbanUsers(ArrayList<Integer> userIdList);

    ChatRoomMemberInfo getChatRoomMemberInfo(Integer userId);
}
