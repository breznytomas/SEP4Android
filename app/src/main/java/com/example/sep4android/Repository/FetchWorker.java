package com.example.sep4android.Repository;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION;
import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.ForegroundInfo;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.EventValue;
import com.google.common.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class FetchWorker extends ListenableWorker {
    private static final String KEY_BOARDID = "KEY_BOARDID";
    private Repository repository;
    private NotificationManager notificationManager;
    private NotificationManagerCompat notificationManagerCompat;

    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public FetchWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        repository = Repository.getInstance();
        notificationManager = (NotificationManager)
                appContext.getSystemService(NOTIFICATION_SERVICE);
        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        return CallbackToFutureAdapter.getFuture(completer -> {
            Data inputData = getInputData();
            String boardId = inputData.getString("KEY_BOARDID");
            Log.d("WORKER2137","KURWA DZIALA");
            String dimDateTo = new SimpleDateFormat("YYYY-MM-dd").format(new Date(System.currentTimeMillis()));
            String dimDateFrom =new SimpleDateFormat("YYYY-MM-dd").format(new Date(System.currentTimeMillis()- TimeUnit.DAYS.toMillis(30))) ;
            List<EventValue> eventValues = new ArrayList<>();
            EventValue temperature = repository.fetchEventValuesTemperature(boardId, dimDateFrom, dimDateTo);
            EventValue light = repository.fetchEventValuesLight(boardId, dimDateFrom, dimDateTo);
            EventValue carbon = repository.fetchEventValuesCO2(boardId, dimDateFrom,dimDateTo);
            EventValue humidity = repository.fetchEventValuesHumidity(boardId,dimDateFrom,dimDateTo);
            if(temperature!=null)
                eventValues.add(temperature);
            if(light!=null)
                eventValues.add(light);
            if(carbon!=null)
                eventValues.add(carbon);
            if(humidity!=null)
                eventValues.add(humidity);
            Log.d("WORKER2137", eventValues.toString());
            String progress = "";
            if(eventValues.size()>1){
                progress = "Several sensors exceeded limit!";
            }
            else if(eventValues.size()==1){
                progress = "Your sensor exceeded limit";
            }
            else if(eventValues.isEmpty()){
                progress="All your sensors are fine";
            }




            setForegroundAsync(createForegroundInfo(progress));
           return completer.set(Result.success());
        });
    }

    @NonNull
    @RequiresApi(Build.VERSION_CODES.R)
    private ForegroundInfo createForegroundInfo(@NonNull String progress){

        Context context = getApplicationContext();
        String id = context.getString(R.string.notification_channel_id);
        String title = context.getString(R.string.notification_title);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            createChannel();
        }

        Notification notification = new NotificationCompat.Builder(context, id)
                .setContentTitle(title)
                .setContentText(progress)
                .setTicker(title)
                .setSmallIcon(R.drawable.ic_arduino)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();



        return new ForegroundInfo(Integer.parseInt(id),notification,
                FOREGROUND_SERVICE_TYPE_LOCATION | FOREGROUND_SERVICE_TYPE_MICROPHONE);



    }


    @RequiresApi(Build.VERSION_CODES.R)
    private void createChannel() {
    CharSequence name = getApplicationContext().getString(R.string.channel_name);
    String description = getApplicationContext().getString(R.string.channel_description);
    int importance = NotificationManager.IMPORTANCE_HIGH;
    NotificationChannel channel = new NotificationChannel("2137",name,importance);
    channel.setDescription(description);

    notificationManager.createNotificationChannel(channel);

    }


}
