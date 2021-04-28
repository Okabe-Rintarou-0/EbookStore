package com.catstore.repository;

import com.catstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "from User where userId = :userId")
    User getUserById(@Param("userId") Integer userId);

    @Modifying
    @Query("update User set userSignature = :userSignature where userId = :userId ")
    void setUserSignature(@Param("userId") Integer userId, @Param("userSignature") String userSignature);
}
