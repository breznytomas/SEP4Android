package com.example.sep4android.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Model.Board;
import com.example.sep4android.Model.User;
import com.example.sep4android.Repository.AuthentificationRepository;
import com.example.sep4android.Repository.BoardRepository;
import com.example.sep4android.Repository.Repository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private BoardRepository boardRepository;
    private MutableLiveData<Boolean> isLoading;
    private AuthentificationRepository authRepository;


    public HomeViewModel(@NonNull Application app) {
        super(app);
        isLoading = new MutableLiveData<>();
        boardRepository = BoardRepository.getInstance(app);

        authRepository = AuthentificationRepository.getInstance(app);
    }


    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<Board>> getBoardsLiveData(String email) {
        isLoading.setValue(true);
        boardRepository.getBoards(email);
        return boardRepository.getReceivedBoards();
    }

    public void logout(){
        authRepository.logout();
    }



    public User getCurrentUser() {
       return authRepository.getCurrentUser();
    }
}
