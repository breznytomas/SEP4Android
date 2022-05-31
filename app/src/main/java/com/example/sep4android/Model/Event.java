package com.example.sep4android.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;

public class Event {
    @SerializedName("Id")
    private int eventId;
    @SerializedName("Name")
    private String name;
    @SerializedName("Type")
    private int type;
    @SerializedName("Top")
    private float top;
    @SerializedName("Bottom")
    private float bottom;
    @SerializedName("triggerList")
    private Collection<Trigger> triggerList;

    public Event(String name, int type, float top, float bottom) {
        this.name = name;
        this.type = type;
        this.top = top;
        this.bottom = bottom;
    }

    public Event(int eventId, String name, int type, float top, float bottom) {
        this.eventId = eventId;
        this.name = name;
        this.type = type;
        this.top = top;
        this.bottom = bottom;
        this.triggerList = new ArrayList<>();
    }

    public Event() {

    }

    public int getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public float getTop() {
        return top;
    }

    public float getBottom() {
        return bottom;
    }
}
