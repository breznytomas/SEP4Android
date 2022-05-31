package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.RemoteDataSource.EventValue;
import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.Repository.Repository;
import com.example.sep4android.Shared.ValueTypes;

import java.util.List;

public class LightDetailsViewModel extends AndroidViewModel {
    private Repository repository;
    public LightDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public MutableLiveData<List<SensorValue>> getLightValueLiveData(String boardId) {
        repository.fetchLight(boardId );
        return repository.getLightValueLiveData();
    }

    public MutableLiveData<Double> getAverageLight(String boardId, String dateFrom, String dateTo){
        repository.fetchAverageLight(boardId, dateFrom, dateTo);
        return repository.getLightAverageLiveData();
    }
    public MutableLiveData<Double> getTriggerRatioLight(String boardId, String dateFrom, String dateTo){
        repository.fetchTriggerRatioLight(boardId, dateFrom, dateTo);
        return repository.getLightTriggerRatioLiveData();
    }

    public MutableLiveData<List<EventValue>> getEventValuesLight(
            String boardId, String dateFrom, String dateTo){
        repository.fetchEventValuesLight(boardId, dateFrom, dateTo);
        return repository.getLightEventValuesLiveData();
    }
}
