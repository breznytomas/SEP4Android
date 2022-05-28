package com.example.sep4android.RemoteDataSource;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("Email")
    private String email;
    @SerializedName("Password")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
