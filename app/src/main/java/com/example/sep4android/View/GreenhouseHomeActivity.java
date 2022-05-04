package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sep4android.R;

public class GreenhouseHomeActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhouse_home_page);

        button = findViewById(R.id.buttonHe);
        button.setOnClickListener(view -> {
            startActivity(new Intent(this, GreenhouseSensorsActivity.class));
        });
    }
}