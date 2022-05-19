package com.example.sep4android.RemoteDataSource;

import com.example.sep4android.Model.Board;
import com.example.sep4android.Model.User;
import com.example.sep4android.Shared.Message;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MessageApi {


    //board requests
    @PUT("api/Board/")
    Call<ResponseBody> putBoard(@Query("boardId") String boardId, @Query("userEmail") String userEmail);

    @GET("api/Board/")
    Call<List<Board>> getBoard(@Query("userEmail") String userEmail);

    @POST("api/Board/")
    Call<ResponseBody> postBoard(@Body Board board);

    @DELETE("api/Board/")
    Call<ResponseBody> deleteBoard(@Query("boardId") String boardId);

    @GET("api/{resource}/")
    Call<List<MessageResponse>> getMessage(@Path("resource") String resource, @Query("boardId") String boardId);

    //user requests
    @POST("api/User/")
    Call<ResponseBody> postUser(@Body User user);

    @POST("api/User/Login")
    Call<User> loginUser(@Body User user);
}

