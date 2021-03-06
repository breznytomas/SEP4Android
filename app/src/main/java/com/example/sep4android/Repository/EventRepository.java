package com.example.sep4android.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Model.Event;
import com.example.sep4android.RemoteDataSource.MessageApi;
import com.example.sep4android.RemoteDataSource.ServiceGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class EventRepository {
    private static EventRepository instance;
    private MessageApi messageApi;
    private final MutableLiveData<List<Event>> eventsLiveData;



    public static synchronized EventRepository getInstance(Application app){
        if(instance==null){
            instance = new EventRepository(app);
        }
        return instance;
    }

    private EventRepository(Application app){
        messageApi =  ServiceGenerator.getMessageApi();
        eventsLiveData = new MutableLiveData<>();
    }

    public void wipeData(){
        List<Event> newList = new ArrayList<>();
        eventsLiveData.setValue(newList);
    }

    public LiveData<List<Event>> getEventsLiveData() {
        return eventsLiveData;
    }

    public void postEvent(String boardId, Event event){
        Call<Event> call = messageApi.postEvent(boardId, event);
        call.enqueue(new Callback<Event>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                    if(!event.equals(response.body())){
                        //TODO do something here
                        Log.i("Retrofit","Something went wrong when posting the event! :(");
                    }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.i("Retrofit/Event","Something went wrong when posting the event! :(");
            }
        });
    }

    public void putEvent(String boardId, Event event){
        Call<Event> call = messageApi.putEvent(boardId, event);
        call.enqueue(new Callback<Event>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                Log.d("Retrofit_event",response.message());
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.i("Retrofit","Something went wrong when updating the event! :(");
            }
        });
    }

    public void deleteEvent(String boardId, int eventId){
        Call<ResponseBody> call = messageApi.deleteEvent(boardId, eventId);
        call.enqueue(new Callback<ResponseBody>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Retrofit","Something went wrong when deleting the event! :(");
            }
        });
    }

    public void fetchEvents(String boardId){

        Call<List<Event>> call = messageApi.getEvent(boardId);
        call.enqueue(new Callback<List<Event>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                eventsLiveData.setValue(response.body());
                Log.d("AAA", new Gson().toJson(response.body()));
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.i("Retrofit","Something went wrong when retrieving the events! :(");
            }
        });
    }
}
