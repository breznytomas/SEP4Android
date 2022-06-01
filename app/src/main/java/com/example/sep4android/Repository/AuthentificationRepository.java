package com.example.sep4android.Repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Model.User;
import com.example.sep4android.RemoteDataSource.MessageApi;
import com.example.sep4android.RemoteDataSource.ServiceGenerator;

import okhttp3.ResponseBody;
import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthentificationRepository {


    private static volatile AuthentificationRepository instance;
    private MessageApi messageApi;
    private User loggedUser = null;
    private int code=-999;



    // private constructor : singleton access
    private AuthentificationRepository(Application app) {
        this.messageApi = ServiceGenerator.getMessageApi();

    }

    public static AuthentificationRepository getInstance(Application app) {
        if (instance == null) {
            instance = new AuthentificationRepository(app);
        }
        return instance;
    }


    public void logout() {
        loggedUser = null;
    }


    public int login(String email, String password) {
        loggedUser = new User(email, password);
        Log.d("AuthRepo", "attempt to login");
        Call<User> call = messageApi.loginUser(new User(email, password));
        call.enqueue(new Callback<User>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Log.d("Auth","Successful response");
                    if(response.code() == 200){
                        Log.d("Auth","Response code 200");
                        code = response.code();
                        loggedUser = response.body();
                    }
                    else if(response.code()==204){
                        Log.d("Auth","Response code 204");
                        loggedUser = null;
                        code = response.code();
                    }
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Auth", "Failed API auth call");
                loggedUser=null;
            }
        });

        return code;

    }
    public void register(String email, String password){


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

    public void putUser(String email, String password){
        Call<ResponseBody> call = messageApi.putUser(new User(email, password));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("put", "User edited successfully");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("put", "User not edited properly");
            }
        });
    }

    public void deleteUser(User user){

        Call<ResponseBody> call = messageApi.deleteUser(user);
        Log.d("Auth-delete", "zizi buczek podjazd drugi");
        call.enqueue(new Callback<ResponseBody>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    Log.d("Auth-delete", "Deleted user successfully, code: " + response.code());
                    logout();
                } else if(response.code()==204){
                    Log.d("Auth-delete", "User is not documented, logging out..., code: " + response.code());
                    logout();
                } else{
                    Log.d("Auth-delete", String.valueOf(response.code()));
                    logout();
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Auth-delete","Failure when calling delete user");
            }
        });
    }

    public User getCurrentUser() {
        return loggedUser;
    }
}



/*
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

    public void deleteAccount(User user){
        Log.d("AuthRepoVM", "HALO");
        dataSource.deleteUser(user);
    }

    public void logout() {
        loggedUser = null;
        SharedPreferences.Editor editor;
editor = sharedPreferences.edit();
editor.clear();
            editor.apply();
 SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
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



*/



