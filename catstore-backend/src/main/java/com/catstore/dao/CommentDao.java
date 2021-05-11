package com.catstore.dao;

import com.catstore.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentDao {
    List<Map<String, String>> getCommentsByBookId(Integer bookId);

    void addLikes(Integer commentId);

    void cancelLikes(Integer commentId);

    void addDislikes(Integer commentId);

    void cancelDislikes(Integer commentId);

    String existedCommentAction(Integer commentId);
}
