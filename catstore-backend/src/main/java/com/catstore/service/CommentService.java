package com.catstore.service;

import com.catstore.entity.Comment;
import com.catstore.entity.UserCommentAction;

import java.util.List;
import java.util.Map;

public interface CommentService {
    List<Map<String, String>> getCommentsByBookId(Integer bookId);

    void handleUserCommentAction(Integer commentId, Integer like, Integer dislike);

    String getCommentActionByCommentId(Integer commentId);
}
