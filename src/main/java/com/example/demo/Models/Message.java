package com.example.demo.Models;

public class Message {
    private int senderId;
    private int receiverId;
    private String msg;

    public Message(int senderId, int receiverId, String msg) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.msg = msg;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "Fra: " + senderId +
                ", til: " + receiverId +
                ", besked: '" + msg + '\'' +
                '}';
    }
}
