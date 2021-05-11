package com.catstore.daoimpl;

import com.catstore.dao.CommentDao;
import com.catstore.entity.Comment;
import com.catstore.entity.UserCommentAction;
import com.catstore.repository.CommentRepository;
import com.catstore.repository.UserCommentActionRepository;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CommentDaoImplement implements CommentDao {

    CommentRepository commentRepository;
    UserCommentActionRepository userCommentActionRepository;

    @Autowired
    void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Autowired
    void setUserCommentActionRepository(UserCommentActionRepository userCommentActionRepository) {
        this.userCommentActionRepository = userCommentActionRepository;
    }

    @Override
    public List<Map<String, String>> getCommentsByBookId(Integer bookId) {
        return commentRepository.getCommentsByBookId(bookId);
    }

    @Override
    public void addLikes(Integer commentId) {
        Integer userId = SessionUtil.getUserId();
        if (userId != null) {
            UserCommentAction userCommentAction = userCommentActionRepository.getUserCommentAction(userId, commentId);
            if (userCommentAction != null) {
                userCommentAction.setAction("like");
            } else
                userCommentActionRepository.addUserCommentAction(userId, "like", commentId);
            commentRepository.addLikes(commentId);
        }
    }

    @Override
    public void cancelLikes(Integer commentId) {
        Integer userId = SessionUtil.getUserId();
        if (userId != null) {
            UserCommentAction userCommentAction = userCommentActionRepository.getUserCommentAction(userId, commentId);
            if (userCommentAction != null && userCommentAction.getAction().equals("like")) {
                userCommentActionRepository.deleteUserCommentAction(userId, commentId);
            }
            commentRepository.cancelLikes(commentId);
        }
    }

    @Override
    public void addDislikes(Integer commentId) {
        Integer userId = SessionUtil.getUserId();
        if (userId != null) {
            UserCommentAction userCommentAction = userCommentActionRepository.getUserCommentAction(userId, commentId);
            if (userCommentAction != null) {
                userCommentAction.setAction("dislike");
            } else
                userCommentActionRepository.addUserCommentAction(userId, "dislike", commentId);
            commentRepository.addDislikes(commentId);
        }
    }

    @Override
    public void cancelDislikes(Integer commentId) {
        Integer userId = SessionUtil.getUserId();
        if (userId != null) {
            UserCommentAction userCommentAction = userCommentActionRepository.getUserCommentAction(userId, commentId);
            if (userCommentAction != null && userCommentAction.getAction().equals("dislike")) {
                userCommentActionRepository.deleteUserCommentAction(userId, commentId);
            }
            commentRepository.cancelDislikes(commentId);
        }
    }

    @Override
    public String existedCommentAction(Integer commentId) {
        Integer userId = SessionUtil.getUserId();
        if (userId != null) {
            UserCommentAction userCommentAction = userCommentActionRepository.getUserCommentAction(userId, commentId);
            if (userCommentAction != null)
                return userCommentAction.getAction();
        }
        return null;
    }
}
