package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sep4android.Model.User;
import com.example.sep4android.Repository.AuthentificationRepository;

public class ForgotPasswordViewModel extends AndroidViewModel {
    private AuthentificationRepository authentificationRepository;
    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
        authentificationRepository = AuthentificationRepository.getInstance(application);
    }

    public void resetPassword(String newPassword, User user){
        authentificationRepository.putUser(newPassword,user);
    }
}
