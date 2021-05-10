package com.catstore.daoimpl;

import com.catstore.dao.UserDao;
import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;
import com.catstore.repository.UserAuthorityRepository;
import com.catstore.repository.UserRepository;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Repository
public class UserDaoImplement implements UserDao {
    UserAuthorityRepository userAuthorityRepository;
    UserRepository userRepository;

    @Autowired
    public void setUserAuthorityRepository(UserAuthorityRepository userAuthorityRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserAuthority checkAuthority(String userAccount, String userPassword) {
        return userAuthorityRepository.checkAuthority(userAccount, userPassword);
    }

    @Override
    public User getUser() {
        Integer userId = SessionUtil.getUserId();
        if (userId != null)
            return userRepository.getUserById(userId);
        return null;
    }

    @Override
    public void setUserSignature(String userSignature) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                Integer userId = (Integer) session.getAttribute("userId");
                userRepository.setUserSignature(userId, userSignature);
            }
        }
    }

    @Override
    public Float getUserProperty() {
        Integer userId = SessionUtil.getUserId();
        if (userId != null) {
            return userRepository.getUserPropertyByUserId(userId);
        }
        return null;
    }

    @Override
    public void updateUserProperty(Float delta) {
        Integer userId = SessionUtil.getUserId();
        if (userId != null) {
            userRepository.updateUserProperty(userId, delta);
        }
    }
}
