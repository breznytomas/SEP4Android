package com.example.sep4android.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.RemoteDataSource.MessageApi;
import com.example.sep4android.RemoteDataSource.MessageResponse;
import com.example.sep4android.RemoteDataSource.ServiceGenerator;
import com.example.sep4android.Shared.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MessageRepository {

    private static MessageRepository instance;
    private final MutableLiveData<List<Message>> receivedMessages;
    private  int boardIdTest = 7;
    private String resource = "CarbonDioxide";

    private MessageRepository(){
        receivedMessages = new MutableLiveData<>();
    }

    public static synchronized MessageRepository getInstance(){
        if(instance==null){
            instance = new MessageRepository();
        }

        return instance;
    }

    public LiveData<List<Message>> getReceivedMessages(){
        return receivedMessages;
    }

    public String getLastValueFromReceivedMessageInString(){
        List<Message> list = receivedMessages.getValue();
        String toReturn = "";
        if(list !=null){
            for(Message message : list){
                toReturn += message + "\n";
            }
        }
        return toReturn;
    }

    public void getMessage(int boardId){
        MessageApi messageApi = ServiceGenerator.getMessageApi();
        Call<MessageResponse> call = messageApi.getMessage(resource, boardIdTest);

        call.enqueue(new Callback<MessageResponse>(){
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response){
                if(response.isSuccessful()) {
                    receivedMessages.setValue(response.body().getMessages());
                    System.out.println("Messages successfully received!");
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
    }
}
