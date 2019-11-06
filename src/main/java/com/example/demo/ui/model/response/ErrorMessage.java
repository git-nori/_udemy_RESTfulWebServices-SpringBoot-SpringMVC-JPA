package com.example.demo.ui.model.response;

import java.util.Date;

// エラーメッセージを格納するクラス(clientに返す)
public class ErrorMessage {
    private Date timestamp;
    private String message;

    public ErrorMessage() {}

    public ErrorMessage(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
