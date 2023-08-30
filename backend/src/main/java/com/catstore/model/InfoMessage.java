package com.catstore.model;

import com.alibaba.fastjson.JSON;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InfoMessage extends WebsocketMessage {
    public String info;

    public ChatRoomMemberInfo roomMemberInfo;

    public InfoMessage(ChatRoomMemberInfo roomMemberInfo, String info) {
        super();
        this.info = info;
        this.roomMemberInfo = roomMemberInfo;
        type = "Info";
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
