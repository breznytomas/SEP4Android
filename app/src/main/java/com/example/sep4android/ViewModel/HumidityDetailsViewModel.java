package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.Repository.Repository;
import com.example.sep4android.Shared.ValueTypes;

import java.util.List;

public class HumidityDetailsViewModel extends AndroidViewModel {
    private Repository repository;

    public HumidityDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public MutableLiveData<List<SensorValue>> getHumidityValueLiveData(String boardId) {
        repository.fetchValue(ValueTypes.Humidity, boardId);
        return repository.getHumidityValueLiveData();
    }
}
