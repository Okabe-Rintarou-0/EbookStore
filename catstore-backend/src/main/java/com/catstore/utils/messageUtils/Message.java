package com.catstore.utils.messageUtils;

import lombok.Data;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Data
public class Message {
    private final int status;
    private final String message;
    private final JSONObject data;
    private final JSONArray arrayData;

    Message(int status, String message, JSONArray arrayData) {
        this.status = status;
        this.message = message;
        this.arrayData = arrayData;
        this.data = null;
    }

    Message(int status, String message, JSONObject data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.arrayData = null;
    }

    Message(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
        this.arrayData = null;
    }
}
