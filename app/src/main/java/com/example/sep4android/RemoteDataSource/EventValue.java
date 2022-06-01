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

    public int getId() {
        return id;
    }

    public void set(EventValue eventValue){
        this.id  = eventValue.id;
        this.exceededBy = eventValue.exceededBy;
        this.value = eventValue.value;
        this.measureDate = eventValue.measureDate;
        this.triggeredFrom = eventValue.triggeredFrom;
    }

    @Override
    public String toString() {
        return "EventValue{" +
                "id=" + id +
                ", measureDate='" + measureDate + '\'' +
                ", value=" + value +
                ", triggeredFrom='" + triggeredFrom + '\'' +
                ", exceededBy=" + exceededBy +
                '}';
    }
}
