package com.catstore.controller;

import com.catstore.entity.Comment;
import com.catstore.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
