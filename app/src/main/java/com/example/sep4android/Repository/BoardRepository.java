package com.example.sep4android.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Model.Board;
import com.example.sep4android.RemoteDataSource.BoardResponse;
import com.example.sep4android.RemoteDataSource.MessageApi;
import com.example.sep4android.RemoteDataSource.ServiceGenerator;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class BoardRepository {
    private static BoardRepository instance;
    private MessageApi messageApi;
    private final MutableLiveData<List<Board>> receivedBoards;
    private final MutableLiveData<String> boardCheck;
    private final MutableLiveData<String> putResult;

    public static synchronized BoardRepository getInstance(Application app) {
        if (instance == null) {
            instance = new BoardRepository(app);
        }
        return instance;
    }

    public BoardRepository(Application app) {
        this.messageApi = ServiceGenerator.getMessageApi();
        this.receivedBoards = new MutableLiveData<>();
        boardCheck = new MutableLiveData<>();
        putResult = new MutableLiveData<>();
    }

    public LiveData<List<Board>> getReceivedBoards() {
        return receivedBoards;
    }

    public void getBoards(String userEmail) {
        Call<List<Board>> call = messageApi.getBoard(userEmail);
        call.enqueue(new Callback<List<Board>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                receivedBoards.setValue(response.body());
                Log.d("Retrofit", "Boards successfully received!");
                Log.d("Retrofit", new Gson().toJson(response.body()));
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong when retrieving the board! :(");
            }
        });
    }

    public MutableLiveData<String> postBoard(Board board) {
        Call<ResponseBody> call = messageApi.postBoard(board);
        call.enqueue(new Callback<ResponseBody>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("Retrofit_postBoard", "Board successfully added, code "+ response.code());
                if (response.code() ==200) {
                    boardCheck.setValue("Board added successfully");
                }else{
                    boardCheck.setValue("Error when adding a board");
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong when posting the board! :(");
                boardCheck.setValue("Error when adding a board");
            }
        });
        return boardCheck;
    }

    public MutableLiveData<String> putBoard(String boardId, String userEmail) {
        Call<ResponseBody> call = messageApi.putBoard(boardId, userEmail);
        call.enqueue(new Callback<ResponseBody>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                getBoards(userEmail); //update LiveData
                if(response.code()==200){
                    Log.i("Retrofit_putBoard", "Board successfully assigned, code" + response.code());
                    putResult.setValue("Board successfully assigned");
                }
                else{
                    putResult.setValue("Error when assigning the board");
                }

            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong when putting the board! :(");
                putResult.setValue("Error when assigning the board");
            }
        });
        return putResult;
    }
}
