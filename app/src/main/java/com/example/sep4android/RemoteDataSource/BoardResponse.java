package com.example.sep4android.RemoteDataSource;

import com.example.sep4android.Model.Board;
import com.example.sep4android.Model.Event;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BoardResponse {
    @SerializedName("Id")
    private String boardId;
    @SerializedName("Name")
    private String name;
    private List<Event> eventList;
    @SerializedName("Description")
    private String description;

    public Board getBoard(){
        return new Board(boardId, name,description);
    }
}
