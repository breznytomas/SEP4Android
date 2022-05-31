package com.example.sep4android.RemoteDataSource;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.sep4android.Model.User;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthentificationDataSource {
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    SharedPreferences sharedPreferences;
    private MessageApi messageApi;
    public static User loggedUser;

    public Result<User> login(String email, String password) {
        messageApi = ServiceGenerator.getMessageApi();
        loggedUser = new User(email, password);
        Log.d("dupaDataSource", email+password);
            Call<User> call = messageApi.loginUser(loggedUser);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    new Result.Success<>(loggedUser);
                    Log.d("dupa",""+response.toString()+"\n"+loggedUser.toString());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    new Result.Error(new IOException("Error logging in", t));
                    Log.d("dupa", ""+t);
                }
            });

            return new Result.Success<>(new User(email, password));

    }

    public void logout() {
        loggedUser = null;
    }

    public void register(String email, String password){
        messageApi = ServiceGenerator.getMessageApi();
        Call<Void> call = messageApi.postUser(new User(email, password));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("register", "User registered successfully");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("register", "User not registered");
            }
        });
    }
}
