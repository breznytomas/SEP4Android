package com.example.sep4android.Repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;



public class FetchWorker extends ListenableWorker {
    private static final String KEY_BOARDID = "KEY_BOARDID";
    private Repository repository;

    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public FetchWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        repository = Repository.getInstance();
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        return CallbackToFutureAdapter.getFuture(completer -> {
            Data inputData = getInputData();
            String boardId = inputData.getString("KEY_BOARDID");
            Log.d("WORKER2137","KURWA DZIALA");
            repository.fetchTemperature(boardId);
            repository.fetchLight(boardId);
            repository.fetchCO2(boardId);
            repository.fetchHumidity(boardId);
           return completer.set(Result.success());
        });
    }


}
