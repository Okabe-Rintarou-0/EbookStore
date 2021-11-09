package com.catstore.repository;

import com.catstore.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> getCommentsByBookIdOrderByDate(Integer bookId);

    Comment getCommentByBookId(Integer bookId);
}
