package com.catstore.controller;

import com.catstore.entity.Comment;
import com.catstore.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CommentController {

    CommentService commentService;

    @Autowired
    void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping("/getComments")
    List<Map<String, String>> getCommentsByBookId(@RequestParam("bookId") Integer bookId) {
        return commentService.getCommentsByBookId(bookId);
    }

    @RequestMapping("/postUserCommentAction")
    void postUserCommentAction(@RequestParam("commentId") Integer commentId, @RequestBody Map<String, Integer> params) {
        Integer like = params.get("like");
        Integer dislike = params.get("dislike");
        System.out.print("like: ");
        System.out.println(like);
        System.out.print("dislike: ");
        System.out.println(dislike);
        commentService.handleUserCommentAction(commentId, like, dislike);
    }

    @RequestMapping("/getCommentAction")
    String getCommentAction(@RequestParam("commentId") Integer commentId) {
        System.out.println(commentService.getCommentActionByCommentId(commentId));
        return commentService.getCommentActionByCommentId(commentId);
    }
}
