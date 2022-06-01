package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.RemoteDataSource.EventValue;
import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.Repository.EventRepository;
import com.example.sep4android.Repository.Repository;

import java.util.List;

public class Co2DetailsViewModel extends AndroidViewModel {

    // TODO rename it
    private Repository repository;
    private EventRepository eventRepository;

    public Co2DetailsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        eventRepository = EventRepository.getInstance(application);
    }

    public MutableLiveData<List<SensorValue>> getCO2ValueLiveData(String boardId) {
        repository.fetchCO2(boardId);
        return repository.getCarbonDioxideValueLiveData();
    }

    public MutableLiveData<Double> getAverageCO2(String boardId, String dateFrom, String dateTo){
        repository.fetchAverageCO2(boardId, dateFrom, dateTo);
        return repository.getCarbonDioxideAverageLiveData();
    }
    public MutableLiveData<Double> getTriggerRatioCO2(String boardId, String dateFrom, String dateTo){
        repository.fetchTriggerRatioCO2(boardId, dateFrom, dateTo);
        return repository.getCarbonDioxideTriggerRatioLiveData();
    }

    public MutableLiveData<List<EventValue>> getEventValuesCO2(
            String boardId, String dateFrom, String dateTo){
        repository.fetchAllEventValuesCO2(boardId, dateFrom, dateTo);
        return repository.getCarbonDioxideEventValuesLiveData();
    }



}
