package com.catstore.decoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.catstore.model.*;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class WebsocketMessageDecoder implements Decoder.Text<WebsocketMessage> {
    JSONObject json;

    @Override
    public WebsocketMessage decode(String s) {
        switch (json.getString("type")) {
            case "Action":
                return json.toJavaObject(ActionMessage.class);
            case "Chat":
                return json.toJavaObject(ChatMessage.class);
            case "Info":
                return json.toJavaObject(InfoMessage.class);
            case "PingPong":
                return json.toJavaObject(PingPongMessage.class);
        }
        return null;
    }

    @Override
    public boolean willDecode(String s) {
        json = JSON.parseObject(s);
        if (json.containsKey("type")) {
            switch (json.getString("type")) {
                case "Action":
                    return json.containsKey("action");
                case "Chat":
                    return json.containsKey("roomMemberInfo") && json.containsKey("message");
                case "Info":
                    return json.containsKey("roomMemberInfo") && json.containsKey("info");
                case "PingPong":
                    return json.containsKey("timestamp");
            }
        }
        return false;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
