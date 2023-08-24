package com.example.ap2_ex3.api;

public class myMessage {
    private String id;
    private String created;
    private Sender sender;
    private String content;

    public myMessage(String id, String created, Sender sender, String content) {
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}