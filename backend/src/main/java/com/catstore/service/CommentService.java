package com.catstore.service;

import com.catstore.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getComments(Integer bookId, Integer userId);

    void addComment(int bookId, String content);

    void addComment(int bookId, int userId, String content);

    Integer updateAction(String commentId, Integer userId, Integer actionId);
}
