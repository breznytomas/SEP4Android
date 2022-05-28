package com.example.sep4android.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4android.RemoteDataSource.AuthentificationDataSource;
import com.example.sep4android.Repository.AuthentificationRepository;

public class AuthVMFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthentificationViewModel.class)) {
            return (T) new AuthentificationViewModel(AuthentificationRepository.getInstance(new AuthentificationDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}