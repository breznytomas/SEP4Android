package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.Repository.AuthentificationRepository;
import com.example.sep4android.Repository.BoardRepository;
import com.example.sep4android.Repository.Repository;
import com.example.sep4android.Shared.ValueTypes;


import java.util.List;

public class MessageViewModel extends AndroidViewModel {
    private Repository repository;
    private AuthentificationRepository authRepository;
    private BoardRepository boardRepository;

    public MessageViewModel(Application app){
        super(app);
        repository = Repository.getInstance(app);
        authRepository = AuthentificationRepository.getInstance(app);
        boardRepository = BoardRepository.getInstance(app);
    }

    public MutableLiveData<List<SensorValue>> getCarbonDioxideValueLiveData(String boardId) {
        repository.fetchCO2(boardId );
        return repository.getCarbonDioxideValueLiveData();
    }

    public MutableLiveData<List<SensorValue>> getHumidityValueLiveData(String boardId) {
        repository.fetchHumidity(boardId );
        return repository.getHumidityValueLiveData();
    }

    public MutableLiveData<List<SensorValue>> getLightValueLiveData(String boardId) {
        repository.fetchLight(boardId );
        return repository.getLightValueLiveData();
    }

    public MutableLiveData<List<SensorValue>> getTemperatureValueLiveData(String boardId) {
        repository.fetchTemperature(boardId);
        return repository.getTemperatureValueLiveData();
    }

    public void wipeData(){
        repository.wipeData();
    }

    public void dissociateBoard(String boardId){
         boardRepository.disassociateBoard(boardId, authRepository.getCurrentUser().getEmail());
    }
}
