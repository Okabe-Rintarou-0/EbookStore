package com.catstore.model;

import com.alibaba.fastjson.JSON;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ActionMessage extends WebsocketMessage {
    public String action;

    public String message;

    public ActionMessage(String action) {
        super();
        this.action = action;
        type = "Action";
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
