package com.example.sep4android.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.RemoteDataSource.MessageResponse;
import com.example.sep4android.Repository.MessageRepository;



import java.util.List;

public class MessageViewModel extends ViewModel {
    private MessageRepository repository;

    public MessageViewModel(){
        repository = MessageRepository.getInstance();
    }

    public LiveData<List<MessageResponse>> getAllMessages() {
        return repository.getReceivedMessages();
    }
}
