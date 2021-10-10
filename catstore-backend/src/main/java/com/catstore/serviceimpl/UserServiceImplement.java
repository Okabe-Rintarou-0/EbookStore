package com.catstore.serviceimpl;

import com.catstore.constants.RedisKeys;
import com.catstore.dao.UserDao;
import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;
import com.catstore.model.ChatRoomMemberInfo;
import com.catstore.service.UserService;
import com.catstore.utils.redisUtils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImplement implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserAuthority checkAuthority(String userAccount, String userPassword) {
        return userDao.checkAuthority(userAccount, userPassword);
    }

    @Override
    public User getUser(Integer userId) {
        String redisKey = RedisKeys.UserKey + ":" + userId;
        User user = redisUtil.get(redisKey, User.class);
        if (user == null) {
            System.out.println("Fetch user from database.");
            user = userDao.getUser(userId);
            if (user != null) {
                redisUtil.set(redisKey, user);
                redisUtil.expire(redisKey, 600);
            }
        } else
            System.out.println("Directly fetch user from redis.");
        return user;
    }

    @Override
    public void setUserSignature(String userSignature) {
        userDao.setUserSignature(userSignature);
    }

    @Override
    public BigDecimal getUserProperty(Integer userId) {
        return userId != null ? userDao.getUserProperty(userId) : null;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public Boolean banUsers(ArrayList<Integer> userIdList) {
        for (Integer userId : userIdList) {
            if (!userDao.banUserByUserId(userId))
                return false;
        }
        return true;
    }

    @Override
    public Boolean unbanUsers(ArrayList<Integer> userIdList) {
        for (Integer userId : userIdList) {
            if (!userDao.unbanUserByUserId(userId))
                return false;
        }
        return true;
    }

    @Override
    public ChatRoomMemberInfo getChatRoomMemberInfo(Integer userId) {
        return userDao.getChatRoomMemberInfo(userId);
    }
}
