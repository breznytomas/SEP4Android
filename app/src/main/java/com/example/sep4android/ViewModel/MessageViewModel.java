package com.example.sep4android.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.sep4android.Repository.MessageRepository;

public class MessageViewModel extends ViewModel {
    private MessageRepository repository;

    public MessageViewModel(){
        repository = MessageRepository.getInstance();
    }
}
