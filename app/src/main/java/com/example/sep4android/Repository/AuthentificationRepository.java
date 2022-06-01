package com.example.sep4android.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Model.User;
import com.example.sep4android.RemoteDataSource.AuthentificationDataSource;
import com.example.sep4android.RemoteDataSource.Result;

public class AuthentificationRepository {

    private static volatile AuthentificationRepository instance;

    private AuthentificationDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private User user = null;
    private final MutableLiveData<User> userResponse;

    // private constructor : singleton access
    private AuthentificationRepository(AuthentificationDataSource dataSource) {
        this.dataSource = dataSource;
        userResponse = new MutableLiveData<>();
    }

    public static AuthentificationRepository getInstance(AuthentificationDataSource dataSource) {
        if (instance == null) {
            instance = new AuthentificationRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public MutableLiveData<User> getUserResponse() {
        return userResponse;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(User user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public MutableLiveData<User> login(String email, String password) {
        // handle login
        User user = dataSource.login(email, password);
        Result<User> result = new Result.Error(new Exception("Failed login"));
        if(user!=null){
            result = new Result.Success<>(user);
                    Log.d("dupaRepository", email+password);
            userResponse.setValue(user);

        }

        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<User>) result).getData());
            Log.d("dupaRepositorError","result set as error");
        }

        return userResponse;

    }
    public void register(String email, String password){
        dataSource.register(email,password);
    }
}
