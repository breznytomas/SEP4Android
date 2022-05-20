package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sep4android.R;

import java.util.Calendar;

public class TemperatureDetailsActivity extends AppCompatActivity {

    /* TODO make the sensors color and text validation */

    private ImageView backButton;
    private TextView localTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_temperature_details);

        backButton = findViewById(R.id.back_button_temperature_details);
        backButton.setOnClickListener(view -> finish());

        /* -------------------------------------------------- */

        /*
         * Running a thread that displays local time each second
         */

        localTime = findViewById(R.id.localTimeValueTemperature);

        Thread localTimeThread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateLocalTimeTextView();
                            }
                        });
                    }
                } catch (InterruptedException e) {

                }
            }
        };

        localTimeThread.start();

        /* -------------------------------------------------- */
    }

    private void updateLocalTimeTextView() {
        String time = "dd/MM/yyyy hh:mm:ss"; // 12:00
        localTime.setText(DateFormat.format(time, Calendar.getInstance().getTime()));
    }
}