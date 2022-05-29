package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.MessageResponse;
import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.Repository.Repository;
import com.example.sep4android.ViewModel.MessageViewModel;

import java.util.List;

public class GreeneticsSensorsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView temperatureModule, co2Module, humidityModule, lightModule, backButton, viewEventsButton;
    private TextView greenHouseName, temperatureValue, co2Value, humidityValue, luminosityValue;
    private TextView backIconButton;
    private String nameToBeSentToOtherActivity;

    private final String BOARD_ID_TEST = "0004A30B00259D2C";

    private MessageViewModel viewModel;
    private Repository repository;
    private LiveData<List<MessageResponse>> messageResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhouse_sensors);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        MessageViewModel messageViewModel =
                new ViewModelProvider(this).get(MessageViewModel.class);

        /* -------------------------------------------------- */
        /* Sensor Modules */

        temperatureModule = findViewById(R.id.temperatureModule);
        temperatureModule.setOnClickListener(this);

        co2Module = findViewById(R.id.co2Module);
        co2Module.setOnClickListener(this);

        humidityModule = findViewById(R.id.humidityModule);
        humidityModule.setOnClickListener(this);

        lightModule = findViewById(R.id.lightModule);
        lightModule.setOnClickListener(this);

        /* -------------------------------------------------- */
        /* Buttons */

        viewEventsButton = findViewById(R.id.viewEventsButtonItemView);
        viewEventsButton.setOnClickListener(this);

        /* -------------------------------------------------- */
        // Send data to Sensor Details


        /* -------------------------------------------------- */

        temperatureValue = findViewById(R.id.temperatureDetailsTextView);
        co2Value = findViewById(R.id.co2DetailsTextView);
        humidityValue = findViewById(R.id.humidityDetailsTextView);
        luminosityValue = findViewById(R.id.lightDetailsTextView);
        greenHouseName = findViewById(R.id.greenhouseNameTextDetails);

        /* -------------------------------------------------- */
        // Get data from Home Activity

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            nameToBeSentToOtherActivity = bundle.getString("name");

            greenHouseName.setText(nameToBeSentToOtherActivity);
        }

        /* -------------------------------------------------- */

        messageViewModel.getLightValueLiveData(BOARD_ID_TEST).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                luminosityValue.setText(sensorValues.get(sensorValues.size() - 1).getValue());
            }
        });
        messageViewModel.getCarbonDioxideValueLiveData(BOARD_ID_TEST).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                co2Value.setText(sensorValues.get(sensorValues.size() - 1).getValue());
            }
        });
        messageViewModel.getTemperatureValueLiveData(BOARD_ID_TEST).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                temperatureValue.setText(sensorValues.get(sensorValues.size() - 1).getValue());
            }
        });
        messageViewModel.getHumidityValueLiveData(BOARD_ID_TEST).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                humidityValue.setText(sensorValues.get(sensorValues.size() - 1).getValue());
            }
        });


        /* -------------------------------------------------- */
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.temperatureModule) {
            // Send data to Sensor Details
            Intent intent = new Intent(this, TemperatureDetailsActivity.class);
            intent.putExtra("greenhouse_name", nameToBeSentToOtherActivity);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.co2Module) {
            Intent intent = new Intent(this, Co2DetailsActivity.class);
            intent.putExtra("greenhouse_name", nameToBeSentToOtherActivity);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.humidityModule) {
            Intent intent = new Intent(this, HumidityDetailsActivity.class);
            intent.putExtra("greenhouse_name", nameToBeSentToOtherActivity);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.lightModule) {
            Intent intent = new Intent(this, LightDetailsActivity.class);
            intent.putExtra("greenhouse_name", nameToBeSentToOtherActivity);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.viewEventsButtonItemView) {
            startActivity(new Intent(this, ViewEventsListActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}