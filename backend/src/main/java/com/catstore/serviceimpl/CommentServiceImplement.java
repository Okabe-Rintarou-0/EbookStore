package com.catstore.serviceimpl;

import com.catstore.dao.CommentDao;
import com.catstore.dto.CommentDto;
import com.catstore.service.CommentService;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImplement implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public List<CommentDto> getComments(Integer bookId, Integer userId) {
        return commentDao.getComments(bookId, userId);
    }

    @Override
    public void addComment(int bookId, String content) {
        commentDao.addComment(bookId, SessionUtil.getUserId(), content);
    }

    @Override
    public void addComment(int bookId, int userId, String content) {
        commentDao.addComment(bookId, userId, content);
    }

    @Override
    public Integer updateAction(String commentId, Integer userId, Integer actionId) {
        return commentDao.updateAction(commentId, userId, actionId);
    }
}
