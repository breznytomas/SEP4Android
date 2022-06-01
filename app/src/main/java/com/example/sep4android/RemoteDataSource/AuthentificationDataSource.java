package com.example.sep4android.RemoteDataSource;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.sep4android.Model.User;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class AuthentificationDataSource {

    private MessageApi messageApi;
    public static User loggedUser;
    private Result.Success<User> resultSuccess;
    private Result.Error resultError;
    private String result;
    public static User userResponse;

    public User login(String email, String password) {
        messageApi = ServiceGenerator.getMessageApi();
        loggedUser = new User(email, password);
        Log.d("dupaDataSource", password);
            Call<User> call = messageApi.loginUser(new User(email, password));
            call.enqueue(new Callback<User>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        Log.d("Auth","Successful response");
                        if(response.code() == 200){
                            Log.d("Auth","Response code 200");
                            userResponse = response.body();
                            loggedUser = userResponse;
                        }
                        else if(response.code()==204){
                            Log.d("Auth","Response code 204");
                            userResponse = null;
                        }
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                   Log.d("Auth", "Failed API auth call");
                   userResponse=null;
                }
            });

            return userResponse;

    }

    public void logout() {
        loggedUser = null;
    }

    public void register(String email, String password){
        messageApi = ServiceGenerator.getMessageApi();
        Call<Void> call = messageApi.postUser(new User(email, password));
        call.enqueue(new Callback<Void>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("register", "User registered successfully");
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("register", "User not registered");
            }
        });
    }
}
