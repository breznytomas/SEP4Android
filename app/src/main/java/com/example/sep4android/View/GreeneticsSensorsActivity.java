package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.MessageResponse;
import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.Repository.Repository;
import com.example.sep4android.Shared.ValueTypes;
import com.example.sep4android.ViewModel.MessageViewModel;

import java.util.List;

public class GreeneticsSensorsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView temperatureModule, co2Module, humidityModule, lightModule, backButton, viewEventsButton;
    private TextView greenHouseName, temperatureValue, co2Value, humidityValue, luminosityValue;



    private String boardID = "";
    private String name;

    private MessageViewModel messageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhouse_sensors);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        messageViewModel =
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

            name = bundle.getString("name");
            boardID = bundle.getString("boardId");
            greenHouseName.setText(name);
        }

        messageViewModel.getLightValueLiveData(boardID).observe(this, new Observer<List<SensorValue>>() {

            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                if(!sensorValues.isEmpty())
                    luminosityValue.setText(sensorValues.get(sensorValues.size() - 1).getValue());
            }
        });
        messageViewModel.getCarbonDioxideValueLiveData(boardID).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                if(!sensorValues.isEmpty())
                    co2Value.setText(sensorValues.get(sensorValues.size() - 1).getValue());
            }
        });
        messageViewModel.getTemperatureValueLiveData(boardID).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                if(!sensorValues.isEmpty())
                    temperatureValue.setText(sensorValues.get(sensorValues.size() - 1).getValue());
            }
        });
        messageViewModel.getHumidityValueLiveData(boardID).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                if(!sensorValues.isEmpty())
                    humidityValue.setText(sensorValues.get(sensorValues.size() - 1).getValue());
            }
        });




        /* -------------------------------------------------- */
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.temperatureModule) {
            Intent i = new Intent(this, TemperatureDetailsActivity.class);
            i.putExtra("greenhouseName", name);
            i.putExtra("valueType", ValueTypes.Temperature.toString());
            i.putExtra("boardId",boardID);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.co2Module) {
            Intent i = new Intent(this, Co2DetailsActivity.class);
            i.putExtra("greenhouseName", name);
            i.putExtra("valueType", ValueTypes.CarbonDioxide.toString());
            i.putExtra("boardId",boardID);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.humidityModule) {
            Intent i =new Intent(this, HumidityDetailsActivity.class);
            i.putExtra("greenhouseName", name);
            i.putExtra("valueType", ValueTypes.Humidity.toString());
            i.putExtra("boardId",boardID);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.lightModule) {
            Intent i =new Intent(this, LightDetailsActivity.class);
            i.putExtra("greenhouseName", name);
            i.putExtra("valueType", ValueTypes.Light.toString());
            i.putExtra("boardId",boardID.toString());
            Log.d("SENSORS", boardID);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.viewEventsButtonItemView) {
            Intent i = new Intent(this, ViewEventsListActivity.class);
            i.putExtra("boardId",boardID);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


          }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        messageViewModel.wipeData();
    }
}