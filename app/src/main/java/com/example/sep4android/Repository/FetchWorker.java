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
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.EventValue;
import com.google.common.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class FetchWorker extends Worker {
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
    public Result doWork() {
        Log.d("WORKER2137","KURWA DZIALA");
        Data inputData = getInputData();
        int exceededBy = inputData.getInt("KEY_EXCEEDED_BY",-999);
        int value = inputData.getInt("KEY_VALUE",-999);
        String triggeredFrom = inputData.getString("KEY_TRIGGERED_FROM");
        String valueType = inputData.getString("KEY_VALUE_TYPE");
        String progress = valueType + " " + triggeredFrom.toLowerCase() + " by:" +
                exceededBy + " (" + value+")";
        setForegroundAsync(createForegroundInfo(progress));
        return Result.success();
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
                .setSmallIcon(R.drawable.ic_custom_launcher_background)
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
