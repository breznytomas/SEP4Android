package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.ViewModel.HumidityDetailsViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HumidityDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton, addEventButton;
    private TextView localTime, lastUpdatedTime, sensorId, currentValue;
    private final String BOARD_ID_TEST = "0004A30B00259D2C";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_details);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        HumidityDetailsViewModel viewModel = new ViewModelProvider(this)
                .get(HumidityDetailsViewModel.class);

        /* -------------------------------------------------- */

        backButton = findViewById(R.id.back_button_humidity_details);
        backButton.setOnClickListener(this);

        addEventButton = findViewById(R.id.addHumidityEventsButtonItemView);
        addEventButton.setOnClickListener(this);

        lastUpdatedTime = findViewById(R.id.updatedLastValueHumidity);
        sensorId = findViewById(R.id.sensorIdValueHumidity);
        currentValue = findViewById(R.id.currentValueHumidity);

        viewModel.getHumidityValueLiveData(BOARD_ID_TEST).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                Date unformattedDate = sensorValues.get(sensorValues.size() - 1).getTimestamp();
                String formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(unformattedDate);

                lastUpdatedTime.setText(formattedDate);
                //TODO sensor id, i think we should scrap it
                currentValue.setText(sensorValues.get(sensorValues.size() - 1).getValue());
            }
        });

        /* -------------------------------------------------- */

        localTime = findViewById(R.id.localTimeValueHumidity);

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
        if (view.getId() == R.id.back_button_humidity_details) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (view.getId() == R.id.addHumidityEventsButtonItemView) {
            startActivity(new Intent(this, AddEventActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}