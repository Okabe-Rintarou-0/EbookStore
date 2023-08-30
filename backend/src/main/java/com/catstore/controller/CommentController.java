package com.catstore.controller;

import com.catstore.annotation.SkipSessionCheck;
import com.catstore.dto.CommentDto;
import com.catstore.model.Message;
import com.catstore.service.CommentService;
import com.catstore.utils.messageUtils.MessageUtil;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{bookId}")
    List<CommentDto> getCommentsByBookId(@PathVariable(name = "bookId") int bookId) {
        return commentService.getComments(bookId, SessionUtil.getUserId());
    }

    @PutMapping("/{bookId}/{content}")
    Message addComment(@PathVariable(name = "bookId") int bookId, @PathVariable(name = "content") String content) {
        commentService.addComment(bookId, content);
        return MessageUtil.createMessage(MessageUtil.STAT_OK, "评论成功");
    }

    @PutMapping("/action/{commentId}/{newAction}")
    Message updateAction(@PathVariable(name = "commentId") String commentId, @PathVariable(name = "newAction") Integer newAction) {
        Integer action = commentService.updateAction(commentId, SessionUtil.getUserId(), newAction);
        return MessageUtil.createMessage(MessageUtil.STAT_OK, action.toString());
    }

    @SkipSessionCheck
    @PutMapping("/{bookId}/{userId}/{content}")
    Message addComment(@PathVariable(name = "bookId") int bookId, @PathVariable(name = "userId") int userId, @PathVariable(name = "content") String content) {
        commentService.addComment(bookId, userId, content);
        return MessageUtil.createMessage(MessageUtil.STAT_OK, "评论成功");
    }
}
