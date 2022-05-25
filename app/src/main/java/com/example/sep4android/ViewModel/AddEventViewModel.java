package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Model.Board;
import com.example.sep4android.Model.Event;
import com.example.sep4android.Repository.BoardRepository;
import com.example.sep4android.Repository.EventRepository;

import java.util.List;

public class AddEventViewModel extends AndroidViewModel {
    EventRepository eventRepository;
    BoardRepository boardRepository;
    public AddEventViewModel(@NonNull Application application) {
        super(application);
        eventRepository = EventRepository.getInstance(application);
        boardRepository = BoardRepository.getInstance(application);
    }
    public void  postEvent(String boardId, Event event){
        eventRepository.postEvent(boardId, event);
    }

    public LiveData<List<Board>> getBoardsLiveData(String email) {
        boardRepository.getBoards(email);
        return boardRepository.getReceivedBoards();
    }
    public LiveData<List<Event>> getEventsLiveData(String boardID){
        eventRepository.fetchEvents(boardID);
        return eventRepository.getEventsLiveData();
    }
}
