package com.example.sep4android.Model;

import java.util.List;

public class User {
    private long id;
    private String email;
    private String password;
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
