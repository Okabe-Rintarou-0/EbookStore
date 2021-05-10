package com.catstore.service;

import com.catstore.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    List<Map<String, String>> getCommentsByBookId(Integer bookId);
}
