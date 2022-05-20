package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.MessageResponse;
import com.example.sep4android.Repository.Repository;
import com.example.sep4android.ViewModel.MessageViewModel;

import java.util.List;

public class GreenhouseSensorsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView temperatureModule, co2Module, humidityModule, lightModule;
    private TextView co2Details, greenHouseName;
    private MessageViewModel viewModel;
    private Repository repository;
    private LiveData<List<MessageResponse>> messageResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhouse_sensors);

        /* -------------------------------------------------- */
        /* Sensor Modules */

        temperatureModule = findViewById(R.id.temperatureModule);
        temperatureModule.setOnClickListener(this);

        co2Module = findViewById(R.id.co2Module);
        co2Module.setOnClickListener(this);

        co2Details = findViewById(R.id.co2DetailsTextView);
        co2Details.setText("120");

        humidityModule = findViewById(R.id.humidityModule);
        humidityModule.setOnClickListener(this);

        lightModule = findViewById(R.id.lightModule);
        lightModule.setOnClickListener(this);

        /* -------------------------------------------------- */

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        greenHouseName = findViewById(R.id.greenhouseNameTextDetails);

        if (bundle != null) {
            String name;
            name = bundle.getString("name");

            greenHouseName.setText(name);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.temperatureModule) {
            startActivity(new Intent(this, TemperatureDetailsActivity.class));
        } else if (view.getId() == R.id.co2Module) {
            startActivity(new Intent(this, Co2DetailsActivity.class));
        } else if (view.getId() == R.id.humidityModule) {
            startActivity(new Intent(this, HumidityDetailsActivity.class));
        } else if (view.getId() == R.id.lightModule) {
            startActivity(new Intent(this, LightDetailsActivity.class));
        }
    }
}