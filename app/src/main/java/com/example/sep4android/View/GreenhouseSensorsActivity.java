package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.sep4android.R;

public class GreenhouseSensorsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView temperatureModule, co2Module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhouse_sensors);

        temperatureModule = findViewById(R.id.temperatureModule);
        temperatureModule.setOnClickListener(this);

        co2Module = findViewById(R.id.co2Module);
        co2Module.setOnClickListener(this);

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
        }
    }
}