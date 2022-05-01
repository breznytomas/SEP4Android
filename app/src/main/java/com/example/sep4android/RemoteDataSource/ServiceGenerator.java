package com.example.sep4android.RemoteDataSource;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static MessageApi messageApi;

    public static MessageApi getMessageApi(){
        if(messageApi == null){
            messageApi = new Retrofit.Builder()
                    .baseUrl("https://smart-greenhouse-data-server.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MessageApi.class);
        }

        return messageApi;
    }
}
