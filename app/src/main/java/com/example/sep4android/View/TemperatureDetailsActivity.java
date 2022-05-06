package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.sep4android.R;

public class TemperatureDetailsActivity extends AppCompatActivity {

    /* TODO make the sensors color and text validation */

    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_temperature_details);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> finish());
    }
}