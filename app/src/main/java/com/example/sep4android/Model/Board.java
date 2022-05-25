package com.example.sep4android.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Board {

    @SerializedName("Id")
    private String boardId;
    @SerializedName("Name")
    private String name;
    private List<Event> eventList;
    @SerializedName("Description")
    private String description;

    public Board(String id, String description, String name) {
        this.boardId = id;
        this.description = description;
        this.name = name;
        eventList = new ArrayList<>();
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
