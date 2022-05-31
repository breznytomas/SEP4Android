package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Model.Board;
import com.example.sep4android.Repository.BoardRepository;

import java.util.List;

public class EventListViewModel extends AndroidViewModel {
    private BoardRepository boardRepository;
    private MutableLiveData<Boolean> isLoading;
    public EventListViewModel(@NonNull Application application) {
        super(application);
        isLoading = new MutableLiveData<>();
        boardRepository = BoardRepository.getInstance(application);
    }


    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<Board>> getBoardsLiveData(String email) {
        isLoading.setValue(true);
        boardRepository.getBoards(email);
        return boardRepository.getReceivedBoards();
    }



}
