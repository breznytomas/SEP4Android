package com.example.sep4android.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Model.Board;
import com.example.sep4android.RemoteDataSource.AuthentificationDataSource;
import com.example.sep4android.Repository.AuthentificationRepository;
import com.example.sep4android.Repository.BoardRepository;
import com.example.sep4android.Repository.Repository;
import com.example.sep4android.View.ViewEventsListActivity;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private BoardRepository boardRepository;
    private MutableLiveData<Boolean> isLoading;
    private AuthentificationRepository authRepository;
    private Repository repository;

    public HomeViewModel(@NonNull Application app) {
        super(app);
        isLoading = new MutableLiveData<>();
        boardRepository = BoardRepository.getInstance(app);
        repository = Repository.getInstance(app);
    }


    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<Board>> getBoardsLiveData(String email) {
        isLoading.setValue(true);
        boardRepository.getBoards(email);
        return boardRepository.getReceivedBoards();
    }

    public void logout(AuthentificationDataSource dataSource){
        authRepository = AuthentificationRepository.getInstance(dataSource);
        authRepository.logout();
    }

    public void startNotificationWork(Context context, String boardId, String dimDateFrom, String dimDateTo){
        repository.createNotificationWorkerLight(context, boardId, dimDateFrom, dimDateTo);
        repository.createNotificationWorkerTemperature(context, boardId, dimDateFrom, dimDateTo);
        repository.createNotificationWorkerHumidity(context, boardId, dimDateFrom, dimDateTo);
        repository.createNotificationWorkerCO2(context, boardId, dimDateFrom, dimDateTo);
    }
}
