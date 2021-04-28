package com.catstore.serviceimpl;

import com.catstore.dao.UserDao;
import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;
import com.catstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImplement implements UserService {

    final UserDao userDao;

    @Autowired
    UserServiceImplement(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserAuthority checkAuthority(String userAccount, String userPassword) {
        return userDao.checkAuthority(userAccount, userPassword);
    }

    @Override
    public User getUser() {
        return userDao.getUser();
    }

    @Override
    public void setUserSignature(String userSignature) {
        userDao.setUserSignature(userSignature);
    }
}
