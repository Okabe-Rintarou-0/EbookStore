package com.catstore.repository;

import com.catstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "from User where userId = :userId")
    User getUserById(@Param("userId") Integer userId);

    @Modifying
    @Query("update User set userSignature = :userSignature where userId = :userId ")
    void setUserSignature(@Param("userId") Integer userId, @Param("userSignature") String userSignature);

    @Query(value = "select userProperty from User where userId =?1")
    BigDecimal getUserPropertyByUserId(Integer userId);

    @Modifying
    @Query(value = "update User set userProperty = userProperty + ?2 where userId =?1")
    void updateUserProperty(Integer userId, BigDecimal delta);
}
