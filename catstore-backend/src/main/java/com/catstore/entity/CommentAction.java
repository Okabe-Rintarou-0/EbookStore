package com.catstore.entity;

import com.catstore.constants.CommentConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "actions")
public class CommentAction {
    private String id;
    private String commentId;
    private Integer userId;
    private Integer action;

    public CommentAction(String commentId, Integer userId) {
        this.commentId = commentId;
        this.userId = userId;
        this.action = CommentConstants.None;
    }
}
