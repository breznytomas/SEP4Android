package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.RemoteDataSource.EventValue;
import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.Repository.Repository;

import java.util.List;

public class HumidityDetailsViewModel extends AndroidViewModel {
    private Repository repository;

    public HumidityDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public MutableLiveData<List<SensorValue>> getHumidityValueLiveData(String boardId) {
        repository.fetchHumidity(boardId);
        return repository.getHumidityValueLiveData();
    }

    public MutableLiveData<Double> getAverageHumidity(String boardId, String dateFrom, String dateTo){
        repository.fetchAverageHumidity(boardId, dateFrom, dateTo);
        return repository.getHumidityAverageLiveData();
    }
    public MutableLiveData<Double> getTriggerRatioHumidity(String boardId, String dateFrom, String dateTo){
        repository.fetchTriggerRatioHumidity(boardId, dateFrom, dateTo);
        return repository.getHumidityTriggerRatioLiveData();
    }

    public MutableLiveData<List<EventValue>> getEventValuesHumidity(
            String boardId, String dateFrom, String dateTo){
        repository.fetchAllEventValuesHumidity(boardId, dateFrom, dateTo);
        return repository.getHumidityEventValuesLiveData();
    }
}
