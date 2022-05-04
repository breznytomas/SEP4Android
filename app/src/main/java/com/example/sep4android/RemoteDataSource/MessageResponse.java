package com.example.sep4android.RemoteDataSource;

import com.example.sep4android.Shared.Message;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageResponse {
    @SerializedName("Id")
    private int id;
    @SerializedName("Timestamp")
    private String timestamp;
    @SerializedName("Value")
    private String value;

    public Message getMessage(){
        return new Message(id, timestamp, value);
    }
}
