package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.RemoteDataSource.MessageResponse;
import com.example.sep4android.Repository.Repository;



import java.util.List;

public class MessageViewModel extends AndroidViewModel {
    private Repository repository;

    public MessageViewModel(Application app){
        super(app);
        repository = Repository.getInstance(app);
    }

    public LiveData<List<MessageResponse>> getAllMessages() {
        return repository.getReceivedMessages();
    }
}
