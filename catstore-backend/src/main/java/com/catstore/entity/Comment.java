package com.catstore.entity;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "comments")
public class Comment implements Serializable {
    private String id;
    private Integer bookId;
    private Integer userId;
    private String content;
    private Date date;
    private Integer likes;
    private Integer dislikes;

    private List<Comment> subComments;

    public Comment(Integer bookId, Integer userId, String content, Date date, Integer likes, Integer dislikes, List<Comment> subComments) {
        this.bookId = bookId;
        this.userId = userId;
        this.content = content;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
        this.subComments = subComments;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
