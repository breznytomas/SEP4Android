package com.example.sep4android.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.RemoteDataSource.MessageApi;
import com.example.sep4android.RemoteDataSource.MessageResponse;
import com.example.sep4android.RemoteDataSource.ServiceGenerator;
import com.example.sep4android.Shared.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MessageRepository {

    private static MessageRepository instance;
    private final MutableLiveData<List<MessageResponse>> receivedMessages;
    private  int boardIdTest = 7;
    private String resource = "CarbonDioxide";
    private String responseStr = " ";

    private MessageRepository(){
        receivedMessages = new MutableLiveData<>();
    }

    public static synchronized MessageRepository getInstance(){
        if(instance==null){
            instance = new MessageRepository();
        }

        return instance;
    }

    public LiveData<List<MessageResponse>> getReceivedMessages(){
        return receivedMessages;
    }


    public void getMessage(int boardId){
        MessageApi messageApi = ServiceGenerator.getMessageApi();
        Call<List<MessageResponse>> call = messageApi.getMessage(resource, boardIdTest);

        call.enqueue(new Callback<List<MessageResponse>>(){
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response){
                if(response.isSuccessful()) {

                    receivedMessages.setValue(response.body());
                    Log.d("Retrofit","Messages successfully received!");
                    Log.d("Retrofit", new Gson().toJson(response.body()));
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<MessageResponse>> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
    }
}
