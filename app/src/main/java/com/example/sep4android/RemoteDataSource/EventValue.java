package com.example.sep4android.RemoteDataSource;

import com.google.gson.annotations.SerializedName;

public class EventValue {
    @SerializedName("id")
    private int id;
    @SerializedName("measureDate")
    private String measureDate;
    @SerializedName("value")
    private int value;
    @SerializedName("triggeredFrom")
    private String triggeredFrom;
    @SerializedName("exceededBy")
    private int exceededBy;

    public String getMeasureDate() {
        return measureDate;
    }

    public int getValue() {
        return value;
    }

    public String getTriggeredFrom() {
        return triggeredFrom;
    }

    public int getExceededBy() {
        return exceededBy;
    }
}
