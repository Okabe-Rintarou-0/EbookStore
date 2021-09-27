package com.catstore.repository;

import com.catstore.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Integer> {
    @Query(value = "from UserAuthority where userAccount = :userAccount and userPassword = :userPassword")
    UserAuthority checkAuthority(@Param("userAccount") String userAccount, @Param("userPassword") String userPassword);

    @Modifying
    @Query(value = "update UserAuthority set userIdentity = 2 where userId = ?1 and not userIdentity = 1")
    Integer banUserByUserId(Integer userId);

    @Modifying
    @Query(value = "update UserAuthority set userIdentity = 0 where userId = ?1 and userIdentity = 2")
    Integer unbanUserByUserId(Integer userId);

    @Query(value = "from UserAuthority where userAccount = ?1")
    UserAuthority getUserAuthorityByUserAccount(String userAccount);
}
