package com.example.sep4android.View;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4android.Model.Event;
import com.example.sep4android.R;
import com.example.sep4android.Shared.ValueTypes;
import com.example.sep4android.ViewModel.EventViewModel;

import java.util.List;

public class ViewEventsListActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView backButton, editTemp, editCO2,editHumidity,editLight;
    private TextView co2TopValue, co2BottomValue,temperatureTopValue, temperatureBottomValue,
        humidityTopValue, humidityBottomValue, lightTopValue, lightBottomValue;

    private String boardId ="";
    private Event tempEventToPass;
    private Event co2EventToPass;
    private Event humidityEventToPass;
    private Event lightEventToPass;

    private EventViewModel eventViewModel;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /* ----------------------------------------------------------------------------------------------------------------------------- */
        /* Buttons binding */
        backButton = findViewById(R.id.back_button_view_events);
        backButton.setOnClickListener(this);
        editTemp = findViewById(R.id.editEventListOfTemperature);
        editTemp.setOnClickListener(this);
        editCO2 = findViewById(R.id.editEventListOfCO2);
        editCO2.setOnClickListener(this);
        editHumidity = findViewById(R.id.editEventListOfHumidity);
        editHumidity.setOnClickListener(this);
        editLight = findViewById(R.id.editEventListOfLight);
        editLight.setOnClickListener(this);

        editTemp.setOnClickListener(this);
        editCO2.setOnClickListener(this);
        editHumidity.setOnClickListener(this);
        editLight.setOnClickListener(this);

        /* ----------------------------------------------------------------------------------------------------------------------------- */

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
                            tempEventToPass = new Event(event.getEventId(),
                                    event.getName(), event.getType(),event.getTop(), event.getBottom());
                            break;
                        case 1:
                            //humidity
                            humidityTopValue.setText(String.valueOf(event.getTop()));
                            humidityBottomValue.setText(String.valueOf(event.getBottom()));
                            humidityEventToPass = new Event(event.getEventId(),
                                    event.getName(), event.getType(),event.getTop(), event.getBottom());
                            break;
                        case 2:
                            //co2
                            co2TopValue.setText(String.valueOf(event.getTop()));
                            co2BottomValue.setText(String.valueOf(event.getBottom()));
                            co2EventToPass = new Event(event.getEventId(),
                                    event.getName(), event.getType(),event.getTop(), event.getBottom());
                            break;
                        case 3:
                            //light
                            lightTopValue.setText(String.valueOf(event.getTop()));
                            lightBottomValue.setText(String.valueOf(event.getBottom()));
                            lightEventToPass = new Event(event.getEventId(),
                                    event.getName(), event.getType(),event.getTop(), event.getBottom());
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
        if(view.getId()==R.id.editEventListOfTemperature){
            Intent i = new Intent(this, EditEventActivity.class);
            i.putExtra("boardId",boardId);
            i.putExtra("eventId",tempEventToPass.getEventId() );
            i.putExtra("eventName",tempEventToPass.getName());
            i.putExtra("eventTop",tempEventToPass.getTop());
            i.putExtra("eventBottom",tempEventToPass.getBottom());
            i.putExtra("eventType",tempEventToPass.getType());
            startActivity(i);


        }
        if(view.getId()==R.id.editEventListOfCO2){
            Intent i = new Intent(this, EditEventActivity.class);
            i.putExtra("boardId",boardId);
            i.putExtra("eventId",co2EventToPass.getEventId() );
            i.putExtra("eventName",co2EventToPass.getName());
            i.putExtra("eventTop",co2EventToPass.getTop());
            i.putExtra("eventBottom",co2EventToPass.getBottom());
            i.putExtra("eventType",co2EventToPass.getType());
            startActivity(i);
        }
        if(view.getId()==R.id.editEventListOfHumidity){
            Intent i = new Intent(this, EditEventActivity.class);
            i.putExtra("boardId",boardId);
            i.putExtra("eventId",humidityEventToPass.getEventId() );
            i.putExtra("eventName",humidityEventToPass.getName());
            i.putExtra("eventTop",humidityEventToPass.getTop());
            i.putExtra("eventBottom",humidityEventToPass.getBottom());
            i.putExtra("eventType",humidityEventToPass.getType());
            startActivity(i);
        }
        if(view.getId()==R.id.editEventListOfLight){
            Intent i = new Intent(this, EditEventActivity.class);
            i.putExtra("boardId",boardId);
            i.putExtra("eventId",lightEventToPass.getEventId() );
            i.putExtra("eventName",lightEventToPass.getName());
            i.putExtra("eventTop",lightEventToPass.getTop());
            i.putExtra("eventBottom",lightEventToPass.getBottom());
            i.putExtra("eventType",lightEventToPass.getType());
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        eventViewModel.wipeData();
    }
}