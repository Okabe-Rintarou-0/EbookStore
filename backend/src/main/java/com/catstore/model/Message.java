package com.catstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int status;
    private String message;
    private JSONObject data;
    private JSONArray arrayData;

    public Message(int status, String message, JSONArray arrayData) {
        this.status = status;
        this.message = message;
        this.arrayData = arrayData;
        this.data = null;
    }

    public Message(int status, String message, JSONObject data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.arrayData = null;
    }

    public Message(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
        this.arrayData = null;
    }
}
