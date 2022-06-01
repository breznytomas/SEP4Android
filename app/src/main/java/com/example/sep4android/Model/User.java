package com.example.sep4android.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    private long id;
    @SerializedName("Email")
    private String email;
    @SerializedName("Password")
    private String password;
    @SerializedName("boardList")
    private List<Board> boards;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", boards=" + boards +
                '}';
    }
}
