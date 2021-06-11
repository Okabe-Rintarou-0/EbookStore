package com.catstore.serviceimpl;

import com.catstore.dao.UserDao;
import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;
import com.catstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


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

    @Override
    public BigDecimal getUserProperty() {
        return userDao.getUserProperty();
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
    public Boolean checkDuplication(String userAccount) {
        System.out.println(userDao.getUserAuthorityByUserAccount(userAccount));
        return userDao.getUserAuthorityByUserAccount(userAccount) != null;
    }

    @Override
    @Transactional
    public Boolean register(String userAccount, String username, String password, String email) {
        Integer newUserId = userDao.addUser(username, email);
        userDao.addUserAuthority(newUserId, userAccount, password);
        return true;
    }
}
