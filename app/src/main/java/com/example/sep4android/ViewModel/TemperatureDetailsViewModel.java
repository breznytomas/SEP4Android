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

public class TemperatureDetailsViewModel extends AndroidViewModel {
    private Repository repository;

    public TemperatureDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);

    }
    public MutableLiveData<List<SensorValue>> getTemperatureValueLiveData(String boardId) {
        repository.fetchTemperature(boardId );
        return repository.getTemperatureValueLiveData();
    }

    public MutableLiveData<Double> getAverageTemperature(String boardId, String dateFrom, String dateTo){
        repository.fetchAverageTemperature(boardId, dateFrom, dateTo);
        return repository.getTemperatureAverageLiveData();
    }
    public MutableLiveData<Double> getTriggerRatioTemperature(String boardId, String dateFrom, String dateTo){
        repository.fetchTriggerRatioTemperature(boardId, dateFrom, dateTo);
        return repository.getTemperatureTriggerRatioLiveData();
    }

    public MutableLiveData<List<EventValue>> getEventValuesTemperature(
            String boardId, String dateFrom, String dateTo){
        repository.fetchEventValuesTemperature(boardId, dateFrom, dateTo);
        return repository.getTemperatureEventValuesLiveData();
    }
}
