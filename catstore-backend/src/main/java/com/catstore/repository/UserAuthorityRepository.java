package com.catstore.repository;

import com.catstore.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Integer> {
    @Query(value = "from UserAuthority where userAccount = :userAccount and userPassword = :userPassword")
    UserAuthority checkAuthority(@Param("userAccount") String userAccount, @Param("userPassword") String userPassword);
}
