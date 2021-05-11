package com.catstore.repository;

import com.catstore.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value = "select comment_content,\n" +
            "       comment_id,\n" +
            "       comment_date,\n" +
            "       likes,\n" +
            "       dislikes,\n" +
            "       username,\n" +
            "       user_icon\n" +
            "from (book\n" +
            "         natural join comment\n" +
            "         natural join user)\n" +
            "where book_id = ?1", nativeQuery = true)
    List<Map<String, String>> getCommentsByBookId(Integer bookId);

    @Modifying
    @Query(value = "update Comment set likes = likes+1 where commentId=?1")
    void addLikes(Integer commentId);

    @Modifying
    @Query(value = "update Comment set likes = likes-1 where commentId=?1")
    void cancelLikes(Integer commentId);

    @Modifying
    @Query(value = "update Comment set dislikes = dislikes+1 where commentId=?1")
    void addDislikes(Integer commentId);

    @Modifying
    @Query(value = "update Comment set dislikes = dislikes-1 where commentId=?1")
    void cancelDislikes(Integer commentId);
}
