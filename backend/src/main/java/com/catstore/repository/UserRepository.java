package com.catstore.repository;

import com.catstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "from User where userId = :userId")
    User getUserById(@Param("userId") Integer userId);

    @Modifying
    @Query("update User set userSignature = :userSignature where userId = :userId ")
    void setUserSignature(@Param("userId") Integer userId, @Param("userSignature") String userSignature);

    @Query(value = "from User where not userIdentity = 1")
        //except for the managers
    List<User> getAllUsers();

    @Modifying
    @Query(value = "update User set userProperty = userProperty + ?2 where userId =?1")
    void updateUserProperty(Integer userId, BigDecimal delta);

    @Modifying
    @Query(value = "update User set userIdentity = 2 where userId = ?1 and not userIdentity = 1")
    Integer banUserByUserId(Integer userId);

    @Modifying
    @Query(value = "update User set userIdentity = 0 where userId = ?1 and userIdentity = 2")
    Integer unbanUserByUserId(Integer userId);
}
