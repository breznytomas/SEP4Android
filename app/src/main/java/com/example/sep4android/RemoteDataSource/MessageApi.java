package com.example.sep4android.RemoteDataSource;

import com.example.sep4android.Model.Board;
import com.example.sep4android.Model.Event;
import com.example.sep4android.Model.User;

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

    @DELETE("api/Board/Disassociate")
    Call<ResponseBody> disassociateBoard(@Query("boardId") String boardId, @Query("userEmail") String userEmail);

    //resource requests
    @GET("api/{resource}/")
    Call<List<MessageResponse>> getMessage(@Path("resource") String resource, @Query("boardId") String boardId);

    @GET("api/{resource}/Average")
    Call<Double> getAverage (@Path("resource") String resource, @Query("boardId") String boardId,
                             @Query("timeFrom") String timeFrom, @Query("timeTo") String timeTo);

    @GET("api/{resource}/EventValues")
    Call<List<EventValue>> getEventValues(@Path("resource") String resource,
                                          @Query("boardId") String boardId, @Query("timeFrom") String timeFrom,
                                          @Query("timeTo") String timeTo);
    @GET("api/{resource}/TriggerRatio")
    Call<Double> getTriggerRatio (@Path("resource") String resource,
                                 @Query("boardId") String boardId, @Query("timeFrom") String timeFrom,
                                 @Query("timeTo") String timeTo);
    //event requests
    @GET("api/Event/")
    Call<List<Event>> getEvent(@Query("boardId") String boardID);
    @POST("api/Event/")
    Call<Event> postEvent(@Query("boardId") String boardID, @Body Event event);

    @PUT("api/Event/")
    Call<Event> putEvent(@Query("boardId") String boardID, @Body Event event);

    @DELETE("api/Event/")
    Call<ResponseBody> deleteEvent(@Query("boardId") String boardId, @Query("eventId") int eventId);

    //user requests
    @POST("api/User/")
    Call<Void> postUser(@Body User user);

    @POST("api/User/Login")
    Call<User> loginUser(@Body User user);

    @PUT("api/User")
    Call<ResponseBody> putUser(@Query("newPassword") String password, @Body User user);

    @DELETE("api/User/delete")
    Call<ResponseBody> deleteUser(@Body User user);


}

