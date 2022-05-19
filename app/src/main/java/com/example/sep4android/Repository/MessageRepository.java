package com.example.sep4android.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.RemoteDataSource.MessageApi;
import com.example.sep4android.RemoteDataSource.MessageResponse;
import com.example.sep4android.RemoteDataSource.ServiceGenerator;
import com.google.gson.Gson;

import java.util.ArrayList;
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

    private LiveData<List<MessageResponse>> message;

    private MessageRepository(){
        receivedMessages = new MutableLiveData<>();
        List<MessageResponse> newList = new ArrayList<>();
        receivedMessages.setValue(newList);
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
//                    receivedMessages.setValue(response.body());

                    List<MessageResponse> mr = response.body();
                    receivedMessages.setValue(mr);

                    for (MessageResponse message : mr) {
                        String content = "";
                        content += "User Id:  " + message.getId() + "\n";
                        content += "Timestamp:  " + message.getTimestamp() + "\n";
                        content += "Message:  " + message.getMessage() + "\n\n";

                        Log.d("List", content);
                    }

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
