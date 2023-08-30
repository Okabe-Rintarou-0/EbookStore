package com.catstore.repository;

import com.catstore.entity.CommentAction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentActionRepository extends MongoRepository<CommentAction, String> {
    CommentAction getCommentActionByCommentIdAndUserId(String commentId, Integer userId);
}
