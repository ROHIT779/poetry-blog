package com.poetryblog.poetryblog_service.model;

public class Message {
    private String message;
    private String info;

    public Message() {}

    public Message(String message, String info) {
        this.message = message;
        this.info = info;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
