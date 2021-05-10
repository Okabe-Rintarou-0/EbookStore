package com.catstore.daoimpl;

import com.catstore.dao.CommentDao;
import com.catstore.entity.Comment;
import com.catstore.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CommentDaoImplement implements CommentDao {

    CommentRepository commentRepository;

    @Autowired
    void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Map<String, String>> getCommentsByBookId(Integer bookId) {
        return commentRepository.getCommentsByBookId(bookId);
    }

    @Override
    public void addLike(Integer commentId) {

    }
}
