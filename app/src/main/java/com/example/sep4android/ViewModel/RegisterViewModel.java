package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sep4android.Model.User;
import com.example.sep4android.Repository.AuthentificationRepository;

public class RegisterViewModel extends AndroidViewModel {
    AuthentificationRepository repository;
    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = AuthentificationRepository.getInstance(application);
    }

    public void register(String email, String password) {
        repository.register(email, password);
    }
}
