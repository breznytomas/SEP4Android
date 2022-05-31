package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Model.Event;
import com.example.sep4android.Repository.EventRepository;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    EventRepository eventRepository;
    public EventViewModel(@NonNull Application application) {
        super(application);
        eventRepository = EventRepository.getInstance(application);
    }

    public LiveData<List<Event>> getEvents(String boardId){
        eventRepository.fetchEvents(boardId);
        return eventRepository.getEventsLiveData();
    }

    public void putEvent(String boardId, Event event){
        eventRepository.putEvent(boardId, event);
    }

    public void deleteEvent(String boardId, int eventId){
        eventRepository.deleteEvent(boardId, eventId);
    }

    public void wipeData(){
        eventRepository.wipeData();
    }

}
