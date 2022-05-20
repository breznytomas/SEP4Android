package com.example.sep4android.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Board {

    @SerializedName("Id")
    private String boardId;
    @SerializedName("Name")
    private String description;
    private List<Event> eventList;
    @SerializedName("Description")
    private String name;

    public Board(String description, String name) {
        this.description = description;
        this.name = name;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getDescription() {
        return description;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public String getName() {
        return name;
    }
}
