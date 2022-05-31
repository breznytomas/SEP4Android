package com.example.sep4android.View;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4android.Model.Event;
import com.example.sep4android.R;
import com.example.sep4android.ViewModel.EventViewModel;

import java.util.List;

public class ViewEventsListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton;
    private TextView co2TopValue, co2BottomValue, temperatureTopValue, temperatureBottomValue,
            humidityTopValue, humidityBottomValue, lightTopValue, lightBottomValue;

    private String boardId = "";
    private EventViewModel eventViewModel;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        backButton = findViewById(R.id.back_button_view_events);
        backButton.setOnClickListener(this);

        co2TopValue = findViewById(R.id.co2TopValue);
        co2BottomValue = findViewById(R.id.co2BottomValue);
        temperatureTopValue = findViewById(R.id.temperatureTopValue);
        temperatureBottomValue = findViewById(R.id.temperatureBottomValue);
        humidityTopValue = findViewById(R.id.humidityTopValue);
        humidityBottomValue = findViewById(R.id.humidityBottomValue);
        lightTopValue = findViewById(R.id.lightTopValue);
        lightBottomValue = findViewById(R.id.lightBottomValue);


        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            boardId = bundle.getString("boardId");
        }

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        eventViewModel.getEvents(boardId).observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                for (Event event : events) {
                    switch (event.getType()) {
                        case 0:
                            //temperature
                            temperatureTopValue.setText(String.valueOf(event.getTop()));
                            temperatureBottomValue.setText(String.valueOf(event.getBottom()));
                            break;
                        case 1:
                            //humidity
                            humidityTopValue.setText(String.valueOf(event.getTop()));
                            humidityBottomValue.setText(String.valueOf(event.getBottom()));
                            break;
                        case 2:
                            //co2
                            co2TopValue.setText(String.valueOf(event.getTop()));
                            co2BottomValue.setText(String.valueOf(event.getBottom()));
                            break;
                        case 3:
                            //light
                            lightTopValue.setText(String.valueOf(event.getTop()));
                            lightBottomValue.setText(String.valueOf(event.getBottom()));
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_view_events) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        eventViewModel.wipeData();
    }
}