package com.catstore.daoimpl;

import com.catstore.dao.UserDao;
import com.catstore.entity.User;
import com.catstore.entity.UserAuthority;
import com.catstore.model.ChatRoomMemberInfo;
import com.catstore.repository.UserAuthorityRepository;
import com.catstore.repository.UserRepository;
import com.catstore.constants.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class UserDaoImplement implements UserDao {
    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserAuthority checkAuthority(String userAccount, String userPassword) {
        return userAuthorityRepository.checkAuthority(userAccount, userPassword);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUser(Integer userId) {
        if (userId != null)
            return userRepository.getUserById(userId);
        return null;
    }

    @Override
    public BigDecimal getUserProperty(int userId) {
        return getUser(userId).getUserProperty();
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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updateUserProperty(Integer userId, BigDecimal delta) {
        if (userId != null) {
            userRepository.updateUserProperty(userId, delta);
        }
    }

    @Override
    public Boolean banUserByUserId(Integer userId) {
        return userRepository.banUserByUserId(userId) > 0 && userAuthorityRepository.banUserByUserId(userId) > 0;
    }

    @Override
    public Boolean unbanUserByUserId(Integer userId) {
        return userRepository.unbanUserByUserId(userId) > 0 && userAuthorityRepository.unbanUserByUserId(userId) > 0;
    }

    @Override
    public UserAuthority getUserAuthorityByUserAccount(String userAccount) {
        return userAuthorityRepository.getUserAuthorityByUserAccount(userAccount);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void addUserAuthority(Integer userId, String userAccount, String password) {
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setUserAccount(userAccount);
        userAuthority.setUserIdentity(Constant.COMMON_USER);
        userAuthority.setUserPassword(password);
        userAuthority.setUserId(userId);
        userAuthorityRepository.save(userAuthority);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public Integer addUser(String username, String email) {
        User user = new User();
        user.setUserAddress("");
        user.setUserIdentity(Constant.COMMON_USER);
        user.setUserIcon(Constant.DEFAULT_USER_ICON);
        user.setUserSignature("");
        user.setUserTel("");
        user.setUsername(username);
        user.setUserProperty(BigDecimal.ZERO);
        user.setEmail(email);
        return userRepository.save(user).getUserId();
    }

    @Override
    public ChatRoomMemberInfo getChatRoomMemberInfo(Integer userId) {
        if (userId != null) {
            User user = getUser(userId);
            if (user != null) {
                ChatRoomMemberInfo roomMemberInfo = new ChatRoomMemberInfo();
                roomMemberInfo.setUserIcon(user.getUserIcon());
                roomMemberInfo.setUsername(user.getUsername());
                return roomMemberInfo;
            }
        }
        return null;
    }
}
