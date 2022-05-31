package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sep4android.Model.Event;
import com.example.sep4android.Repository.EventRepository;

public class EditEventViewModel extends AndroidViewModel {
    private EventRepository eventRepository;
    public EditEventViewModel(@NonNull Application application) {
        super(application);
        eventRepository = EventRepository.getInstance(application);
    }

    public void putEvent(String boardId, Event event){
        eventRepository.putEvent(boardId,event);
    }

    public void refreshRepo(String boardId){
        eventRepository.fetchEvents(boardId);
    }
}
