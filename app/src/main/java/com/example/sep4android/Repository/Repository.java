package com.example.sep4android.Repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.sep4android.Model.User;
import com.example.sep4android.RemoteDataSource.EventValue;
import com.example.sep4android.RemoteDataSource.MessageApi;
import com.example.sep4android.RemoteDataSource.MessageResponse;
import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.RemoteDataSource.ServiceGenerator;
import com.example.sep4android.Shared.ValueTypes;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    private final MutableLiveData<Double> CarbonDioxideAverageLiveData;
    private final MutableLiveData<Double> CarbonDioxideTriggerRatioLiveData;
    private final MutableLiveData<List<EventValue>> CarbonDioxideEventValuesLiveData;

    private final MutableLiveData<Double> HumidityAverageLiveData;
    private final MutableLiveData<Double> HumidityTriggerRatioLiveData;
    private final MutableLiveData<List<EventValue>> HumidityEventValuesLiveData;

    private final MutableLiveData<Double> LightAverageLiveData;
    private final MutableLiveData<Double> LightTriggerRatioLiveData;
    private final MutableLiveData<List<EventValue>> LightEventValuesLiveData;

    private final MutableLiveData<Double> TemperatureAverageLiveData;
    private final MutableLiveData<Double> TemperatureTriggerRatioLiveData;
    private final MutableLiveData<List<EventValue>> TemperatureEventValuesLiveData;
    private EventValue temp;






    private Repository(Application app){
        messageApi =  ServiceGenerator.getMessageApi();

        CarbonDioxideValueLiveData = new MutableLiveData<>();
        HumidityValueLiveData = new MutableLiveData<>();
        LightValueLiveData = new MutableLiveData<>();
        TemperatureValueLiveData = new MutableLiveData<>();

        CarbonDioxideAverageLiveData = new MutableLiveData<>();
        CarbonDioxideTriggerRatioLiveData = new MutableLiveData<>();
        CarbonDioxideEventValuesLiveData = new MutableLiveData<>();

        HumidityAverageLiveData = new MutableLiveData<>();
        HumidityTriggerRatioLiveData = new MutableLiveData<>();
        HumidityEventValuesLiveData = new MutableLiveData<>();

        LightAverageLiveData = new MutableLiveData<>();
        LightTriggerRatioLiveData = new MutableLiveData<>();
        LightEventValuesLiveData = new MutableLiveData<>();

        TemperatureAverageLiveData = new MutableLiveData<>();
        TemperatureTriggerRatioLiveData = new MutableLiveData<>();
        TemperatureEventValuesLiveData = new MutableLiveData<>();


    }
    private Repository(){
        messageApi =  ServiceGenerator.getMessageApi();

        CarbonDioxideValueLiveData = new MutableLiveData<>();
        HumidityValueLiveData = new MutableLiveData<>();
        LightValueLiveData = new MutableLiveData<>();
        TemperatureValueLiveData = new MutableLiveData<>();

        CarbonDioxideAverageLiveData = new MutableLiveData<>();
        CarbonDioxideTriggerRatioLiveData = new MutableLiveData<>();
        CarbonDioxideEventValuesLiveData = new MutableLiveData<>();

        HumidityAverageLiveData = new MutableLiveData<>();
        HumidityTriggerRatioLiveData = new MutableLiveData<>();
        HumidityEventValuesLiveData = new MutableLiveData<>();

        LightAverageLiveData = new MutableLiveData<>();
        LightTriggerRatioLiveData = new MutableLiveData<>();
        LightEventValuesLiveData = new MutableLiveData<>();

        TemperatureAverageLiveData = new MutableLiveData<>();
        TemperatureTriggerRatioLiveData = new MutableLiveData<>();
        TemperatureEventValuesLiveData = new MutableLiveData<>();
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

    public void wipeData(){
        List<SensorValue> newList =  new ArrayList<>();
        CarbonDioxideValueLiveData.setValue(newList);
        HumidityValueLiveData.setValue(newList);
        LightValueLiveData.setValue(newList);
        TemperatureValueLiveData.setValue(newList);
    }

    public MutableLiveData<Double> getCarbonDioxideAverageLiveData() {
        return CarbonDioxideAverageLiveData;
    }
    public MutableLiveData<Double> getCarbonDioxideTriggerRatioLiveData(){
        return CarbonDioxideTriggerRatioLiveData;
    }

    public MutableLiveData<List<EventValue>> getCarbonDioxideEventValuesLiveData() {
        return CarbonDioxideEventValuesLiveData;
    }

    public MutableLiveData<Double> getHumidityAverageLiveData() {
        return HumidityAverageLiveData;
    }

    public MutableLiveData<Double> getHumidityTriggerRatioLiveData() {
        return HumidityTriggerRatioLiveData;
    }

    public MutableLiveData<List<EventValue>> getHumidityEventValuesLiveData() {
        return HumidityEventValuesLiveData;
    }

    public MutableLiveData<Double> getLightAverageLiveData() {
        return LightAverageLiveData;
    }

    public MutableLiveData<Double> getLightTriggerRatioLiveData() {
        return LightTriggerRatioLiveData;
    }

    public MutableLiveData<Double> getTemperatureAverageLiveData() {
        return TemperatureAverageLiveData;
    }

    public MutableLiveData<Double> getTemperatureTriggerRatioLiveData() {
        return TemperatureTriggerRatioLiveData;
    }

    public MutableLiveData<List<EventValue>> getTemperatureEventValuesLiveData() {
        return TemperatureEventValuesLiveData;
    }

    public MutableLiveData<List<EventValue>> getLightEventValuesLiveData() {
        return LightEventValuesLiveData;
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



    public void fetchValue(ValueTypes resource, String boardId){
        Call<List<MessageResponse>> call = messageApi.getMessage(resource.toString(), boardId);
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
        Call<List<MessageResponse>> call = messageApi.getMessage(ValueTypes.Humidity.toString(), boardId);
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
        Call<List<MessageResponse>> call = messageApi.getMessage(ValueTypes.CarbonDioxide.toString(), boardId);
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
        Call<List<MessageResponse>> call = messageApi.getMessage(ValueTypes.Light.toString(), boardId);
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

    public void fetchTemperature(String boardId){
        Call<List<MessageResponse>> call = messageApi.getMessage(ValueTypes.Temperature.toString(), boardId);
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

    public void fetchAverageCO2(String boardId, String dateFrom, String dateTo){
        Call<Double> call = messageApi.getAverage(ValueTypes.CarbonDioxide.toString(),boardId,dateFrom,dateTo);
        call.enqueue(new Callback<Double>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                CarbonDioxideAverageLiveData.setValue(response.body());
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
    }

    public void fetchTriggerRatioCO2(String boardId, String dateFrom, String dateTo){
        Call<Double> call = messageApi.getTriggerRatio(ValueTypes.CarbonDioxide.toString(),boardId,dateFrom,dateTo);
        call.enqueue(new Callback<Double>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                Log.d("Retrofit-triggers", String.valueOf(response.body()));
                CarbonDioxideTriggerRatioLiveData.setValue(response.body());
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
    }

    public void fetchAllEventValuesCO2(String boardId, String dateFrom, String dateTo){
        Call<List<EventValue>> call = messageApi.getEventValues(
                ValueTypes.CarbonDioxide.toString(),boardId,dateFrom,dateTo);

        call.enqueue(new Callback<List<EventValue>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<EventValue>> call, Response<List<EventValue>> response) {

                List<EventValue> eventValues = new ArrayList<>();
                eventValues = response.body();
                CarbonDioxideEventValuesLiveData.setValue(eventValues);
                
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<EventValue>> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });

    }

    public void fetchAverageHumidity(String boardId, String dateFrom, String dateTo){
        Call<Double> call = messageApi.getAverage(ValueTypes.Humidity.toString(),boardId,dateFrom,dateTo);
        call.enqueue(new Callback<Double>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                HumidityAverageLiveData.setValue(response.body());
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
    }

    public void fetchTriggerRatioHumidity(String boardId, String dateFrom, String dateTo){
        Call<Double> call = messageApi.getTriggerRatio(ValueTypes.Humidity.toString(),boardId,dateFrom,dateTo);
        call.enqueue(new Callback<Double>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                Log.d("Retrofit-triggers", String.valueOf(response.body()));
                HumidityTriggerRatioLiveData.setValue(response.body());
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
    }

    public void fetchAllEventValuesHumidity(String boardId, String dateFrom, String dateTo){
        Call<List<EventValue>> call = messageApi.getEventValues(
                ValueTypes.Humidity.toString(),boardId,dateFrom,dateTo);

        call.enqueue(new Callback<List<EventValue>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<EventValue>> call, Response<List<EventValue>> response) {

                List<EventValue> eventValues = new ArrayList<>();
                eventValues = response.body();
                HumidityEventValuesLiveData.setValue(eventValues);


            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<EventValue>> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });

    }

    public void fetchAverageLight(String boardId, String dateFrom, String dateTo){
        Call<Double> call = messageApi.getAverage(ValueTypes.Light.toString(),boardId,dateFrom,dateTo);
        call.enqueue(new Callback<Double>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                LightAverageLiveData.setValue(response.body());
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
    }

    public void fetchTriggerRatioLight(String boardId, String dateFrom, String dateTo){
        Call<Double> call = messageApi.getTriggerRatio(ValueTypes.Light.toString(),boardId,dateFrom,dateTo);
        call.enqueue(new Callback<Double>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                Log.d("Retrofit-triggers", String.valueOf(response.body()));
                LightTriggerRatioLiveData.setValue(response.body());
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
    }

    public void fetchAllEventValuesLight(String boardId, String dateFrom, String dateTo){
        Call<List<EventValue>> call = messageApi.getEventValues(
                ValueTypes.Light.toString(),boardId,dateFrom,dateTo);

        call.enqueue(new Callback<List<EventValue>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<EventValue>> call, Response<List<EventValue>> response) {

               List<EventValue> eventValues = new ArrayList<>();
                eventValues = response.body();

            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<EventValue>> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });

    }

    public void fetchAverageTemperature(String boardId, String dateFrom, String dateTo){
        Log.d("Retrofit-boardID", boardId);
        Call<Double> call = messageApi.getAverage(ValueTypes.Temperature.toString(),boardId,dateFrom,dateTo);
        call.enqueue(new Callback<Double>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                TemperatureAverageLiveData.setValue(response.body());
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
    }

    public void fetchTriggerRatioTemperature(String boardId, String dateFrom, String dateTo){
        Call<Double> call = messageApi.getTriggerRatio(ValueTypes.Temperature.toString(),boardId,dateFrom,dateTo);
        call.enqueue(new Callback<Double>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                Log.d("Retrofit-triggers", String.valueOf(response.body()));
                TemperatureTriggerRatioLiveData.setValue(response.body());
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
    }

    public void fetchAllEventValuesTemperature(String boardId, String dateFrom, String dateTo){
        Call<List<EventValue>> call = messageApi.getEventValues(
                ValueTypes.Temperature.toString(),boardId,dateFrom,dateTo);

        call.enqueue(new Callback<List<EventValue>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<EventValue>> call, Response<List<EventValue>> response) {

                List<EventValue> eventValues = new ArrayList<>();
                eventValues = response.body();
                TemperatureEventValuesLiveData.setValue(eventValues);

            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<EventValue>> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });

    }


    public void createNotificationWorkerTemperature(Context context, String boardId, String dimDateFrom, String dimDateTo) {
        Call<List<EventValue>> call = messageApi.getEventValues(
                ValueTypes.Temperature.toString(), boardId,dimDateFrom,dimDateTo);
        call.enqueue(new Callback<List<EventValue>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<EventValue>> call, Response<List<EventValue>> response) {
                List<EventValue> eventValues = new ArrayList<>();
                eventValues = response.body();
                if (eventValues != null && !eventValues.isEmpty()) {
                    startWorker(eventValues, context,ValueTypes.Temperature.toString());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<EventValue>> call, Throwable t) {
                Log.d("Notifications","Failed to create worker");
            }
        });


    }



    public void createNotificationWorkerLight(Context context, String boardId, String dimDateFrom, String dimDateTo) {
        Call<List<EventValue>> call = messageApi.getEventValues(
                ValueTypes.Light.toString(), boardId,dimDateFrom,dimDateTo);
        call.enqueue(new Callback<List<EventValue>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<EventValue>> call, Response<List<EventValue>> response) {
                List<EventValue> eventValues = new ArrayList<>();
                eventValues = response.body();
                if (eventValues != null && !eventValues.isEmpty()) {
                    startWorker(eventValues, context,ValueTypes.Temperature.toString());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<EventValue>> call, Throwable t) {
                Log.d("Notifications","Failed to create worker");
            }
        });
    }

    public void createNotificationWorkerCO2(Context context, String boardId, String dimDateFrom, String dimDateTo) {
        Call<List<EventValue>> call = messageApi.getEventValues(
                ValueTypes.CarbonDioxide.toString(), boardId,dimDateFrom,dimDateTo);
        call.enqueue(new Callback<List<EventValue>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<EventValue>> call, Response<List<EventValue>> response) {
                List<EventValue> eventValues = new ArrayList<>();
                eventValues = response.body();
                if (eventValues != null && !eventValues.isEmpty()) {
                    startWorker(eventValues, context,ValueTypes.Temperature.toString());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<EventValue>> call, Throwable t) {
                Log.d("Notifications","Failed to create worker");
            }
        });
    }

    public void createNotificationWorkerHumidity(Context context, String boardId, String dimDateFrom, String dimDateTo) {
        Log.d("WORKER_REPO","creating notification worker humidity");
        Call<List<EventValue>> call = messageApi.getEventValues(
                ValueTypes.Humidity.toString(), boardId,dimDateFrom,dimDateTo);
        call.enqueue(new Callback<List<EventValue>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<EventValue>> call, Response<List<EventValue>> response) {
                List<EventValue> eventValues = new ArrayList<>();
                eventValues = response.body();
                if (eventValues != null && !eventValues.isEmpty()) {
                    startWorker(eventValues, context,ValueTypes.Temperature.toString());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<EventValue>> call, Throwable t) {
                Log.d("Notifications","Failed to create worker");
            }
        });
    }

    private void startWorker(List<EventValue> eventValues, Context context, String valueType) {
        EventValue eventValue = eventValues.get(eventValues.size()-1);
        Data data = new Data.Builder()
                .putInt("KEY_EXCEEDED_BY", eventValue.getExceededBy())
                .putString("KEY_TRIGGERED_FROM",eventValue.getTriggeredFrom())
                .putInt("KEY_VALUE", eventValue.getValue())
                .putString("KEY_VALUE_TYPE", valueType)
                .build();

        Log.d("START_WORKER_REPO", eventValue.toString());

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(false)
                .setRequiresBatteryNotLow(false)
                .setRequiresStorageNotLow(false)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(FetchWorker.class)
                .setInputData(data)
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(constraints)
                .build();


        WorkManager.getInstance(context)
                .enqueue(request);
    }


}
