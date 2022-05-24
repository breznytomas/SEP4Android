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
import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.Repository.Repository;
import com.example.sep4android.ViewModel.MessageViewModel;

import java.util.List;

public class GreenhouseSensorsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView temperatureModule, co2Module, humidityModule, lightModule;
    private TextView co2Value, greenHouseName, temperatureValue, humidityValue, luminosityValue;
    private MessageViewModel viewModel;
    private LiveData<List<SensorValue>> SensorValues;
    private final String BOARD_ID_TEST = "0004A30B00259D2C";

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

        temperatureValue = findViewById(R.id.temperatureDetailsTextView);
        co2Value = findViewById(R.id.co2DetailsTextView);
        humidityValue = findViewById(R.id.humidityDetailsTextView);
        luminosityValue = findViewById(R.id.lightDetailsTextView);

        messageViewModel.getLightValueLiveData(BOARD_ID_TEST).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                luminosityValue.setText(sensorValues.get(sensorValues.size()-1).getValue());
            }
        });
        messageViewModel.getCarbonDioxideValueLiveData(BOARD_ID_TEST).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                co2Value.setText(sensorValues.get(sensorValues.size()-1).getValue());
            }
        });
        messageViewModel.getTemperatureValueLiveData(BOARD_ID_TEST).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                temperatureValue.setText(sensorValues.get(sensorValues.size()-1).getValue());
            }
        });
        messageViewModel.getHumidityValueLiveData(BOARD_ID_TEST).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                humidityValue.setText(sensorValues.get(sensorValues.size()-1).getValue());
            }
        });

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

//        List<SensorValue> mr = (List<SensorValue>) repository.getReceivedMessages();
//        for (SensorValue response : mr){
//            String content = "";
//            content += "Id:  " + response.getId() + "\n";
//            Log.d("Retrofit",content);
//        }


//        textView3.setText(mr.get(1).getId());

//        SensorValues = (MutableLiveData<List<SensorValue>>) repository.getReceivedMessages();
//
//        for (int i = 0; i < 2 ; i++) {
//            String content = "";
//            content += SensorValues;
//            Log.d("Retrofit",content);
//            textView3.append(content);
//        }
//
//        for (MutableLiveData<SensorValue> response : SensorValues){
//            String content = "";
//            content += "Id:  " + response.getValue().getId() + "\n";
//            Log.d("Retrofit",content);
//        }

//        textView3.setText(SensorValues.toString());


//        for (LiveData<SensorValue> response : SensorValues) {
//            String content = "";
//            content += "Id:  " + response.getValue() + "\n";
//
//            textView3.append(content);
//        }

//        moduleTest.setOnClickListener(view -> {
//            Toast.makeText(this,
//                    "Button Clicked!",
//                    Toast.LENGTH_SHORT).show();
//        });

//        moduleTest.setOnTouchListener((view, event) -> {
//                int x = (int) event.getX();
//                if (100 <= x && x <= 800) {
//                    Toast.makeText(this,
//                            "Button Clicked!",
//                            Toast.LENGTH_SHORT).show();
//                }
//            return true;
//        });
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