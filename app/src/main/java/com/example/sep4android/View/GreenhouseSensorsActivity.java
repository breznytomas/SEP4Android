package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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

public class GreenhouseSensorsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView temperatureModule, co2Module, humidityModule, lightModule, addButton, backButton;
    private TextView co2Details, greenHouseName, temperatureValue, co2Value, humidityValue, luminosityValue;

    private TextView addEventButton, backIconButton;

    private final String BOARD_ID_TEST = "0004A30B00259D2C";

    private MessageViewModel viewModel;
    private Repository repository;
    private LiveData<List<MessageResponse>> messageResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhouse_sensors);

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

        addEventButton = findViewById(R.id.addEventButtonTextView);
        addEventButton.setOnClickListener(this);

        addButton = findViewById(R.id.addEventIcon);
        addButton.setOnClickListener(this);

        backButton = findViewById(R.id.back_button_sensors);
        backButton.setOnClickListener(this);

        backIconButton = findViewById(R.id.backTextView);
        backIconButton.setOnClickListener(this);

        /* -------------------------------------------------- */

        temperatureValue = findViewById(R.id.temperatureDetailsTextView);
        co2Value = findViewById(R.id.co2DetailsTextView);
        humidityValue = findViewById(R.id.humidityDetailsTextView);
        luminosityValue = findViewById(R.id.lightDetailsTextView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        greenHouseName = findViewById(R.id.greenhouseNameTextDetails);

        if (bundle != null) {
            String name;
            name = bundle.getString("name");

            greenHouseName.setText(name);
        }

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
            startActivity(new Intent(this, TemperatureDetailsActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.co2Module) {
            startActivity(new Intent(this, Co2DetailsActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.humidityModule) {
            startActivity(new Intent(this, HumidityDetailsActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.lightModule) {
            startActivity(new Intent(this, LightDetailsActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.addEventButtonTextView) {
            startActivity(new Intent(this, AddEventActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.back_button_sensors) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (view.getId() == R.id.backTextView) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (view.getId() == R.id.addEventIcon) {
            startActivity(new Intent(this, AddEventActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}