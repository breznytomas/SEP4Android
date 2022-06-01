package com.example.sep4android.Repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InstantiateFetchWorker extends ListenableWorker {
    private Repository repository;
    private Context context = getApplicationContext();
    private Calendar calendar = Calendar.getInstance();
    private final String TAG = "INSTANTIATE_WORKER";
    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public InstantiateFetchWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        repository = Repository.getInstance();
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        Log.d(TAG,"Starting work");
        Data data = getInputData();
        String[] boardIds = data.getStringArray("BOARD_IDS");
        List<String> boardIdList = new ArrayList<>();

        if (boardIds != null) {
            boardIdList = Arrays.asList(boardIds);
        }


        List<String> finalBoardIdList = boardIdList;
        Log.d(TAG,finalBoardIdList.toString());
        return CallbackToFutureAdapter.getFuture(completer -> {
            String dateFrom = new SimpleDateFormat("YYYY-MM-dd").format(calendar.getTime());
            Log.d(TAG,dateFrom);
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            String dateTo = new SimpleDateFormat("YYYY-MM-dd").format(calendar.getTime());
            Log.d(TAG,dateTo);
            if(!finalBoardIdList.isEmpty())
                for (String id:finalBoardIdList) {
                    repository.createNotificationWorkerCO2(context,id,dateFrom,dateTo);
                    repository.createNotificationWorkerHumidity(context,id,dateFrom,dateTo);
                    repository.createNotificationWorkerTemperature(context,id,dateFrom,dateTo);
                    repository.createNotificationWorkerLight(context,id,dateFrom,dateTo);
                    Log.d(TAG,"Creating Notification Worker");
                }

            return completer.set(Result.success());
        });
    }
}
