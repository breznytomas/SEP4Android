package com.example.sep4android.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.RemoteDataSource.MessageApi;
import com.example.sep4android.RemoteDataSource.MessageResponse;
import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.RemoteDataSource.ServiceGenerator;
import com.example.sep4android.Shared.ValueTypes;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Repository {

    private static Repository instance;
    private MessageApi messageApi;

    private final MutableLiveData<List<SensorValue>> CarbonDioxideValueLiveData;
    private final MutableLiveData<List<SensorValue>> HumidityValueLiveData;
    private final MutableLiveData<List<SensorValue>> LightValueLiveData;
    private final MutableLiveData<List<SensorValue>> TemperatureValueLiveData;

    private final String boardIdTest = "0004A30B00259D2C";
    private final String EMAIL_TEST = "policja@gov.pl";
    private String resourceTest = "CarbonDioxide";
    private String responseStr = " ";

    private LiveData<List<MessageResponse>> message;

    private Repository(Application app){
        messageApi =  ServiceGenerator.getMessageApi();

        CarbonDioxideValueLiveData = new MutableLiveData<>();
        HumidityValueLiveData = new MutableLiveData<>();
        LightValueLiveData = new MutableLiveData<>();
        TemperatureValueLiveData = new MutableLiveData<>();
    }
    private Repository(){
        messageApi =  ServiceGenerator.getMessageApi();

        CarbonDioxideValueLiveData = new MutableLiveData<>();
        HumidityValueLiveData = new MutableLiveData<>();
        LightValueLiveData = new MutableLiveData<>();
        TemperatureValueLiveData = new MutableLiveData<>();
    }


    public static synchronized Repository getInstance(Application app){
        if(instance==null){
            instance = new Repository(app);
        }
        return instance;
    }

    public static synchronized Repository getInstance(){
        if(instance==null){
            instance = new Repository();
        }
        return instance;
    }


    public MutableLiveData<List<SensorValue>> getCarbonDioxideValueLiveData() {
        return CarbonDioxideValueLiveData;
    }

    public MutableLiveData<List<SensorValue>> getHumidityValueLiveData() {
        return HumidityValueLiveData;
    }

    public MutableLiveData<List<SensorValue>> getLightValueLiveData() {
        return LightValueLiveData;
    }

    public MutableLiveData<List<SensorValue>> getTemperatureValueLiveData() {
        return TemperatureValueLiveData;
    }



    public void fetchTemperature(String boardId){
        Call<List<MessageResponse>> call = messageApi.getMessage(ValueTypes.Temperature.toString(), boardIdTest);
        call.enqueue(new Callback<List<MessageResponse>>(){
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response){
                if(response.isSuccessful()) {
                    List<MessageResponse> mr = response.body();
                    List<SensorValue> sv = new ArrayList<>();

                    for (MessageResponse messageResponse: mr) {
                        sv.add(messageResponse.getMessage());
                    }
                    sv.sort(Comparator.comparing(SensorValue::getTimestamp));

                            TemperatureValueLiveData.setValue(sv);

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

    public void fetchHumidity(String boardId){
        Call<List<MessageResponse>> call = messageApi.getMessage(ValueTypes.Humidity.toString(), boardIdTest);
        call.enqueue(new Callback<List<MessageResponse>>(){
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response){
                if(response.isSuccessful()) {
                    List<MessageResponse> mr = response.body();
                    List<SensorValue> sv = new ArrayList<>();

                    for (MessageResponse messageResponse: mr) {
                        sv.add(messageResponse.getMessage());
                    }
                    sv.sort(Comparator.comparing(SensorValue::getTimestamp));

                    HumidityValueLiveData.setValue(sv);

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

    public void fetchCO2(String boardId){
        Call<List<MessageResponse>> call = messageApi.getMessage(ValueTypes.CarbonDioxide.toString(), boardIdTest);
        call.enqueue(new Callback<List<MessageResponse>>(){
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response){
                if(response.isSuccessful()) {
                    List<MessageResponse> mr = response.body();
                    List<SensorValue> sv = new ArrayList<>();

                    for (MessageResponse messageResponse: mr) {
                        sv.add(messageResponse.getMessage());
                    }
                    sv.sort(Comparator.comparing(SensorValue::getTimestamp));

                    CarbonDioxideValueLiveData.setValue(sv);

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

    public void fetchLight(String boardId){
        Call<List<MessageResponse>> call = messageApi.getMessage(ValueTypes.Light.toString(), boardIdTest);
        call.enqueue(new Callback<List<MessageResponse>>(){
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response){
                if(response.isSuccessful()) {
                    List<MessageResponse> mr = response.body();
                    List<SensorValue> sv = new ArrayList<>();

                    for (MessageResponse messageResponse: mr) {
                        sv.add(messageResponse.getMessage());
                    }
                    sv.sort(Comparator.comparing(SensorValue::getTimestamp));

                    LightValueLiveData.setValue(sv);

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
