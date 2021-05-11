package com.catstore.serviceimpl;

import com.catstore.dao.CommentDao;
import com.catstore.entity.Comment;
import com.catstore.entity.UserCommentAction;
import com.catstore.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImplement implements CommentService {

    CommentDao commentDao;

    @Autowired
    void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public List<Map<String, String>> getCommentsByBookId(Integer bookId) {
        return commentDao.getCommentsByBookId(bookId);
    }

    @Override
    public void handleUserCommentAction(Integer commentId, Integer like, Integer dislike) {
        if (like > 0) {
            commentDao.addLikes(commentId);
        } else if (like < 0)
            commentDao.cancelLikes(commentId);
        if (dislike > 0) {
            commentDao.addDislikes(commentId);
        } else if (dislike < 0)
            commentDao.cancelDislikes(commentId);
    }

    @Override
    public String getCommentActionByCommentId(Integer commentId) {
        return commentDao.existedCommentAction(commentId);
    }
}
