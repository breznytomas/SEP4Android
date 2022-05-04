package com.example.sep4android.RemoteDataSource;

import com.example.sep4android.Shared.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MessageApi {
    @GET("api/{resource}/")
    Call<List<MessageResponse>> getMessage(@Path("resource") String resource, @Query("boardId") int boardID);}

