package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.ViewModel.TemperatureDetailsViewModel;

import java.util.Calendar;
import java.util.List;

public class TemperatureDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    /* TODO make the sensors color and text validation */

    private ImageView backButton;
    private TextView localTime, lastUpdatedTime, sensorId, currentValue;
    private final String BOARD_ID_TEST = "0004A30B00259D2C";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_temperature_details);

        TemperatureDetailsViewModel temperatureDetailsViewModel
                = new ViewModelProvider(this).get(TemperatureDetailsViewModel.class);

        /* -------------------------------------------------- */

        backButton = findViewById(R.id.back_button_temperature_details);
        backButton.setOnClickListener(this);

        lastUpdatedTime = findViewById(R.id.updatedLastValueTemperature);
        sensorId = findViewById(R.id.sensorIdValueTemperature);
        currentValue = findViewById(R.id.currentValueTemperature);

        /* -------------------------------------------------- */

        temperatureDetailsViewModel.getTemperatureValueLiveData(BOARD_ID_TEST).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                lastUpdatedTime.setText(sensorValues.get(sensorValues.size()-1).getTimestamp().toString());
                //TODO sensor id, i think we should scrap it
                currentValue.setText(sensorValues.get(sensorValues.size()-1).getValue());
            }
        });
        /* Running a thread that displays local time each second */

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
        String time = "dd/MM/yyyy HH:mm:ss";
        localTime.setText(DateFormat.format(time, Calendar.getInstance().getTime()));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_temperature_details)
        {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}