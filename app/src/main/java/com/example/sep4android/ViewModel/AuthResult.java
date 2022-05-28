package com.example.sep4android.ViewModel;

import androidx.annotation.Nullable;

public class AuthResult {
    @Nullable
    private LoggedUserView success;
    @Nullable
    private Integer error;

    AuthResult(@Nullable Integer error) {
        this.error = error;
    }

    AuthResult(@Nullable LoggedUserView success) {
        this.success = success;
    }

    @Nullable
    public LoggedUserView getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

    @Override
    public String toString() {
        return "AuthResult{" +
                "success=" + success +
                ", error=" + error +
                '}';
    }
}