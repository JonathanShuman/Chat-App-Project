package com.example.ap2_ex3.api;

public class OneMessage {

    private String message;

    private boolean iSend;

    public OneMessage(String message, boolean iSend) {
        this.message = message;
        this.iSend = iSend;
    }

    public void setiSend(boolean iSend) {
        this.iSend = iSend;
    }

    public boolean isiSend() {
        return iSend;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
