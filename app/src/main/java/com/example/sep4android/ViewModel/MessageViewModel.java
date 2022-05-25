package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

import com.example.sep4android.RemoteDataSource.MessageResponse;
import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.Repository.Repository;
import com.example.sep4android.ValueTypes;


import java.util.List;

public class MessageViewModel extends AndroidViewModel {
    private Repository repository;

    public MessageViewModel(Application app){
        super(app);
        repository = Repository.getInstance(app);
    }

    public MutableLiveData<List<SensorValue>> getCarbonDioxideValueLiveData(String boardId) {
        repository.fetchValue(ValueTypes.CarbonDioxide, boardId );
        return repository.getCarbonDioxideValueLiveData();
    }

    public MutableLiveData<List<SensorValue>> getHumidityValueLiveData(String boardId) {
        repository.fetchValue(ValueTypes.Humidity, boardId );
        return repository.getHumidityValueLiveData();
    }

    public MutableLiveData<List<SensorValue>> getLightValueLiveData(String boardId) {
        repository.fetchValue(ValueTypes.Light, boardId );
        return repository.getLightValueLiveData();
    }

    public MutableLiveData<List<SensorValue>> getTemperatureValueLiveData(String boardId) {
        repository.fetchValue(ValueTypes.Temperature, boardId );
        return repository.getTemperatureValueLiveData();
    }
}
