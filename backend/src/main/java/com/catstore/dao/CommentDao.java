package com.catstore.dao;

import com.catstore.dto.CommentDto;
import com.catstore.entity.CommentAction;

import java.util.List;

public interface CommentDao {
    List<CommentDto> getComments(Integer bookId, Integer userId);

    void addComment(Integer bookId, Integer userId, String content);

    CommentAction getAction(Integer bookId, Integer userId);

    Integer updateAction(String commentId, Integer userId, Integer newAction);
}
