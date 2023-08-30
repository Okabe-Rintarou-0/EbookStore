package com.catstore.dto;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto implements Serializable {
    private String commentId;
    private String username;
    private String userIcon;
    private String content;
    private Date date;
    private Integer likes;
    private Integer dislikes;
    private Integer action;

    private List<CommentDto> subComments;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
