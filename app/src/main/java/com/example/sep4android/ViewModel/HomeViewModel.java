package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Model.Board;
import com.example.sep4android.Repository.Repository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Board>> boardsLiveData;
    private MutableLiveData<Boolean> isLoading;

    public HomeViewModel(@NonNull Application app) {
        super(app);
        isLoading = new MutableLiveData<>();
        boardsLiveData = new MutableLiveData<>();

        repository = Repository.getInstance(app);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<Board>> getBoardsLiveData(String email) {
        isLoading.setValue(true);
        repository.getBoards(email);
        boardsLiveData = repository.getReceivedBoards();
        return boardsLiveData;
    }
}
