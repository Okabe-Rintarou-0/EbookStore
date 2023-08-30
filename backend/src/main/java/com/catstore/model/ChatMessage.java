package com.catstore.model;

import com.alibaba.fastjson.JSON;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ChatMessage extends WebsocketMessage {
    public String message;

    public ChatRoomMemberInfo roomMemberInfo;

    public ChatMessage(ChatRoomMemberInfo roomMemberInfo, String message) {
        super();
        this.roomMemberInfo = roomMemberInfo;
        this.message = message;
        type = "Chat";
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
