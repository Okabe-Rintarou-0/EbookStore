package com.catstore.model;

import com.alibaba.fastjson.JSON;

public class PingPongMessage extends WebsocketMessage {
    public Long timestamp = System.currentTimeMillis();

    public PingPongMessage() {
        type = "PingPong";
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
