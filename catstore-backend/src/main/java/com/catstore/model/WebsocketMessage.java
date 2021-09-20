package com.catstore.model;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebsocketMessage implements Serializable {
    public String type;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
