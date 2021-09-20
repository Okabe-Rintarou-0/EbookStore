package com.catstore.encoder;

import com.catstore.model.WebsocketMessage;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class WebsocketMessageEncoder implements Encoder.Text<WebsocketMessage> {
    @Override
    public String encode(WebsocketMessage message) {
        return message.toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
