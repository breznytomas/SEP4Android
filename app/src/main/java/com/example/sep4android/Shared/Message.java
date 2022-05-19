package com.example.sep4android.Shared;

public class Message {
    private int id;
    private String timestamp;
    private String value;

    public Message(int id, String timestamp, String value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", timestamp='" + timestamp + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
