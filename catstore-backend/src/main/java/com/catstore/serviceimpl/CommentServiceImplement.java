package com.catstore.serviceimpl;

import com.catstore.dao.CommentDao;
import com.catstore.entity.Comment;
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
}
