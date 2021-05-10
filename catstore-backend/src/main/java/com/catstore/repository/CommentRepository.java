package com.catstore.repository;

import com.catstore.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value = "select comment_content,\n" +
            "       comment_date,\n" +
            "       likes,\n" +
            "       dislikes,\n" +
            "       username,\n" +
            "       user_icon\n" +
            "from (book\n" +
            "         natural join comment\n" +
            "         natural join user)\n" +
            "where book_id = 1", nativeQuery = true)
    List<Map<String, String>> getCommentsByBookId(Integer bookId);
}
