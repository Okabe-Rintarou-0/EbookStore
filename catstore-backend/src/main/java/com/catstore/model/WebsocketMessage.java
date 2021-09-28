package com.catstore.model;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class WebsocketMessage implements Serializable {
    public String type;
    public Date timestamp;

    public WebsocketMessage() {
        timestamp = new Date();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
