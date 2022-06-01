package com.example.sep4android.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sep4android.Model.User;
import com.example.sep4android.Repository.AuthentificationRepository;

public class MainViewModel extends AndroidViewModel {
    private AuthentificationRepository repository;
    private final String TAG = "login8)";
    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = AuthentificationRepository.getInstance(application);
    }

    public int login(String email, String password) {
        Log.d(TAG,"viewmodel");
        return repository.login(email, password);
    }
}
