package com.catstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class Comment {
    @Id
    private Integer commentId;
    private Integer userId;
    private String username;
    private String commentContent;
    private String commentDate;
    private Integer likes;
    private Integer dislikes;
}
