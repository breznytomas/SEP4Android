package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.Repository.Repository;
import com.example.sep4android.Shared.ValueTypes;

import java.util.List;

public class Co2DetailsViewModel extends AndroidViewModel {

    // TODO rename it
    private Repository repository;

    public Co2DetailsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public MutableLiveData<List<SensorValue>> getCO2ValueLiveData(String boardId) {
        repository.fetchCO2(boardId);
        return repository.getCarbonDioxideValueLiveData();
    }


}
