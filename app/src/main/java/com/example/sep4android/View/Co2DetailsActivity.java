package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.sep4android.R;

public class Co2DetailsActivity extends AppCompatActivity {

    /* TODO design new activities in AdobeXD */

    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_co2_details);

        backButton = findViewById(R.id.back_button_co2_details);
        backButton.setOnClickListener(view -> finish());
    }
}