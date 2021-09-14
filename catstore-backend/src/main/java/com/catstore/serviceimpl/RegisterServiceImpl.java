package com.catstore.serviceimpl;

import com.catstore.dao.UserDao;
import com.catstore.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

/**
 * @author
 * @Title:
 * @Package
 * @Description:
 * @date 2021/9/14 13:17
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public boolean register(Map<String, String> registerInfo) {
        String userAccount = registerInfo.get("userAccount");
        String username = registerInfo.get("username");
        String email = registerInfo.get("email");
        String password = registerInfo.get("password");
        Integer newUserId = userDao.addUser(username, email);
        userDao.addUserAuthority(newUserId, userAccount, password);
        return true;
    }

    @Override
    public boolean checkDuplication(String account) {
        return userDao.getUserAuthorityByUserAccount(account) != null;
    }
}
