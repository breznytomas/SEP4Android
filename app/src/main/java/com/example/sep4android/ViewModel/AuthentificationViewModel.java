package com.example.sep4android.ViewModel;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sep4android.Model.User;
import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.Result;
import com.example.sep4android.Repository.AuthentificationRepository;
import com.example.sep4android.Repository.Repository;



public class AuthentificationViewModel extends ViewModel {



    private MutableLiveData<AuthResult> authResult = new MutableLiveData<>();
    private AuthentificationRepository authentificationRepository;

    AuthentificationViewModel(AuthentificationRepository authentificationRepository) {
        this.authentificationRepository = authentificationRepository;
    }



    public LiveData<AuthResult> getAuthResult() {
        return authResult;
    }

    public void login(String email, String password) {
        // can be launched in a separate asynchronous job
        Result<User> result = authentificationRepository.login(email, password);
    Log.d("dupaVM", email+password);
        if (result instanceof Result.Success) {
            User data = ((Result.Success<User>) result).getData();
            authResult.setValue(new AuthResult(new LoggedUserView(data.getEmail())));
            Log.d("duparesult",data.getEmail());
        } else {
            authResult.setValue(new AuthResult(R.string.login_failed));
        }
    }

    public void register(String email, String password){
        authentificationRepository.register(email, password);
    }




}