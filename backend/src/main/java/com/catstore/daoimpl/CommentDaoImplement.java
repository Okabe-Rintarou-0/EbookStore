package com.catstore.daoimpl;

import com.catstore.constants.CommentConstants;
import com.catstore.dao.CommentDao;
import com.catstore.dto.CommentDto;
import com.catstore.entity.Comment;
import com.catstore.entity.CommentAction;
import com.catstore.entity.User;
import com.catstore.repository.CommentActionRepository;
import com.catstore.repository.CommentRepository;
import com.catstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDaoImplement implements CommentDao {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentActionRepository commentActionRepository;

    @Autowired
    private UserRepository userRepository;

    private CommentDto fromComment(Comment comment, Integer userId) {
        User user = userRepository.getUserById(comment.getUserId());
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment cmt : comment.getSubComments()) {
            commentDtos.add(fromComment(cmt, userId));
        }
        CommentAction commentAction = commentActionRepository.getCommentActionByCommentIdAndUserId(comment.getId(), userId);
        Integer action = commentAction == null ?
                CommentConstants.None : commentAction.getAction();
        return new CommentDto(
                comment.getId(),
                user.getUsername(), user.getUserIcon(),
                comment.getContent(), comment.getDate(),
                comment.getLikes(), comment.getDislikes(),
                action, commentDtos
        );
    }

    @Override
    public List<CommentDto> getComments(Integer bookId, Integer userId) {
        List<Comment> comments = commentRepository.getCommentsByBookIdOrderByDate(bookId);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(fromComment(comment, userId));
        }
        return commentDtos;
    }

    @Override
    public void addComment(Integer bookId, Integer userId, String content) {
        commentRepository.save(new Comment(bookId, userId, content, new Date(), 0, 0, new ArrayList<>()));
    }

    @Override
    public CommentAction getAction(Integer bookId, Integer userId) {
        Comment comment = commentRepository.getCommentByBookId(bookId);
        return commentActionRepository.getCommentActionByCommentIdAndUserId(comment.getId(), userId);
    }

    @Override
    public Integer updateAction(String commentId, Integer userId, Integer newAction) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isPresent()) {
            CommentAction commentAction = commentActionRepository.getCommentActionByCommentIdAndUserId(commentId, userId);
            if (commentAction == null) commentAction = new CommentAction(commentId, userId);
            {
                Integer action = commentAction.getAction();
                Comment comment = commentOpt.get();
                Integer curLikes = comment.getLikes();
                Integer curDislikes = comment.getDislikes();
                if (newAction.equals(action)) {
                    if (newAction.equals(CommentConstants.Like))
                        --curLikes;
                    else if (newAction.equals(CommentConstants.Dislike))
                        --curDislikes;
                    newAction = CommentConstants.None;
                } else {
                    switch (newAction) {
                        case CommentConstants.None:
                            if (action == CommentConstants.Like) --curLikes;
                            else --curDislikes;
                            break;
                        case CommentConstants.Like:
                            ++curLikes;
                            curDislikes = action == CommentConstants.None ? curDislikes : curDislikes - 1;
                            break;
                        case CommentConstants.Dislike:
                            ++curDislikes;
                            curLikes = action == CommentConstants.None ? curLikes : curLikes - 1;
                            break;
                        default:
                            break;
                    }
                }

                comment.setLikes(curLikes);
                comment.setDislikes(curDislikes);
                commentRepository.save(comment);

                commentAction.setAction(newAction);
                commentActionRepository.save(commentAction);
            }
        }
        return newAction;
    }
}
