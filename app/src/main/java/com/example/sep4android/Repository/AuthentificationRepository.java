package com.example.sep4android.Repository;

import android.util.Log;

import com.example.sep4android.Model.User;
import com.example.sep4android.RemoteDataSource.AuthentificationDataSource;
import com.example.sep4android.RemoteDataSource.Result;

public class AuthentificationRepository {

    private static volatile AuthentificationRepository instance;

    private AuthentificationDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private User user = null;

    // private constructor : singleton access
    private AuthentificationRepository(AuthentificationDataSource dataSource) {
        this.dataSource = dataSource;
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

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(User user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<User> login(String email, String password) {
        // handle login
        Result<User> result = dataSource.login(email, password);
        Log.d("dupaRepository", email+password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<User>) result).getData());
        }
        return result;
    }
    public void register(String email, String password){
        dataSource.register(email,password);
    }
}
