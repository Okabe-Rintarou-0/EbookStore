package com.catstore.repository;

import com.catstore.entity.UserCommentAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface UserCommentActionRepository extends JpaRepository<UserCommentAction, Integer> {
    @Modifying
    @Query(value = "insert into user_comment_action(user_id,action,comment_id) values(?1,?2,?3)", nativeQuery = true)
    void addUserCommentAction(Integer userId, String action, Integer commentId);

    @Modifying
    @Query(value = "delete from UserCommentAction where userId = ?1 and commentId = ?1")
    void deleteUserCommentAction(Integer userId, Integer commentId);

    @Modifying
    @Query(value = "update UserCommentAction set action = ?1")
    void updateAction(String newAction);

    @Query(value = "from UserCommentAction where userId = ?1 and commentId = ?2")
    UserCommentAction getUserCommentAction(Integer userId, Integer commentId);
}
