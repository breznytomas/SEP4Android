package com.example.sep4android.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4android.Model.Event;
import com.example.sep4android.R;
import com.example.sep4android.Shared.ValueTypes;
import com.example.sep4android.ViewModel.AddEventViewModel;

import java.util.List;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton, addEventButton;
    private EditText eventName, eventTopValue, eventBottomValue;
    private String sensorType;
    private AddEventViewModel eventViewModel;
    private final String EMAIL_TEST = "policja@gov.pl";
    private final String TEST_BOARD_ID = "0004A30B00259D2C";

    /* TODO maybe change it to ValueTypes enum*/
    private String[] valueTypes = {"Temperature", "Humidity", "Carbon Dioxide", "Light"};

    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<ValueTypes> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        eventViewModel =
                new ViewModelProvider(this).get(AddEventViewModel.class);

        /* -------------------------------------------------- */

        backButton = findViewById(R.id.back_button_add_event);
        backButton.setOnClickListener(this);

        addEventButton = findViewById(R.id.addEventButtonItemView);
        addEventButton.setOnClickListener(this);

        /* -------------------------------------------------- */

        eventName = findViewById(R.id.NameEditTextEvent);
        eventTopValue = findViewById(R.id.TopEditTextEvent);
        eventBottomValue = findViewById(R.id.BottomEditTextEvent);

        /* -------------------------------------------------- */

        autoCompleteTextView = findViewById(R.id.auto_complete_text);
        adapter = new ArrayAdapter<>(this, R.layout.sensors_type_list, ValueTypes.values());
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                sensorType = String.valueOf(adapterView.getItemAtPosition(position));
                Log.d("SENSOR TESTING", "SensorType: " + sensorType);
                Toast.makeText(getApplicationContext(), "Sensor Type: " + sensorType, Toast.LENGTH_SHORT).show();
            }
        });

        /* -------------------------------------------------- */
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_add_event) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (view.getId() == R.id.addEventButtonItemView) {
            addEvent();
        }
    }


    private void addEvent() {
        if(validateSensorType()==-1){
            Toast.makeText(this,"Choose sensor type please!",Toast.LENGTH_SHORT).show();
        } else{
            String name = eventName.getText().toString().trim();
            float top = Float.parseFloat(eventTopValue.getText().toString().trim());
            float bottom = Float.parseFloat(eventBottomValue.getText().toString().trim());
//        String typeOfSensor = String.valueOf(validateSensorType());
            Event newEvent = new Event(name,validateSensorType(),top,bottom);
            eventViewModel.getEventsLiveData(TEST_BOARD_ID).observe(this, new Observer<List<Event>>() {
                @Override
                public void onChanged(List<Event> events) {
                    if(events.stream().anyMatch(event -> event.getType() == validateSensorType())){
                        Toast.makeText(getApplicationContext(),
                                "The board already has this type of Event set",Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getApplicationContext(),
                                "Event Added",
                                Toast.LENGTH_SHORT).show();

                        eventViewModel.postEvent("gowno", newEvent);
                        Log.d("DATA TEST", "Name: " + newEvent.getName() +
                                " Top: " + newEvent.getTop() +
                                " Bottom: " + newEvent.getBottom() +
                                " SensorValue: " + newEvent.getType());
                        finish();
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    }
                }
            });
        }



    }

    private int validateSensorType() {
        if (sensorType.equalsIgnoreCase("temperature")) {
            return 0;
        } else if (sensorType.equalsIgnoreCase("humidity")) {
            return 1;
        } else if (sensorType.equalsIgnoreCase("carbon dioxide")) {
            return 2;
        } else if (sensorType.equalsIgnoreCase("light")) {
            return 3;
        }

        return -1;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}