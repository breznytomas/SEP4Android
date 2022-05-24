package com.example.sep4android.RemoteDataSource;

import android.util.Log;

import java.util.Date;

public class SensorValue {
    private int id;
    private Date timestamp;
    private String value;


    public SensorValue(int id, String timestamp, String value) {
        this.id = id;
        this.value = value;
        this.timestamp = new Date(Long.parseLong(timestamp) * 1000);

    }

    public int getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getValue() {
        return value;
    }
}
