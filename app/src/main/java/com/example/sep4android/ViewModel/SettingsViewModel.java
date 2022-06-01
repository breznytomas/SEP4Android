package com.example.sep4android.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sep4android.Model.User;

import com.example.sep4android.Repository.AuthentificationRepository;

public class SettingsViewModel extends AndroidViewModel {
    private AuthentificationRepository repository;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        repository = AuthentificationRepository.getInstance(application);
    }

    public void logout(){
        Log.d("SettingsVM", "HALO");
        repository.deleteUser(repository.getCurrentUser());
    }

}
