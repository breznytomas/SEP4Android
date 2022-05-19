package com.example.sep4android.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Model.Board;
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

public class Repository {

    private static Repository instance;
    private MessageApi messageApi;
    private final MutableLiveData<List<MessageResponse>> receivedMessages;
    private final MutableLiveData<List<Board>> receivedBoards;
    private final String boardIdTest = "0004A30B00259D2C";
    private final String EMAIL_TEST = "policja@gov.pl";
    private String resourceTest = "CarbonDioxide";
    private String responseStr = " ";

    private LiveData<List<MessageResponse>> message;

    private Repository(Application app){
        messageApi =  ServiceGenerator.getMessageApi();
        receivedMessages = new MutableLiveData<>();
        List<MessageResponse> newList = new ArrayList<>();
        receivedMessages.setValue(newList);
        receivedBoards = new MutableLiveData<>();

    }

    public static synchronized Repository getInstance(Application app){
        if(instance==null){
            instance = new Repository(app);
        }
        return instance;
    }

    public LiveData<List<MessageResponse>> getReceivedMessages(){
        return receivedMessages;
    }

    public LiveData<List<Board>> getReceivedBoards() {
        return receivedBoards;
    }

    public void getBoards(String userEmail){
        Call<List<Board>> call = messageApi.getBoard(userEmail);
        call.enqueue(new Callback<List<Board>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                receivedBoards.setValue(response.body());
                Log.d("Retrofit","Boards successfully received!");
                Log.d("Retrofit", new Gson().toJson(response.body()));
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                Log.i("Retrofit","Something went wrong when retrieving the board! :(");
            }
        });
    }

    public void getMessage(String resource,int boardId){
        Call<List<MessageResponse>> call = messageApi.getMessage(resourceTest, boardIdTest);
        call.enqueue(new Callback<List<MessageResponse>>(){
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response){
                if(response.isSuccessful()) {
//                    receivedMessages.setValue(response.body());

                    List<MessageResponse> mr = response.body();
                    receivedMessages.setValue(mr);

                    if (mr != null) {
                        for (MessageResponse message : mr) {
                            String content = "";
                            content += "User Id:  " + message.getId() + "\n";
                            content += "Timestamp:  " + message.getTimestamp() + "\n";
                            content += "Message:  " + message.getMessage() + "\n\n";

                            Log.d("List", content);
                        }
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
