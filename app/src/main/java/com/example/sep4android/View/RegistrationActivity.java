package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.sep4android.R;

public class RegistrationActivity extends AppCompatActivity {

    private ImageView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registartion);

        login = findViewById(R.id.back_button_temperature_details);
        login.setOnClickListener(view -> {
            finish();
        });
    }
}