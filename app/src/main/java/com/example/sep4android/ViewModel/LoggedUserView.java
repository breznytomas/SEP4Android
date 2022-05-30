package com.example.sep4android.ViewModel;

public class LoggedUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI

    LoggedUserView(String email) {
        this.displayName = email;
    }

    public String getEmail() {
        return displayName;
    }
}